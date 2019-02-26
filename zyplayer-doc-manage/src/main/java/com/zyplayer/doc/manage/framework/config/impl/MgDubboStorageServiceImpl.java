package com.zyplayer.doc.manage.framework.config.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.dubbo.framework.service.MgDubboStorage;
import com.zyplayer.doc.dubbo.framework.service.MgDubboStorageService;
import com.zyplayer.doc.data.repository.manage.entity.ZyplayerStorage;
import com.zyplayer.doc.data.service.manage.ZyplayerStorageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * dubbo相关的文档存储服务
 * @author 暮光：城中城
 * @since 2018年8月19日
 */
@Service
public class MgDubboStorageServiceImpl implements MgDubboStorageService {
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
	public List<MgDubboStorage> like(String key, String value) {
		QueryWrapper<ZyplayerStorage> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(key), "doc_key", key);
		wrapper.like(StringUtils.isNotBlank(value), "doc_value", value);
		List<ZyplayerStorage> storageList = zyplayerStorageService.list(wrapper);
		if (storageList == null || storageList.isEmpty()) {
			return Collections.emptyList();
		}
		return storageList.stream().map(val -> new MgDubboStorage(val.getDocKey(), val.getDocValue())).collect(Collectors.toList());
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
	
}
