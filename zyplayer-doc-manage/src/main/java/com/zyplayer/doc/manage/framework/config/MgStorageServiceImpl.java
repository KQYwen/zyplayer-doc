package com.zyplayer.doc.manage.framework.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.manage.repository.manage.entity.ZyplayerStorage;
import com.zyplayer.doc.manage.service.manage.ZyplayerStorageService;
import com.zyplayer.doc.swagger.framework.service.MgStorage;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 申明为@Service之后网页上才能使用存储能力，同时需要在@EnableSwagger2的地方添加@EnableSwaggerMgUi注解，
 * 才能开启存储的接口<br/>
 * 开放存储能力的好处：<br/>
 * 所有网页的配置、调试值都可以存储到服务器的数据库中，便于团队所有人的调试，一人配置，所有人受益<br/>
 * 如果不开启的话，数据是存放在浏览器的localStorage中，每个人、每个浏览器都得配置一次才能使用<br/>
 *
 * @author 暮光：城中城
 * @since 2018年8月19日
 */
@Service
public class MgStorageServiceImpl implements MgStorageService {
	
	@Value("${zyplayer.doc.swagger.proxy-request.white-domain}")
	private String proxyRequestWhiteDomain;
	@Resource
	ZyplayerStorageService zyplayerStorageService;

	/**
	 * 使用数据库来存储，例： storageMapper.select(key);
	 */
	@Override
	public String get(String key) {
		QueryWrapper<ZyplayerStorage> wrapper = new QueryWrapper<>();
		wrapper.eq("doc_key", key);
		ZyplayerStorage zyplayerStorage = zyplayerStorageService.getOne(wrapper);
		if (zyplayerStorage == null) {
			return null;
		}
		return zyplayerStorage.getDocValue();
	}

	@Override
	public List<MgStorage> like(String key, String value) {
		QueryWrapper<ZyplayerStorage> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(key), "doc_key", key);
		wrapper.like(StringUtils.isNotBlank(value), "doc_value", value);
		List<ZyplayerStorage> storageList = zyplayerStorageService.list(wrapper);
		if (storageList == null || storageList.isEmpty()) {
			return Collections.emptyList();
		}
		return storageList.stream().map(val -> new MgStorage(val.getDocKey(), val.getDocValue())).collect(Collectors.toList());
	}

	/**
	 * 使用数据库来存储，例： storageMapper.updateOrInsert(key, value);
	 */
	@Override
	public void put(String key, String value) {
		ZyplayerStorage entity = new ZyplayerStorage();
		entity.setDocValue(value);
		UpdateWrapper<ZyplayerStorage> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq(true, "doc_key", key);
		boolean update = zyplayerStorageService.update(entity, updateWrapper);
		if (!update) {
			entity = new ZyplayerStorage();
			entity.setDocValue(value);
			entity.setDocKey(key);
			zyplayerStorageService.save(entity);
		}
	}
	
	/**
	 * 删除一个key
	 */
	@Override
	public void remove(String key) {
		UpdateWrapper<ZyplayerStorage> queryWrapper = new UpdateWrapper<>();
		queryWrapper.eq("doc_key", key);
		zyplayerStorageService.remove(queryWrapper);
	}
	
	@Override
	public List<String> getProxyRequestWhiteDomain() {
		if (StringUtils.isBlank(proxyRequestWhiteDomain)) {
			return Collections.emptyList();
		}
		return Arrays.asList(proxyRequestWhiteDomain.split(";"));
	}
	
}
