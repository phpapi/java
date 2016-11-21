package com.caozj.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.caozj.framework.util.data.ThreadDataUtil;

@Service
public class TestService {

  private static final Log logger = LogFactory.getLog(TestService.class);

  @Cacheable(value = "default")
  public int testCache4(String info) {
    logger.info("receive==>" + info);
    return 1;
  }

  @Cacheable(value = "default")
  public int testCache3(String info) {
    logger.info("receive==>" + info);
    return 1;
  }

  @Cacheable(value = "default", key = "#root.method.name.toString().concat(#root.targetClass.toString()).concat(#p0)")
  public String testCache2(String info) {
    logger.info("receive==>" + info);
    return "test:" + info;
  }

  @Cacheable(value = "default", key = "#root.method.name.toString().concat(#root.targetClass.toString()).concat(#p0)")
  public int testCache(String info) {
    logger.info("receive==>" + info);
    return 1;
  }

  @CacheEvict(value = "default", allEntries = true)
  public void clearCache() {

  }

  @CacheEvict(value = "default")
  public void clear(String info) {

  }

  @Cacheable(value = "redis")
  public int testRedisCache(String info) {
    logger.info("redis receive==>" + info);
    return 1;
  }

  @CacheEvict(value = "redis", allEntries = true)
  public void clearRedisCache() {

  }

  @Async
  public void async() {
    logger.info("async==>async");
  }

  public String threadData() {
    return (String) ThreadDataUtil.get("test");
  }

}
