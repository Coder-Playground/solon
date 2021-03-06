package org.noear.solon.core;

/**
 * 缓存服务接口
 *
 * @author noear
 * @since 1.0
 * */
public interface CacheService {
    /**
     * 保存
     */
    void store(String key, Object obj, int seconds);

    /**
     * 获取
     */
    Object get(String key);

    /**
     * 移除
     */
    void remove(String key);
}
