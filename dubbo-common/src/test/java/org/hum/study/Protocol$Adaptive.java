//package org.hum.study;
//
//import com.alibaba.dubbo.common.extension.ExtensionLoader;
//
//public class Protocol$Adaptive implements com.alibaba.dubbo.rpc.Protocol {
//	public void setPropertyValue(Object o, String n, Object v) {
//		com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl w;
//		try {
//			w = ((com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl) $1);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException(e);
//		}
//		throw new com.alibaba.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2
//				+ "\" filed or setter method in class com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl.");
//	}
//
//	public Object getPropertyValue(Object o, String n) {
//		com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl w;
//		try {
//			w = ((com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl) $1);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException(e);
//		}
//		if ($2.equals("called")) {
//			return ($w) w.isCalled();
//		}
//		throw new com.alibaba.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2
//				+ "\" filed or setter method in class com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl.");
//	}
//
//	public Object invokeMethod(Object o, String n, Class[] p, Object[] v)
//			throws java.lang.reflect.InvocationTargetException {
//		com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl w;
//		try {
//			w = ((com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl) $1);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException(e);
//		}
//		try {
//			if ("sayHello".equals($2) && $3.length == 1) {
//				return ($w) w.sayHello((java.lang.String) $4[0]);
//			}
//			if ("isCalled".equals($2) && $3.length == 0) {
//				return ($w) w.isCalled();
//			}
//			if ("timeOut".equals($2) && $3.length == 1) {
//				w.timeOut(((Number) $4[0]).intValue());
//				return null;
//			}
//			if ("customException".equals($2) && $3.length == 0) {
//				return ($w) w.customException();
//			}
//		} catch (Throwable e) {
//			throw new java.lang.reflect.InvocationTargetException(e);
//		}
//		throw new com.alibaba.dubbo.common.bytecode.NoSuchMethodException(
//				"Not found method \"" + $2 + "\" in class com.alibaba.dubbo.rpc.protocol.hessian.HessianServiceImpl.");
//	}
//}