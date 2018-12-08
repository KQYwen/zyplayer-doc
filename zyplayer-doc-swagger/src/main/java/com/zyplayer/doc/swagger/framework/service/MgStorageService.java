package com.zyplayer.doc.swagger.framework.service;

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
	
}