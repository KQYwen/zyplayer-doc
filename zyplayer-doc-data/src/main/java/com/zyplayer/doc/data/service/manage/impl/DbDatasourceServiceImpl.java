package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.mapper.DbDatasourceMapper;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.manage.DbDatasourceService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-07-04
 */
@Service
public class DbDatasourceServiceImpl extends ServiceImpl<DbDatasourceMapper, DbDatasource> implements DbDatasourceService {

	@Resource
	UserAuthService userAuthService;

	@Autowired
	private DbDatasourceMapper dbDatasourceMapper;

	@Override
	public List<DbDatasource> getDataSourceList() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		QueryWrapper<DbDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		// 没管理权限只返回有权限的数据源
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)) {
			QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
			updateWrapper.eq("sys_type", DocSysType.DB.getType());
			updateWrapper.eq("sys_module_type", DocSysModuleType.Db.DATASOURCE.getType());
			updateWrapper.eq("del_flag", 0);
			updateWrapper.eq("user_id", currentUser.getUserId());
			List<UserAuth> userAuthList = userAuthService.list(updateWrapper);
			if (userAuthList == null || userAuthList.isEmpty()) {
				return new ArrayList<>();
			}
			List<Long> userAuthDbIds = userAuthList.stream()
					.map(UserAuth::getSysModuleId)
					.collect(Collectors.toList());
			wrapper.in("id", userAuthDbIds);
		}
		wrapper.select("id", "name", "group_name");

		return list(wrapper);
	}

	/**
	 * 获取数据源
	 */
	@Override
	public DbDatasource getDataSource(Long sourceId){
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		QueryWrapper<DbDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		wrapper.eq("id", sourceId);
		// 没管理权限只返回有权限的数据源
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)) {
			QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
			updateWrapper.eq("sys_type", DocSysType.DB.getType());
			updateWrapper.eq("sys_module_type", DocSysModuleType.Db.DATASOURCE.getType());
			updateWrapper.eq("del_flag", 0);
			updateWrapper.eq("user_id", currentUser.getUserId());
			List<UserAuth> userAuthList = userAuthService.list(updateWrapper);
			if (userAuthList == null || userAuthList.isEmpty()) {
				return null;
			}
			List<Long> userAuthDbIds = userAuthList.stream()
					.map(UserAuth::getSysModuleId)
					.collect(Collectors.toList());
			wrapper.in("id", userAuthDbIds);
		}
		wrapper.select("id", "name", "group_name");
		return dbDatasourceMapper.selectOne(wrapper);
	}
}
