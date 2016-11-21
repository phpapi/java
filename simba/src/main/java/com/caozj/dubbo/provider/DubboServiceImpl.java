package com.caozj.dubbo.provider;

import org.springframework.stereotype.Service;

@Service("dubboService")
public class DubboServiceImpl implements DubboServiceInterface {

	@Override
	public String test(String content) {
		return "hello " + content;
	}

}
