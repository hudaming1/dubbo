package org.hum.study.extensionloader;

import java.util.List;

import org.junit.Test;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class ExtensionLoaderTest {

	/**
	 * 测试自动适配功能：
	 * 	1.如果在配置文件加载的实现类中，有@Adaptive标记，则返回实例对象
	 * 	2.如果没有任何类打@Adaptive，则会动态生成后缀为$Adaptive的代理类，且通过URL方式找到对应实现
	 * 	3.如果接口类型的某个方法标记了@Adaptive，则也会像②生成$Adaptive代理类，
	 */
	@Test
	public void testAdaptive() {
		IUserService userServiceAdaptive = ExtensionLoader.getExtensionLoader(IUserService.class).getAdaptiveExtension();
		System.out.println(userServiceAdaptive.sayHello("huming", URL.valueOf("dubbo://127.0.0.1/" + IUserService.class.getName())));
	}
	
	/**
	 * 
	 */
	@Test
	public void testActivateGroup() {
		URL url = URL.valueOf("dubbo://127.0.0.1/" + IUserService.class.getName() + "?g=1");
		List<IUserService> userServiceList = ExtensionLoader.getExtensionLoader(IUserService.class).getActivateExtension(url, "g", "group1");
		for (IUserService userService : userServiceList) {
			System.out.println(userService.sayHello("huming", url));
		}
	}
}
