package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomFolder;
import com.zyplayer.doc.data.repository.manage.mapper.ApiCustomFolderMapper;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	
	@Override
	public void deleteFolder(Long id) {
		this.deleteFolderRecursion(id);
		// 逻辑删除
		ApiCustomFolder folderDel = new ApiCustomFolder();
		folderDel.setId(id);
		folderDel.setYn(0);
		this.updateById(folderDel);
	}
	
	/**
	 * 递归删除下级
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-22
	 */
	public void deleteFolderRecursion(Long id) {
		QueryWrapper<ApiCustomFolder> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		wrapper.eq("parent_folder_id", id);
		List<ApiCustomFolder> childrenList = this.list(wrapper);
		if (CollectionUtils.isNotEmpty(childrenList)) {
			for (ApiCustomFolder folder : childrenList) {
				// 递归删除下级
				this.deleteFolderRecursion(folder.getId());
			}
			// 逻辑删除
			ApiCustomFolder folderDel = new ApiCustomFolder();
			folderDel.setYn(0);
			QueryWrapper<ApiCustomFolder> wrapperDel = new QueryWrapper<>();
			wrapperDel.in("id", childrenList.stream().map(ApiCustomFolder::getId).collect(Collectors.toSet()));
			this.update(folderDel, wrapperDel);
		}
	}
	
}
