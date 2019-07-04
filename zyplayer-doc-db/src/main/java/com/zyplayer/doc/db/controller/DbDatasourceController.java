package com.zyplayer.doc.db.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.service.manage.DbDatasourceService;
import com.zyplayer.doc.db.framework.configuration.DatasourceUtil;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.json.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 数据源控制器
 *
 * @author 暮光：城中城
 * @since 2019年6月29日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/datasource")
public class DbDatasourceController {

	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@Resource
	DbDatasourceService dbDatasourceService;

	@PostMapping(value = "/list")
	public ResponseJson list() {
		QueryWrapper<DbDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		List<DbDatasource> datasourceList = dbDatasourceService.list(wrapper);
		return DocDbResponseJson.ok(datasourceList);
	}

	@PostMapping(value = "/update")
	public ResponseJson update(DbDatasource dbDatasource) {
		if (StringUtils.isBlank(dbDatasource.getDriverClassName())) {
			return DocDbResponseJson.warn("驱动类必选");
		} else if (StringUtils.isBlank(dbDatasource.getSourceUrl())) {
			return DocDbResponseJson.warn("地址必填");
		} else if (StringUtils.isBlank(dbDatasource.getSourceName())) {
			return DocDbResponseJson.warn("用户名必填");
		} else if (StringUtils.isBlank(dbDatasource.getSourcePassword())) {
			return DocDbResponseJson.warn("密码必填");
		}
		Long sourceId = Optional.ofNullable(dbDatasource.getId()).orElse(0L);
		if (sourceId > 0) {
			dbDatasourceService.updateById(dbDatasource);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			dbDatasource.setCreateTime(new Date());
			dbDatasource.setCreateUserId(currentUser.getUserId());
			dbDatasource.setYn(1);
			dbDatasourceService.save(dbDatasource);
		}
		List<DatabaseFactoryBean> newFactoryBeanList = new LinkedList<>();
		List<DatabaseFactoryBean> databaseFactoryBeanList = databaseRegistrationBean.getDatabaseFactoryBeanList();
		for (DatabaseFactoryBean factoryBean : databaseFactoryBeanList) {
			if (Objects.equals(factoryBean.getId(), sourceId)) {
				try {
					// 关闭旧的数据源
					factoryBean.getDataSource().close();
					factoryBean.getDataSource().destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				newFactoryBeanList.add(factoryBean);
			}
		}
		// 创建新的数据源
		DatabaseFactoryBean databaseFactoryBean = DatasourceUtil.createDatabaseFactoryBean(dbDatasource);
		if (databaseFactoryBean != null) {
			newFactoryBeanList.add(databaseFactoryBean);
		}
		databaseRegistrationBean.setDatabaseFactoryBeanList(newFactoryBeanList);
		
		if (databaseFactoryBean == null) {
			return DocDbResponseJson.warn("创建数据源失败，请检查配置是否正确");
		}
		return DocDbResponseJson.ok();
	}
}

