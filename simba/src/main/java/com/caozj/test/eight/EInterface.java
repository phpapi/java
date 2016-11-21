package com.caozj.test.eight;

@FunctionalInterface
public interface EInterface {

	String a(String h);

	default void b() {
	}

	default String hello(String w) {
		return "hello " + w;
	}

}
