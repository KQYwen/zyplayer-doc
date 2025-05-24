package org.dromara.zyplayer.data.service.manage.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.UserMessage;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPage;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageMapper;
import org.dromara.zyplayer.data.repository.manage.vo.WikiPageTemplateInfoVo;
import org.dromara.zyplayer.data.repository.support.consts.DocSysType;
import org.dromara.zyplayer.data.repository.support.consts.UserMsgType;
import org.dromara.zyplayer.data.service.manage.UserMessageService;
import org.dromara.zyplayer.data.service.manage.WikiPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-06-05
 */
@Service
public class WikiPageServiceImpl extends ServiceImpl<WikiPageMapper, WikiPage> implements WikiPageService {
	
	@Resource
	WikiPageMapper wikiPageMapper;
	@Resource
	UserMessageService userMessageService;
	
	@Override
	public void changeParent(WikiPage wikiPage, Integer beforeSeq, Integer afterSeq) {
		WikiPage wikiPageSel = this.getById(wikiPage.getId());
		if (beforeSeq != null && beforeSeq >= 0) {
			// 在此seq之前
			wikiPageMapper.updateAfterSeq(wikiPageSel.getSpaceId(), wikiPage.getParentId(), beforeSeq);
			wikiPage.setSeqNo(beforeSeq);
		} else if (afterSeq != null && afterSeq >= 0) {
			// 在此seq之后
			wikiPageMapper.updateAfterSeq(wikiPageSel.getSpaceId(), wikiPage.getParentId(), afterSeq + 1);
			wikiPage.setSeqNo(afterSeq + 1);
		} else {
			// 放在末尾
			Integer lastSeq = wikiPageMapper.getLastSeq(wikiPageSel.getSpaceId(), wikiPage.getParentId());
			lastSeq = Optional.ofNullable(lastSeq).orElse(0);
			wikiPage.setSeqNo(lastSeq + 1);
		}
		this.updateById(wikiPage);
		// 重置当前分支的所有节点seq值
		wikiPageMapper.updateChildrenSeq(wikiPageSel.getSpaceId(), wikiPage.getParentId());
		// 给相关人发送消息
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_PARENT);
		userMessage.setAffectUserId(wikiPageSel.getCreateUserId());
		userMessage.setAffectUserName(wikiPageSel.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
	}
	
	@Override
	public void deletePage(WikiPage wikiPage) {
		// 给相关人发送消息
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPage.getId(), wikiPage.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_DELETE);
		userMessage.setAffectUserId(wikiPage.getCreateUserId());
		userMessage.setAffectUserName(wikiPage.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		// 递归删除，名字不能修改
		wikiPage.setName(null);
		this.deletePageAndSon(wikiPage);
	}
	
	private void deletePageAndSon(WikiPage wikiPage) {
		wikiPage.setDelFlag(wikiPage.getDelFlag());
		this.updateById(wikiPage);
		
		QueryWrapper<WikiPage> wrapper = new QueryWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("parent_id", wikiPage.getId());
		List<WikiPage> wikiPageList = this.list(wrapper);
		if (CollectionUtil.isEmpty(wikiPageList)) {
			return;
		}
		// 递归删除子页面
		for (WikiPage page : wikiPageList) {
			wikiPage.setId(page.getId());
			this.deletePageAndSon(wikiPage);
		}
	}

	public List<WikiPageTemplateInfoVo> wikiPageTemplateInfos(Long spaceId){
		return wikiPageMapper.getWikiPageTemplateInfos(spaceId);
	}
}
