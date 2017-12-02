package org.hum.study.extensionloader.impl;

import org.hum.study.extensionloader.IUserService;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;

//@Adaptive
public class UserServiceImpl1 implements IUserService {

	@Override
	public String sayHello(String name, URL url) {
		return "hello " + name;
	}

	@Override
	public String register(String name, Integer age) {
		return name + "-" + age;
	}
}
