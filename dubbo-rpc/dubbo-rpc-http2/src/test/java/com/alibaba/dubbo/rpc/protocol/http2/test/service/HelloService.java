package com.alibaba.dubbo.rpc.protocol.http2.test.service;

public interface HelloService {

	public String sayHello(String name);
	
	public String sayHello(int name);
}
