package com.alibaba.dubbo.rpc.protocol.http2.test;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.protocol.http2.test.service.HelloService;
import com.alibaba.dubbo.rpc.protocol.http2.test.service.HelloServiceImpl;
import com.esotericsoftware.reflectasm.MethodAccess;

public class Http2Test {

	@Test
	public void testExport() throws IOException {
		Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("http2");
		URL url = URL.valueOf("hessian://127.0.0.1:8000/" + HelloService.class.getName() + "?version=1.0.0&proxy=jdk");
		ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
		Invoker<HelloService> serviceInvoker = proxyFactory.getInvoker(new HelloServiceImpl(), HelloService.class, url);
		protocol.export(serviceInvoker);

		System.in.read();
	}

	public static void main(String[] args) {
		HelloServiceImpl target = new HelloServiceImpl();
		MethodAccess access = MethodAccess.get(HelloService.class);
		Object invoke = access.invoke(target, "sayHello", "33");
		System.out.println(invoke);
	}
}
