package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpaceFavorite;
import com.zyplayer.doc.data.repository.manage.mapper.WikiSpaceFavoriteMapper;
import com.zyplayer.doc.data.service.manage.WikiSpaceFavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户空间收藏记录表 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
@Service
public class WikiSpaceFavoriteServiceImpl extends ServiceImpl<WikiSpaceFavoriteMapper, WikiSpaceFavorite> implements WikiSpaceFavoriteService {
	
	@Override
	public List<WikiSpaceFavorite> myFavoriteSpaceList() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		LambdaQueryWrapper<WikiSpaceFavorite> favoriteWrapper = new LambdaQueryWrapper<>();
		favoriteWrapper.eq(WikiSpaceFavorite::getUserId, currentUser.getUserId());
		favoriteWrapper.eq(WikiSpaceFavorite::getDelFlag, 0);
		return this.list(favoriteWrapper);
	}
}
