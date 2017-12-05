/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.protocol.hessian;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl.MyException;

import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.io.IOException;

/**
 * HessianProtocolTest
 *
 * @author william.liangf
 */
public class HessianProtocolTest {

    @Test
    public void test() throws IOException {
        HessianServiceImpl serviceImpl = new HessianServiceImpl();
        /**
         * 发布服务
         * 	1.先考虑通过什么协议？这里用的是Hessian协议
         * 	2.通过URL方式选择配置组件
         */
        // 通过ExtensionLoader，先获得ProtocolFactory，然后获得实例代理
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("hessian://127.0.0.1:5342/" + HessianService.class.getName() + "?timeout=11111111&version=1.0.0&proxy=jdk");
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        
        Invoker<HessianService> serviceInvoker = proxyFactory.getInvoker(serviceImpl, HessianService.class, url);
        Exporter<HessianService> exporter = protocol.export(serviceInvoker);
        
        
        Invoker<HessianService> invoker = protocol.refer(HessianService.class, url);
        HessianService client = proxyFactory.getProxy(invoker);
        System.out.println(client.sayHello("haha"));
        System.out.println(client.sayHello(111));
        System.in.read();
//        invoker.destroy();
//        exporter.unexport();
        
    }
    
    @Test
    public void testHessianProtocol() {
        HessianServiceImpl serviceImpl = new HessianServiceImpl();
        Assert.assertFalse(serviceImpl.isCalled());
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("hessian://127.0.0.1:5342/" + HessianService.class.getName() + "?version=1.0.0&proxy=jdk");
        Invoker<HessianService> serviceInvoker = proxyFactory.getInvoker(serviceImpl, HessianService.class, url);
        Exporter<HessianService> exporter = protocol.export(serviceInvoker);
        Invoker<HessianService> invoker = protocol.refer(HessianService.class, url);
        HessianService client = proxyFactory.getProxy(invoker);
        String result = client.sayHello("haha");
        Assert.assertTrue(serviceImpl.isCalled());
        Assert.assertEquals("Hello, haha", result);
        invoker.destroy();
        exporter.unexport();
    }

    @Test
    public void testHttpClient() {
        HessianServiceImpl server = new HessianServiceImpl();
        Assert.assertFalse(server.isCalled());
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("hessian://127.0.0.1:5342/" + HessianService.class.getName() + "?version=1.0.0&client=httpclient");
        Exporter<HessianService> exporter = protocol.export(proxyFactory.getInvoker(server, HessianService.class, url));
        Invoker<HessianService> invoker = protocol.refer(HessianService.class, url);
        HessianService client = proxyFactory.getProxy(invoker);
        String result = client.sayHello("haha");
        Assert.assertTrue(server.isCalled());
        Assert.assertEquals("Hello, haha", result);
        invoker.destroy();
        exporter.unexport();
    }

    @Test
    public void testTimeOut() {
        HessianServiceImpl server = new HessianServiceImpl();
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("hessian://127.0.0.1:5342/" + HessianService.class.getName() + "?version=1.0.0&timeout=10");
        Exporter<HessianService> exporter = protocol.export(proxyFactory.getInvoker(server, HessianService.class, url));
        Invoker<HessianService> invoker = protocol.refer(HessianService.class, url);
        HessianService client = proxyFactory.getProxy(invoker);
        try {
            client.timeOut(6000);
            fail();
        } catch (RpcException expected) {
            Assert.assertEquals(true, expected.isTimeout());
        } finally {
            invoker.destroy();
            exporter.unexport();
        }

    }

    @Test
    public void testCustomException() {
        HessianServiceImpl server = new HessianServiceImpl();
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = URL.valueOf("hessian://127.0.0.1:5342/" + HessianService.class.getName() + "?version=1.0.0");
        Exporter<HessianService> exporter = protocol.export(proxyFactory.getInvoker(server, HessianService.class, url));
        Invoker<HessianService> invoker = protocol.refer(HessianService.class, url);
        HessianService client = proxyFactory.getProxy(invoker);
        try {
            client.customException();
            fail();
        } catch (MyException expected) {
        }
        invoker.destroy();
        exporter.unexport();
    }

}