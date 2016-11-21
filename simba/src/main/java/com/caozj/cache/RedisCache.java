package com.caozj.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import com.caozj.framework.util.common.SerializeUtil;

/**
 * 使用Redis方式集成Spring Cache
 * 
 * @author caozj
 *
 */
public class RedisCache implements Cache {

  private RedisTemplate<String, Object> redisTemplate;

  private String name;

  public RedisTemplate<String, Object> getRedisTemplate() {
    return redisTemplate;
  }

  public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getNativeCache() {
    return this.redisTemplate;
  }

  @Override
  public ValueWrapper get(Object key) {
    final String keyf = (String) key;
    Object object = getFromRedis(keyf);
    return (object != null ? new SimpleValueWrapper(object) : null);
  }

  private Object getFromRedis(final String keyf) {
    return redisTemplate.execute((RedisConnection connection) -> {
      byte[] value = connection.get(keyf.getBytes());
      if (value == null) {
        return null;
      }
      return SerializeUtil.unserialize(value);
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Object key, Class<T> type) {
    final String keyf = (String) key;
    return (T) getFromRedis(keyf);
  }

  @Override
  public void put(Object key, final Object value) {
    final String keyf = (String) key;
    redisTemplate.execute((RedisConnection connection) -> {
      connection.set(keyf.getBytes(), SerializeUtil.serialize(value));
      return null;
    });
  }

  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {
    final String keyf = (String) key;
    Boolean success = redisTemplate.execute((RedisConnection connection) -> {
      return connection.setNX(keyf.getBytes(), SerializeUtil.serialize(value));
    });
    return success ? new SimpleValueWrapper(value) : null;
  }

  @Override
  public void evict(Object key) {
    final String keyf = (String) key;
    redisTemplate.execute((RedisConnection connection) -> {
      connection.del(keyf.getBytes());
      return null;
    });
  }

  @Override
  public void clear() {
    redisTemplate.execute((RedisConnection connection) -> {
      connection.flushDb();
      return null;
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Object key, Callable<T> arg1) {
    final String keyf = (String) key;
    Object object = getFromRedis(keyf);
    return (T) object;
  }

}
