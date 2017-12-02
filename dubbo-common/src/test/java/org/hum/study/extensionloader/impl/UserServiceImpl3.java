package org.hum.study.extensionloader.impl;

import org.hum.study.extensionloader.IUserService;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;

@Activate(group = { "group1", "group2" })
public class UserServiceImpl3 implements IUserService {

	@Override
	public String sayHello(String name, URL url) {
		return "haha " + name;
	}

	@Override
	public String register(String name, Integer age) {
		return name + " " + age;
	}
}
