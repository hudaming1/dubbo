package org.hum.study.extensionloader;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("userService1")
public interface IUserService {

	@Adaptive("sayHello")
	public String sayHello(String name, URL url);
	
	@Activate
	public String register(String name, Integer age);
}
