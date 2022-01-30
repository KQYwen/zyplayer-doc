package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomFolder;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomRequest;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.repository.manage.mapper.ApiCustomRequestMapper;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomDocVo;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomVo;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import com.zyplayer.doc.data.service.manage.ApiCustomRequestService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	@Resource
	ApiCustomRequestService apiCustomRequestService;
	@Resource
	ApiCustomFolderService apiCustomFolderService;
	
	@Override
	public List<ApiCustomVo> buildCustomApiList(ApiDoc apiDoc) {
		// 查询api列表
		QueryWrapper<ApiCustomRequest> wrapper = new QueryWrapper<>();
		wrapper.eq("doc_id", apiDoc.getId());
		wrapper.eq("yn", 1);
		List<ApiCustomRequest> apiCustomList = apiCustomRequestService.list(wrapper);
		Map<Long, List<ApiCustomRequest>> apiMap = apiCustomList.stream()
				.peek(item -> item.setFolderId(Optional.ofNullable(item.getFolderId()).orElse(0L)))
				.collect(Collectors.groupingBy(ApiCustomRequest::getFolderId));
		// 查询分组列表
		QueryWrapper<ApiCustomFolder> groupWrapper = new QueryWrapper<>();
		groupWrapper.eq("doc_id", apiDoc.getId());
		groupWrapper.eq("yn", 1);
		List<ApiCustomFolder> apiCustomGroupList = apiCustomFolderService.list(groupWrapper);
		Map<Long, List<ApiCustomFolder>> apiGroupMap = apiCustomGroupList.stream()
				.peek(item -> item.setParentFolderId(Optional.ofNullable(item.getParentFolderId()).orElse(0L)))
				.collect(Collectors.groupingBy(ApiCustomFolder::getParentFolderId));
		List<ApiCustomDocVo> apis = this.buildApiCustomDocVo(apiMap.get(0L));
		List<ApiCustomVo> customGroupChildren = this.getCustomGroupChildren(apiGroupMap.get(0L), apiGroupMap, apiMap);
		// 组装结果对象
		ApiCustomVo apiCustomVo = new ApiCustomVo();
		apiCustomVo.setChildren(customGroupChildren);
		apiCustomVo.setName(apiDoc.getName());
		apiCustomVo.setApis(apis);
		List<ApiCustomVo> apiCustomVoList = new LinkedList<>();
		apiCustomVoList.add(apiCustomVo);
		return apiCustomVoList;
	}
	
	@Override
	public ApiCustomRequest addRequest(ApiCustomRequest apiCustomRequest) {
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomRequest.getDocId());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (apiCustomRequest.getId() == null) {
			apiCustomRequest.setYn(1);
			apiCustomRequest.setCreateTime(new Date());
			apiCustomRequest.setCreateUserId(currentUser.getUserId());
			apiCustomRequest.setCreateUserName(currentUser.getUsername());
		} else {
			apiCustomRequest.setDocId(null);
			apiCustomRequest.setCreateTime(null);
			apiCustomRequest.setCreateUserId(null);
			apiCustomRequest.setCreateUserName(null);
		}
		String apiName = StringUtils.defaultString(apiCustomRequest.getApiName(), "新建接口");
		apiCustomRequest.setApiName(apiName);
		this.saveOrUpdate(apiCustomRequest);
		return apiCustomRequest;
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
//		for (ApiCustomFolder customGroup : apiCustomGroups) {
//			List<ApiCustomRequest> apiCustomList = apiMap.get(customGroup.getId());
//			List<ApiCustomFolder> children = apiGroupMap.get(customGroup.getId());
//			List<ApiCustomVo> customGroupChildren = this.getCustomGroupChildren(children, apiGroupMap, apiMap);
//			List<ApiCustomDocVo> apis = this.buildApiCustomDocVo(apiCustomList);
//			ApiCustomVo apiCustomVo = new ApiCustomVo();
//			apiCustomVo.setFolderId(customGroup.getId());
//			apiCustomVo.setName(customGroup.getFolderName());
//			apiCustomVo.setDesc(customGroup.getFolderDesc());
//			apiCustomVo.setChildren(customGroupChildren);
//			apiCustomVo.setApis(apis);
//			apiCustomVoList.add(apiCustomVo);
//		}
		return apiCustomVoList;
	}
	
	private List<ApiCustomDocVo> buildApiCustomDocVo(List<ApiCustomRequest> apiCustomList) {
		List<ApiCustomDocVo> apis = new LinkedList<>();
//		if (CollectionUtils.isNotEmpty(apiCustomList)) {
//			for (ApiCustomRequest apiCustom : apiCustomList) {
//				ApiCustomDocVo apiCustomDocVo = new ApiCustomDocVo();
//				apiCustomDocVo.setRequestId(apiCustom.getId());
//				apiCustomDocVo.setFolderId(apiCustom.getFolderId());
//				apiCustomDocVo.setApiUrl(apiCustom.getApiUrl());
//				apiCustomDocVo.setMethod(apiCustom.getMethod());
//				apiCustomDocVo.setApiName(apiCustom.getApiName());
//				apiCustomDocVo.setBodyData(apiCustom.getBodyData());
//				apiCustomDocVo.setCookieData(apiCustom.getCookieData());
//				apiCustomDocVo.setFormData(apiCustom.getFormData());
//				apiCustomDocVo.setHeaderData(apiCustom.getHeaderData());
//				apis.add(apiCustomDocVo);
//			}
//		}
		return apis;
	}
}
