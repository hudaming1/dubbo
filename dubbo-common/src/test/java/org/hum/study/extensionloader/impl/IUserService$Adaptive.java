package org.hum.study.extensionloader.impl;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class IUserService$Adaptive implements org.hum.study.extensionloader.IUserService {
	public java.lang.String register(java.lang.String arg0, java.lang.Integer arg1) {
		throw new UnsupportedOperationException(
				"method public abstract java.lang.String org.hum.study.extensionloader.IUserService.register(java.lang.String,java.lang.Integer) of interface org.hum.study.extensionloader.IUserService is not adaptive method!");
	}

	public java.lang.String sayHello(java.lang.String arg0, com.alibaba.dubbo.common.URL arg1) {
		if (arg1 == null)
			throw new IllegalArgumentException("url == null");
		com.alibaba.dubbo.common.URL url = arg1;
		String extName = url.getParameter("sayHello");
		if (extName == null)
			throw new IllegalStateException(
					"Fail to get extension(org.hum.study.extensionloader.IUserService) name from url(" + url.toString()
							+ ") use keys([sayHello])");
		org.hum.study.extensionloader.IUserService extension = (org.hum.study.extensionloader.IUserService) ExtensionLoader
				.getExtensionLoader(org.hum.study.extensionloader.IUserService.class).getExtension(extName);
		return extension.sayHello(arg0, arg1);
	}
}