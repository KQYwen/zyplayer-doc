package com.zyplayer.doc.data.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 轻量缓存工具类
 *
 * @author 暮光：城中城
 * @since 2019年05月25日
 */
public class CacheUtil {
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	
	// 定期清除过期的key
	static {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				long currentTimeMillis = System.currentTimeMillis();
				for (String key : cacheDataMap.keySet()) {
					CacheData cacheData = cacheDataMap.get(key);
					if (cacheData == null || currentTimeMillis - cacheData.getLastVisitTime() < (cacheData.getSeconds() * 1000)) {
						continue;
					}
					cacheDataMap.remove(key);
					logger.info("缓存过期，清理缓存：{}", key);
				}
			}
		}, 0, 5000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		CacheUtil.put("xx", "xx", 6);
		Thread.sleep(4000);
		CacheUtil.get("xx");
		System.out.println(cacheDataMap.get("xx").getLastVisitTime());
		Thread.sleep(7000);
		CacheUtil.get("xx");
		System.out.println(cacheDataMap.get("xx").getLastVisitTime());
		Thread.sleep(99000);
	}
	
	// 现在是内存缓存，不支持分布式部署，后期考虑放到redis，但感觉也没必要。。
	private static Map<String, CacheData> cacheDataMap = new ConcurrentHashMap<>();
	
	/**
	 * 放入缓存，默认12小时，按最后一次访问的12小时
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		put(key, value, 60 * 60 * 12);
	}
	
	/**
	 * 放入缓存，有访问则继续有效
	 * @param key
	 * @param value
	 * @param seconds 缓存时长 秒
	 */
	public static void put(String key, Object value, long seconds) {
		if (StringUtils.isBlank(key) || value == null) {
			return;
		}
		cacheDataMap.put(key, new CacheData(seconds, value));
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public static void remove(String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		cacheDataMap.remove(key);
	}
	
	/**
	 * 获取缓存
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		CacheData cacheData = cacheDataMap.get(key);
		if (cacheData != null) {
			cacheData.setLastVisitTime(System.currentTimeMillis());
			return (T) cacheData.getData();
		}
		return null;
	}
	
	private static class CacheData {
		/**缓存时长 秒*/
		private Long seconds;
		private Long lastVisitTime;
		private Object data;
		
		public CacheData(long seconds, Object data) {
			this.data = data;
			this.seconds = seconds;
			this.lastVisitTime = System.currentTimeMillis();
		}
		
		public Long getSeconds() {
			return seconds;
		}
		
		public void setSeconds(Long seconds) {
			this.seconds = seconds;
		}
		
		public Long getLastVisitTime() {
			return lastVisitTime;
		}
		
		public void setLastVisitTime(Long lastVisitTime) {
			this.lastVisitTime = lastVisitTime;
		}
		
		public Object getData() {
			return data;
		}
		
		public void setData(Object data) {
			this.data = data;
		}
	}
}
