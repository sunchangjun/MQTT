package framework.core.utils;

import com.google.common.collect.Lists;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.io.Serializable;

/**
 * Cache工具类
 */
public class CacheUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * 默认cache的key.
     */
    private static final String SYS_CACHE = "sysCache";

    // ================================================================
    // Fields
    // ================================================================

    /**
     * cache管理器,从spring获得.
     */
    private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

    /**
     * 根据cache类型决定是否序列化，默认ehcache不序列化
     */
    private static boolean SERIALIZE_ABLE = false;

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return get(SYS_CACHE, key);
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key 键
     */
    public static void remove(String key) {
        remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     *
     * @param cacheName 缓存KEY
     * @param key       键
     * @return 值
     */
    public static Object get(String cacheName, String key) {
        Cache.ValueWrapper element = getCache(cacheName).get(key);
        // return element == null ? null : element.get();
        if (element != null) {
            Object value = element.get();
            if (value != null && SERIALIZE_ABLE) {
                return SerializeUtils.deserializeFromString(value.toString());
            } else {
                return value;
            }
        }
        return null;
    }

    /**
     * 写入缓存
     *
     * @param cacheName 缓存KEY
     * @param key       键
     * @param value     值
     */
    public static void put(String cacheName, String key, Object value) {
        if (SERIALIZE_ABLE && value != null) {
            getCache(cacheName).put(key, SerializeUtils.serializeToString((Serializable) value));
        } else {
            getCache(cacheName).put(key, value);
        }
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName 缓存KEY
     * @param key       键
     */
    public static void remove(String cacheName, String key) {
        // 从缓存中移除key对应的缓存
        getCache(cacheName).evict(key);
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    /**
     * 获取cache的管理者,目前是ehcache or redis
     *
     * @return 缓存管理器
     */
    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * 获得一个Cache，没有则创建一个。
     *
     * @param cacheName 缓存KEY
     * @return 缓存
     */
    private static Cache getCache(String cacheName) {
        // 防止cache不为空,不设置序列化
        if (cacheManager instanceof RedisCacheManager) {
            SERIALIZE_ABLE = true;
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            if (cacheManager instanceof EhCacheCacheManager) {
                EhCacheCacheManager manager = (EhCacheCacheManager) cacheManager;
                net.sf.ehcache.CacheManager cm = manager.getCacheManager();
                cm.addCache(cacheName);
                cm.getCache(cacheName).getCacheConfiguration().setEternal(false);
                SERIALIZE_ABLE = false;
            } else if (cacheManager instanceof RedisCacheManager) {
                RedisCacheManager manager = (RedisCacheManager) cacheManager;
                manager.setCacheNames(Lists.newArrayList(cacheName));
                SERIALIZE_ABLE = true;
            }
            cache = cacheManager.getCache(cacheName);
        }
        return cache;
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================
}
