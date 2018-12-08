package com.neuedu.commen;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {


    // 获取缓存对象LoadingCache
    private static LoadingCache<String,String> loadingCache= CacheBuilder
            .newBuilder().initialCapacity(1000) // 设置缓存
            .maximumSize(10000) // 最大缓存
            .expireAfterAccess(12, TimeUnit.HOURS) // 设置过期时间
            .build(new CacheLoader<String, String>() {
                // 当key不存在是 调用该方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });


    /**
     * 向缓存添加键值对
     * */
    public static void put(String key,String value){
        loadingCache.put(key,value);
    }


    /**
     * 向缓存获取值
     * */
    public static String get(String key){
        String value = null;
        try {
            value = loadingCache.get(key);
            if (value.equals("null")){
                return null;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }
}
