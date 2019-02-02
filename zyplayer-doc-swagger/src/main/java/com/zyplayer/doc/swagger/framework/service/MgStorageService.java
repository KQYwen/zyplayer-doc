package com.zyplayer.doc.swagger.framework.service;

import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import org.apache.commons.lang.math.NumberUtils;

import java.util.List;

/**
 * 实现此类才能使用服务器端的存贮功能
 * @author 暮光：城中城
 * @since 2018年8月19日
 */
public interface MgStorageService {
	
	/**
	 * 获取存储的值
	 * @author 暮光：城中城
	 * @since 2018年8月19日
	 * @param key 参数
	 * @return 值
	 */
	String get(String key);
	
	/**
	 * 模糊获取存储的值
	 * @author 暮光：城中城
	 * @since 2018年8月19日
	 * @param key 参数
	 * @param value 值
	 * @return 值
	 */
	List<MgStorage> like(String key, String value);
	
	/**
	 * 存储数据
	 * @author 暮光：城中城
	 * @since 2018年8月19日
	 * @param key 参数
	 * @param value 值
	 */
	void put(String key, String value);
	
	/**
	 * 删除数据
	 * @author 暮光：城中城
	 * @since 2018年8月19日
	 * @param key 参数
	 */
	void remove(String key);
	
	/**
	 * 获取代理请求白名单
	 * @author 暮光：城中城
	 * @since 2018年8月19日
	 */
	List<String> getProxyRequestWhiteDomain();
	
	/**
	 * 获取一个自增的ID
	 * @author 暮光：城中城
	 * @since 2019年1月27日
	 */
	default Integer getNextId() {
		synchronized (StorageKeys.SWAGGER_ID_WORKER) {
			String idWorker = this.get(StorageKeys.SWAGGER_ID_WORKER);
			Integer nextId = NumberUtils.toInt(idWorker, 1);
			this.put(StorageKeys.SWAGGER_ID_WORKER, String.valueOf(nextId + 1));
			return nextId;
		}
	}
}
