package org.dromara.zyplayer.data.service.common;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.dromara.zyplayer.core.exception.ConfirmException;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.ApiDoc;
import org.dromara.zyplayer.data.repository.manage.entity.AuthInfo;
import org.dromara.zyplayer.data.repository.manage.entity.UserAuth;
import org.dromara.zyplayer.data.repository.support.consts.ApiAuthType;
import org.dromara.zyplayer.data.repository.support.consts.DocSysModuleType;
import org.dromara.zyplayer.data.repository.support.consts.DocSysType;
import org.dromara.zyplayer.data.service.manage.ApiDocService;
import org.dromara.zyplayer.data.service.manage.AuthInfoService;
import org.dromara.zyplayer.data.service.manage.UserAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 判断文档权限
 *
 * @author 暮光：城中城
 * @since 2021-12-12
 */
@Service
public class ApiDocAuthJudgeService {
	
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	@Resource
	ApiDocService apiDocService;
	
	/**
	 * 判断当前用户是否有管理员权限
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-12
	 */
	public boolean haveManageAuth(Long docId) {
		ApiDoc apiDoc = apiDocService.getById(docId);
		return haveManageAuth(apiDoc);
	}
	
	/**
	 * 判断当前用户是否有管理员权限
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-12
	 */
	public boolean haveManageAuth(ApiDoc apiDoc) {
		if (apiDoc == null) {
			return false;
		}
		// 创建者
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (Objects.equals(apiDoc.getCreateUserId(), currentUser.getUserId())) {
			return true;
		}
		// 管理员
		AuthInfo authInfo = authInfoService.getByCode(ApiAuthType.MANAGE.getCode());
		List<UserAuth> userModuleAuthList = userAuthService.getUserModuleAuthList(currentUser.getUserId(), DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), apiDoc.getId());
		return userModuleAuthList.stream().anyMatch(auth -> Objects.equals(auth.getAuthId(), authInfo.getId()));
	}
	
	/**
	 * 判断当前用户是否有查看权限
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-12
	 */
	public void judgeDevelopAndThrow(Long docId) {
		ApiDoc apiDoc = apiDocService.getById(docId);
		if (!haveDevelopAuth(apiDoc)) {
			throw new ConfirmException("没有此文档的开发权限");
		}
	}
	
	/**
	 * 判断当前用户是否有查看权限
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-12
	 */
	public boolean haveDevelopAuth(Long docId) {
		ApiDoc apiDoc = apiDocService.getById(docId);
		return haveDevelopAuth(apiDoc);
	}
	
	/**
	 * 判断当前用户是否有查看权限
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-12
	 */
	public boolean haveDevelopAuth(ApiDoc apiDoc) {
		if (apiDoc == null) {
			return false;
		}
		// 创建者
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (Objects.equals(apiDoc.getCreateUserId(), currentUser.getUserId())) {
			return true;
		}
		// 开发人员，存在则说明肯定是管理员或开发人员
		List<UserAuth> userModuleAuthList = userAuthService.getUserModuleAuthList(currentUser.getUserId(), DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), apiDoc.getId());
		return CollectionUtils.isNotEmpty(userModuleAuthList);
	}
}
