package com.vehicle.base.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 本地缓存类
public class CacheManager {
    private static Map<String, CacheData> CACHE_DATA = new ConcurrentHashMap<>();
	
    public static <T> T getData(String key, Load<T> load, int expire) {
        T data = getData(key);
        if (data == null && load != null) {
            data = load.load();
            if (data != null) {
                setData(key, data, expire);
            }
        }
        return data;
    }

	// 根据key获取数据
    public static <T> T getData(String key) {
        CacheData<T> data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSaveTime() >= System.currentTimeMillis())) {
            return data.getData();
        }
        return null;
    }
	
	// 存入数据并设置过期时间
    public static <T> void setData(String key, T data, int expire) {
        CACHE_DATA.put(key, new CacheData(data, expire));
    }
	
	// 根据key清除数据
    public static void clear(String key) {
        CACHE_DATA.remove(key);
    }

	// 清除所有缓存数据
    public static void clearAll() {
        CACHE_DATA.clear();
    }

    public interface Load<T> {
        T load();
    }

    private static class CacheData<T> {
        CacheData(T t, int expire) {
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = System.currentTimeMillis() + this.expire;
        }

        private T data;
        private long saveTime; // 存活时间
        private long expire;   // 过期时间 小于等于0标识永久存活

        public T getData() {
            return data;
        }

        public long getExpire() {
            return expire;
        }

        public long getSaveTime() {
            return saveTime;
        }
    }
}

