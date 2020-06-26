package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageMapper;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import org.apache.commons.collections.CollectionUtils;
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
		if (beforeSeq != null && beforeSeq >= 0) {
			// 在此seq之前
			wikiPageMapper.updateAfterSeq(wikiPage.getParentId(), beforeSeq);
			wikiPage.setSeqNo(beforeSeq);
		} else if (afterSeq != null && afterSeq >= 0) {
			// 在此seq之后
			wikiPageMapper.updateAfterSeq(wikiPage.getParentId(), afterSeq + 1);
			wikiPage.setSeqNo(afterSeq + 1);
		} else {
			// 放在末尾
			Integer lastSeq = wikiPageMapper.getLastSeq(wikiPage.getParentId());
			lastSeq = Optional.ofNullable(lastSeq).orElse(0);
			wikiPage.setSeqNo(lastSeq + 1);
		}
		this.updateById(wikiPage);
		// 给相关人发送消息
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPage.getId(), wikiPage.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_PARENT);
		userMessage.setAffectUserId(wikiPage.getCreateUserId());
		userMessage.setAffectUserName(wikiPage.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
	}
	
	@Override
	public void deletePage(WikiPage wikiPage) {
		// 给相关人发送消息
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPage.getId(), wikiPage.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_DELETE);
		userMessage.setAffectUserId(wikiPage.getCreateUserId());
		userMessage.setAffectUserName(wikiPage.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		// 递归删除
		this.deletePageAndSon(wikiPage);
	}
	
	private void deletePageAndSon(WikiPage wikiPage) {
		wikiPage.setDelFlag(1);
		this.updateById(wikiPage);
		
		QueryWrapper<WikiPage> wrapper = new QueryWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("parent_id", wikiPage.getId());
		List<WikiPage> wikiPageList = this.list(wrapper);
		if (CollectionUtils.isEmpty(wikiPageList)) {
			return;
		}
		// 递归删除子页面
		for (WikiPage page : wikiPageList) {
			wikiPage.setId(page.getId());
			this.deletePageAndSon(wikiPage);
		}
	}
}
