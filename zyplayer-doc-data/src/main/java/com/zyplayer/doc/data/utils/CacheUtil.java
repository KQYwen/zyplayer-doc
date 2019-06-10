package com.zyplayer.doc.data.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存工具类
 *
 * @author 暮光：城中城
 * @since 2019年05月25日
 */
public class CacheUtil {
	
	// 定期清除过期的key
	static {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				long currentTimeMillis = System.currentTimeMillis();
				for (Map.Entry<String, CacheTime> entry : cacheTimeMap.entrySet()) {
					CacheTime cacheTime = entry.getValue();
					if (currentTimeMillis - cacheTime.getLastVisitTime() < (cacheTime.getSecond() * 1000)) {
						continue;
					}
					cacheMap.remove(entry.getKey());
				}
			}
		}, 0, 1000);
	}
	// 现在是内存缓存，不支持分布式部署，后期考虑放到redis，但感觉也没必要。。
	private static Map<String, Object> cacheMap = new ConcurrentHashMap<>();
	private static Map<String, CacheTime> cacheTimeMap = new ConcurrentHashMap<>();
	
	public static void put(String key, Object value) {
		put(key, value, (long) (60 * 60 * 12));
	}
	
	public static void put(String key, Object value, Long second) {
		if (StringUtils.isBlank(key) || value == null) {
			return;
		}
		cacheMap.put(key, value);
		cacheTimeMap.put(key, new CacheTime(second));
	}
	
	public static void remove(String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		cacheMap.remove(key);
		cacheTimeMap.remove(key);
	}
	
	public static <T> T get(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		CacheTime cacheTime = cacheTimeMap.get(key);
		if (cacheTime != null) {
			cacheTime.setLastVisitTime(System.currentTimeMillis());
			cacheTimeMap.put(key, cacheTime);
		}
		return (T) cacheMap.get(key);
	}
	
	private static class CacheTime {
		private Long second;
		private Long lastVisitTime;
		
		public CacheTime(Long second) {
			this.second = second;
			this.lastVisitTime = System.currentTimeMillis();
		}
		
		public Long getSecond() {
			return second;
		}
		
		public void setSecond(Long second) {
			this.second = second;
		}
		
		public Long getLastVisitTime() {
			return lastVisitTime;
		}
		
		public void setLastVisitTime(Long lastVisitTime) {
			this.lastVisitTime = lastVisitTime;
		}
	}
}
