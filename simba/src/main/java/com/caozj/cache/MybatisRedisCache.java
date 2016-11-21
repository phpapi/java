package com.caozj.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;

import com.caozj.common.CustomizedPropertyPlaceholderConfigurer;
import com.caozj.framework.util.common.SerializeUtil;
import com.caozj.model.constant.ConstantData;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 使用Redis实现自定义的Mybatis二级缓存
 * 
 * @author caozj
 *
 */
public class MybatisRedisCache implements Cache {

	private static final Log logger = LogFactory.getLog(MybatisRedisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;

	private static JedisPool pool = null;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
		this.id = id;
		if (pool != null) {
			return;
		}
		// 初始化redis连接池
		String redisHost = CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.host");
		int redisPort = NumberUtils.toInt(CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.port"), 6379);
		String redisPassword = CustomizedPropertyPlaceholderConfigurer.getContextProperty("redis.password");
		int timeout = ConstantData.REDIS_TOMEOUT;
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(200);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(1000 * 30);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		if (StringUtils.isEmpty(redisPassword)) {
			pool = new JedisPool(config, redisHost, redisPort, timeout);
		} else {
			pool = new JedisPool(config, redisHost, redisPort, timeout, redisPassword);
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void putObject(Object key, Object value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		} finally {
			close(jedis);
		}
	}

	@Override
	public Object getObject(Object key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			byte[] data = jedis.get(SerializeUtil.serialize(key));
			if (data == null || data.length <= 0) {
				return null;
			}
			return SerializeUtil.unserialize(data);
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		} finally {
			close(jedis);
		}
	}

	@Override
	public Object removeObject(Object key) {
		Jedis jedis = null;
		Object object = null;
		try {
			jedis = pool.getResource();
			byte[] data = jedis.get(SerializeUtil.serialize(key));
			if (data != null && data.length > 0) {
				object = SerializeUtil.unserialize(data);
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		} finally {
			close(jedis);
		}
		return object;
	}

	@Override
	public void clear() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.flushDB();
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		} finally {
			close(jedis);
		}
	}

	@Override
	public int getSize() {
		int size = 0;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			size = NumberUtils.toInt(jedis.dbSize() + "");
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		} finally {
			close(jedis);
		}
		return size;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	private void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}
