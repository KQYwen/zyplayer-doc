package com.zyplayer.doc.elasticsearch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.EsDatasource;
import com.zyplayer.doc.data.service.manage.EsDatasourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 数据源控制器
 *
 * @author 暮光：城中城
 * @since 2019年7月27日
 */
@RestController
@RequestMapping("/zyplayer-doc-es/datasource")
public class EsDatasourceController {

	@Resource
	EsDatasourceService esDatasourceService;

	@PostMapping(value = "/list")
	public ResponseJson list() {
		QueryWrapper<EsDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		List<EsDatasource> datasourceList = esDatasourceService.list(wrapper);
		return DocResponseJson.ok(datasourceList);
	}
	
	@AuthMan("ES_DATASOURCE_MANAGE")
	@PostMapping(value = "/update")
	public ResponseJson update(EsDatasource esDatasource) {
		if (StringUtils.isBlank(esDatasource.getName())) {
			return DocResponseJson.warn("名字必填");
		} else if (StringUtils.isBlank(esDatasource.getHostPort())) {
			return DocResponseJson.warn("地址端口必选");
		} else if (StringUtils.isBlank(esDatasource.getScheme())) {
			return DocResponseJson.warn("scheme必填");
		}
		Long sourceId = Optional.ofNullable(esDatasource.getId()).orElse(0L);
		if (sourceId > 0) {
			// 这三项不需要修改
			esDatasource.setCreateTime(null);
			esDatasource.setCreateUserId(null);
			esDatasource.setCreateUserName(null);
			esDatasourceService.updateById(esDatasource);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			esDatasource.setCreateTime(new Date());
			esDatasource.setCreateUserId(currentUser.getUserId());
			esDatasource.setCreateUserName(currentUser.getUsername());
			esDatasource.setYn(1);
			esDatasourceService.save(esDatasource);
		}
		return DocResponseJson.ok();
	}
}

