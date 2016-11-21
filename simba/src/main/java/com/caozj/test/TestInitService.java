package com.caozj.test;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * 测试使用
 * 
 * @author caozj
 *
 */
@Service
public class TestInitService {



  @PostConstruct
  private void init() {}
}
