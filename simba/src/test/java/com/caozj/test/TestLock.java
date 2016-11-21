package com.caozj.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.caozj.service.CacheService;

public class TestLock {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/SpringContext.xml");
		final CacheService cs = (CacheService) context.getBean("redisByteService");
		final String key = "key";
		int count = 10000;
		for (int i = 0; i < count; i++) {
			final int t = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean lock = cs.tryLock(key);
					if (lock) {
						System.out.println(t + "抢锁成功");
					}
				}
			}).start();
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cs.releaseLock(key);
	}
}
