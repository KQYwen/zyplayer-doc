package com.zyplayer.doc.test.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.util.Set;
import java.util.function.Function;

/**
 * redis缓存工具类
 *
 * @author 暮光：城中城
 * @since 2019年08月25日
 */
public class RedisUtil {
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	private static JedisPool pool;
	
	public static void init(String host, int port, int timeout) {
		if (pool != null) {
			logger.warn("redis连接池已初始化，请勿重复操作");
			return;
		}
		logger.info("初始化redis连接池...");
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(200);
		poolConfig.setMaxIdle(50);
		poolConfig.setMinIdle(10);
		poolConfig.setTestOnReturn(true);
		pool = new JedisPool(poolConfig, host, port, timeout);
		logger.info("初始化redis连接池完毕");
	}
	
	/**
	 * 获取连接，执行，释放连接
	 * @author 暮光：城中城
	 * @since 2019年08月25日
	 * @param consumer 获取连接后的处理
	 */
	public static <T> T apply(Function<? super Jedis, ? extends T> consumer) {
		return expireAndApply(null, 0, consumer);
	}
	
	/**
	 * 获取连接，续期，执行，释放连接
	 * @author 暮光：城中城
	 * @since 2019年08月25日
	 * @param key 为空则不续期
	 * @param expire 小于等于0则不续期
	 * @param consumer 获取连接后的处理
	 */
	public static <T> T expireAndApply(String key, int expire, Function<? super Jedis, ? extends T> consumer) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			T result = consumer.apply(jedis);
			if (key != null && expire > 0) {
				jedis.expire(key, expire);
			}
			return result;
		} catch (Exception e) {
			logger.error("redis操作失败：{}", e.getMessage());
			return null;
		} finally {
			pool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 累加
	 */
	public static Long incr(String key, int expire) {
		return expireAndApply(key, expire, jedis -> jedis.incr(key));
	}
	
	/**
	 * 浮点型累加
	 */
	public static Double incrByFloat(String key, Double value, int expire) {
		return expireAndApply(key, expire, jedis -> jedis.incrByFloat(key, value));
	}
	
	/**
	 * 累加指定值
	 */
	public static Long incrByNum(String key, long num, int expire) {
		return expireAndApply(key, expire, jedis -> jedis.incrBy(key, num));
	}
	
	/**
	 * 排重set
	 */
	public static void uniqueSet(String key, int expire, String... member) {
		expireAndApply(key, expire, jedis -> jedis.sadd(key, member));
	}
	
	/**
	 * 判断set中成员是否存在
	 */
	public static boolean checkExitInSet(String key, String member) {
		return apply(jedis -> jedis.sismember(key, member));
	}
	
	/**
	 * 添加到有序集合中
	 */
	public static void topSet(String key, String name, long score, int expire) {
		expireAndApply(key, expire, jedis -> jedis.zadd(key, score, name));
	}
	
	/**
	 * 是否存在
	 */
	public static boolean exists(String key) {
		return apply(jedis -> jedis.exists(key));
	}
	
	/**
	 * 续期
	 */
	public static void expire(String key, int seconds) {
		apply(jedis -> jedis.expire(key, seconds));
	}
	
	/**
	 * 获取值
	 */
	public static String get(String key) {
		return apply(jedis -> jedis.get(key));
	}
	
	/**
	 * 获取long型值
	 */
	public static long getLong(String key) {
		return NumberUtils.toLong(get(key));
	}
	
	/**
	 * 设置值
	 */
	public static void set(String key, String value, int expire) {
		expireAndApply(key, expire, jedis -> jedis.set(key, value));
	}
	
	/**
	 * 设置值
	 * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
	 *          if it already exist.
	 * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
	 * @param time expire time in the units of {@param #expx}
	 * @return Status code reply
	 */
	public static String set(String key, String value, String nxxx, String expx, Long time) {
		return apply(jedis -> jedis.set(key, value, nxxx, expx, time));
	}
	
	/**
	 * 添加List值
	 */
	public static Long sadd(String key, String... member) {
		return apply(jedis -> jedis.sadd(key, member));
	}
	
	/**
	 * 删除key
	 */
	public static void del(String key) {
		apply(jedis -> jedis.del(key));
	}
	
	/**
	 * 模糊匹配获取key列表
	 */
	public static Set<String> keys(String pattern) {
		return apply(jedis -> jedis.keys(pattern));
	}
	
	/**
	 * 返回值在指定区间内的集合
	 */
	public static Set<Tuple> zrevrangeWithScores(String topSetKey, int start, int end) {
		return apply(jedis -> jedis.zrevrangeWithScores(topSetKey, start, end));
	}
	
	/**
	 * 将参数添加到 HyperLogLog 数据结构中
	 */
	public static Long pfadd(String key, String value, int expire) {
		return expireAndApply(key, expire, jedis -> jedis.pfadd(key, value));
	}
	
	/**
	 * 返回 HyperLogLog 的基数估算值
	 */
	public static long pfcount(String key) {
		return apply(jedis -> jedis.pfcount(key));
	}
	
}
