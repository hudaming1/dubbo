package com.alibaba.dubbo.rpc.protocol.http2.test.service;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "hello " + name;
	}

	@Override
	public String sayHello(int name) {
		return "hi " + name;
	}
}
