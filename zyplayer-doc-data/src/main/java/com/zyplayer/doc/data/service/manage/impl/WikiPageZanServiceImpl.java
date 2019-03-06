package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageZan;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageMapper;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageZanMapper;
import com.zyplayer.doc.data.service.manage.WikiPageZanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-05
 */
@Service
public class WikiPageZanServiceImpl extends ServiceImpl<WikiPageZanMapper, WikiPageZan> implements WikiPageZanService {
	
	@Resource
	WikiPageMapper wikiPageMapper;
	
	@Override
	@Transactional
	public void zanPage(WikiPageZan wikiPageZan) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UpdateWrapper<WikiPageZan> wrapper = new UpdateWrapper<>();
		wrapper.eq("create_user_id", currentUser.getUserId());
		wrapper.eq("page_id", wikiPageZan.getPageId());
		wrapper.eq(wikiPageZan.getCommentId() != null, "comment_id", wikiPageZan.getCommentId());
		WikiPageZan pageZan = this.getOne(wrapper);
		if (pageZan != null) {
			if (Objects.equals(wikiPageZan.getYn(), pageZan.getYn())) {
				return;
			}
			wikiPageZan.setId(pageZan.getId());
			this.updateById(wikiPageZan);
		} else {
			wikiPageZan.setCreateTime(new Date());
			wikiPageZan.setCreateUserId(currentUser.getUserId());
			wikiPageZan.setCreateUserName(currentUser.getUsername());
			this.save(wikiPageZan);
		}
		int numAdd = wikiPageZan.getYn() == 1 ? 1 : -1;
		wikiPageMapper.updateZanNum(wikiPageZan.getPageId(), numAdd);
	}
}
