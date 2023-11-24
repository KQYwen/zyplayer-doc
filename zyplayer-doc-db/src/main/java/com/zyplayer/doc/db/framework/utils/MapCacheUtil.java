package com.zyplayer.doc.db.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于Map实现内存缓存
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class MapCacheUtil {

    private static MapCacheUtil cacheUtil;
    private static Map<String,Object> cacheMap;

    private MapCacheUtil(){
        cacheMap = new HashMap<>();
    }

    public static MapCacheUtil getInstance(){
        if (cacheUtil == null){
            cacheUtil = new MapCacheUtil();
        }
        return cacheUtil;
    }

    /**
     * 添加缓存
     */
    public void addCacheData(String key,Object obj){
        cacheMap.put(key,obj);
    }

    /**
     * 取出缓存
     */
    public Object getCacheData(String key){
        return cacheMap.get(key);
    }

    /**
     * 清楚缓存
     */
    public void removeCacheData(String key){
        cacheMap.remove(key);
    }

    public Map cacheMap() {
        return cacheMap;
    }
}
