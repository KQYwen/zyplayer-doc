package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomFolder;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomRequest;
import com.zyplayer.doc.data.repository.manage.mapper.ApiCustomRequestMapper;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomDocVo;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomVo;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import com.zyplayer.doc.data.service.manage.ApiCustomRequestService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 自建接口文档 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
@Service
public class ApiCustomRequestServiceImpl extends ServiceImpl<ApiCustomRequestMapper, ApiCustomRequest> implements ApiCustomRequestService {
	
	@Resource
	ApiCustomRequestService apiCustomRequestService;
	@Resource
	ApiCustomFolderService apiCustomFolderService;
	
	@Override
	public List<ApiCustomVo> buildCustomApiList(Long docId) {
		// 查询api列表
		QueryWrapper<ApiCustomRequest> wrapper = new QueryWrapper<>();
		wrapper.eq("doc_id", docId);
		wrapper.eq("yn", 1);
		List<ApiCustomRequest> apiCustomList = apiCustomRequestService.list(wrapper);
		Map<Long, List<ApiCustomRequest>> apiMap = apiCustomList.stream().collect(Collectors.groupingBy(ApiCustomRequest::getFolderId));
		// 查询分组列表
		QueryWrapper<ApiCustomFolder> groupWrapper = new QueryWrapper<>();
		groupWrapper.eq("doc_id", docId);
		groupWrapper.eq("yn", 1);
		List<ApiCustomFolder> apiCustomGroupList = apiCustomFolderService.list(groupWrapper);
		Map<Long, List<ApiCustomFolder>> apiGroupMap = apiCustomGroupList.stream().collect(Collectors.groupingBy(ApiCustomFolder::getParentFolderId));
		List<ApiCustomFolder> apiCustomGroups = apiGroupMap.get(null);
		if (CollectionUtils.isEmpty(apiCustomGroups)) {
			return Collections.emptyList();
		}
		return this.getCustomGroupChildren(apiCustomGroups, apiGroupMap, apiMap);
	}
	
	/**
	 * 递归获取目录树
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-22
	 */
	private List<ApiCustomVo> getCustomGroupChildren(List<ApiCustomFolder> apiCustomGroups, Map<Long, List<ApiCustomFolder>> apiGroupMap, Map<Long, List<ApiCustomRequest>> apiMap) {
		if (CollectionUtils.isEmpty(apiCustomGroups)) {
			return Collections.emptyList();
		}
		List<ApiCustomVo> apiCustomVoList = new LinkedList<>();
		for (ApiCustomFolder customGroup : apiCustomGroups) {
			List<ApiCustomRequest> apiCustomList = apiMap.get(customGroup.getId());
			List<ApiCustomFolder> children = apiGroupMap.get(customGroup.getId());
			List<ApiCustomVo> customGroupChildren = this.getCustomGroupChildren(children, apiGroupMap, apiMap);
			ApiCustomVo apiCustomVo = new ApiCustomVo();
			apiCustomVo.setName(customGroup.getFolderName());
			apiCustomVo.setDesc(customGroup.getFolderDesc());
			apiCustomVo.setChildren(customGroupChildren);
			if (CollectionUtils.isNotEmpty(apiCustomList)) {
				List<ApiCustomDocVo> apis = new LinkedList<>();
				for (ApiCustomRequest apiCustom : apiCustomList) {
					ApiCustomDocVo apiCustomDocVo = new ApiCustomDocVo();
					apiCustomDocVo.setApiUrl(apiCustom.getApiUrl());
					apiCustomDocVo.setApiName(apiCustom.getApiName());
					apiCustomDocVo.setBodyData(apiCustom.getBodyData());
					apiCustomDocVo.setCookieData(apiCustom.getCookieData());
					apiCustomDocVo.setFormData(apiCustom.getFormData());
					apiCustomDocVo.setHeaderData(apiCustom.getHeaderData());
					apis.add(apiCustomDocVo);
				}
				apiCustomVo.setApis(apis);
			}
			apiCustomVoList.add(apiCustomVo);
		}
		return apiCustomVoList;
	}
}
