package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomFolder;
import com.zyplayer.doc.data.repository.manage.mapper.ApiCustomFolderMapper;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 自建接口文档文件夹 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
@Service
public class ApiCustomFolderServiceImpl extends ServiceImpl<ApiCustomFolderMapper, ApiCustomFolder> implements ApiCustomFolderService {
	
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	
	@Override
	public void addFolder(ApiCustomFolder apiCustomFolder) {
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolder.getDocId());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (apiCustomFolder.getId() == null) {
			apiCustomFolder.setYn(1);
			apiCustomFolder.setCreateTime(new Date());
			apiCustomFolder.setCreateUserId(currentUser.getUserId());
			apiCustomFolder.setCreateUserName(currentUser.getUsername());
		} else {
			apiCustomFolder.setCreateTime(null);
			apiCustomFolder.setCreateUserId(null);
			apiCustomFolder.setCreateUserName(null);
		}
		this.saveOrUpdate(apiCustomFolder);
	}
}
