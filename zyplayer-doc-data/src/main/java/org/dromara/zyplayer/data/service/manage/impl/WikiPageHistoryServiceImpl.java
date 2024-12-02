package org.dromara.zyplayer.data.service.manage.impl;

import cn.hutool.core.util.ZipUtil;
import org.dromara.zyplayer.core.exception.ConfirmException;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPageHistory;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageHistoryMapper;
import org.dromara.zyplayer.data.service.manage.WikiPageHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2020-09-05
 */
@Service
public class WikiPageHistoryServiceImpl extends ServiceImpl<WikiPageHistoryMapper, WikiPageHistory> implements WikiPageHistoryService {
	private static final Logger logger = LoggerFactory.getLogger(WikiPageHistoryServiceImpl.class);
	
	@Override
	public WikiPageHistory saveRecord(Long spaceId, Long pageId, String content) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPageHistory entity = new WikiPageHistory();
		entity.setPageId(pageId);
		entity.setCreateTime(new Date());
		entity.setDelFlag(0);
		try {
			entity.setContent(ZipUtil.gzip(content, StandardCharsets.UTF_8.name()));
		} catch (Exception e) {
			logger.error("创建历史记录失败", e);
			throw new ConfirmException("创建历史记录失败：" + e.getMessage(), e);
		}
		entity.setCreateUserId(currentUser.getUserId());
		entity.setCreateUserName(currentUser.getUsername());
		this.save(entity);
		return entity;
	}
}
