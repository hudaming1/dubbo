package org.hum.study.extensionloader.impl;

import org.hum.study.extensionloader.IUserService;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;

@Activate(group = { "group1" }, value = "g")
public class UserServiceImpl2 implements IUserService {

	@Override
	public String sayHello(String name, URL url) {
		if (url.getParameter("g") != null) {
			return "wakaka " + name;
		}
		return "hi " + name;
	}

	@Override
	public String register(String name, Integer age) {
		return name + ":" + age;
	}
}
