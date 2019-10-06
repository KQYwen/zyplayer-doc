package com.zyplayer.doc.data.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 100, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	
	/**
	 * 获取线程池
	 */
	public static ThreadPoolExecutor getThreadPool() {
		return threadPoolExecutor;
	}
}
