package com.zyplayer.doc.data.service.manage.impl;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.mapper.ApiDocMapper;
import com.zyplayer.doc.data.repository.manage.vo.ApiDocVo;
import com.zyplayer.doc.data.repository.support.consts.ApiAuthType;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.manage.ApiDocService;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * <p>
 * api文档地址 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Service
public class ApiDocServiceImpl extends ServiceImpl<ApiDocMapper, ApiDoc> implements ApiDocService {
	
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	
	@Override
	public IPage<ApiDocVo> getApiDocList(ApiDoc apiDoc, Integer pageNum, Integer pageSize) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		// 用户权限
		List<UserAuth> userModuleAuthList = userAuthService.getUserModuleAuthList(currentUser.getUserId(), DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), null);
		List<Long> authDocIdList = userModuleAuthList.stream().map(UserAuth::getSysModuleId).collect(Collectors.toList());
		// 权限ID对应的权限名
		Map<Long, Integer> authDocAuthMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(userModuleAuthList)) {
			Collection<AuthInfo> authInfoList = authInfoService.listByIds(userModuleAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toSet()));
			Map<Long, String> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getId, AuthInfo::getAuthName));
			Map<Long, Integer> authDocAuthRes = userModuleAuthList.stream().collect(Collectors.toMap(UserAuth::getSysModuleId, val -> ApiAuthType.typeOf(authInfoMap.get(val.getAuthId())).getType()));
			authDocAuthMap.putAll(authDocAuthRes);
		}
		// 条件组装
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq(apiDoc.getDocType() != null, "doc_type", apiDoc.getDocType());
		queryWrapper.eq(apiDoc.getOpenVisit() != null, "open_visit", apiDoc.getOpenVisit());
		queryWrapper.eq(apiDoc.getDocStatus() != null, "doc_status", apiDoc.getDocStatus());
		queryWrapper.and(consumer ->
				consumer.or(or -> or.eq("create_user_id", currentUser.getUserId()))
						.or(CollectionUtils.isNotEmpty(authDocIdList), or -> or.in("id", authDocIdList))
		);
		queryWrapper.orderByAsc("id");
		queryWrapper.select("id", "name", "doc_type", "doc_url", "share_uuid",
				"rewrite_domain", "open_visit", "doc_status", "create_user_id",
				"create_user_name", "create_time");
		IPage<ApiDoc> page = new Page<>(pageNum, pageSize);
		this.page(page, queryWrapper);
		// vo转换
		return page.convert(doc -> {
			// 角色判断
			Integer authType = authDocAuthMap.get(doc.getId());
			if (Objects.equals(doc.getCreateUserId(), currentUser.getUserId())) {
				authType = ApiAuthType.MANAGE.getType();
			}
			ApiDocVo apiDocVo = new ApiDocVo();
			apiDocVo.setAuthType(authType);
			apiDocVo.setId(doc.getId());
			apiDocVo.setName(doc.getName());
			apiDocVo.setDocType(doc.getDocType());
			apiDocVo.setDocUrl(doc.getDocUrl());
			apiDocVo.setRewriteDomain(doc.getRewriteDomain());
			apiDocVo.setOpenVisit(doc.getOpenVisit());
			apiDocVo.setDocStatus(doc.getDocStatus());
			apiDocVo.setCreateUserId(doc.getCreateUserId());
			apiDocVo.setCreateUserName(doc.getCreateUserName());
			apiDocVo.setCreateTime(doc.getCreateTime());
			apiDocVo.setShareUuid(doc.getShareUuid());
			return apiDocVo;
		});
	}
	
	@Override
	public List<ApiDoc> getApiDocList() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		// 用户权限
		List<UserAuth> userModuleAuthList = userAuthService.getUserModuleAuthList(currentUser.getUserId(), DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), null);
		List<Long> authDocIdList = userModuleAuthList.stream().map(UserAuth::getSysModuleId).collect(Collectors.toList());
		// 条件组装
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_status", 1);
		queryWrapper.and(consumer ->
				consumer.or(or -> or.eq("create_user_id", currentUser.getUserId()))
						.or(CollectionUtils.isNotEmpty(authDocIdList), or -> or.in("id", authDocIdList))
		);
		queryWrapper.orderByAsc("id");
		queryWrapper.select("id", "name", "doc_type", "doc_url", "rewrite_domain", "open_visit", "doc_status");
		return this.list(queryWrapper);
	}
	
	@Override
	public ApiDoc getByShareUuid(String shareUuid) {
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("open_visit", 1);
		queryWrapper.eq("share_uuid", shareUuid);
		queryWrapper.select("name", "doc_type", "doc_url", "share_uuid", "json_content", "share_instruction");
		return this.getOne(queryWrapper);
	}
}
