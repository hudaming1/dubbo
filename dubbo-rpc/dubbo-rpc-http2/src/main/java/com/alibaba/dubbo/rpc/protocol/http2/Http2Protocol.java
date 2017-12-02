package com.alibaba.dubbo.rpc.protocol.http2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import com.alibaba.fastjson.JSON;

public class Http2Protocol extends AbstractProxyProtocol {

    private final Map<String, HttpServer> serverMap = new ConcurrentHashMap<String, HttpServer>();

    private final Map<String, ImplWrapper<?>> instanceMapper = new ConcurrentHashMap<String, ImplWrapper<?>>();

    private HttpBinder httpBinder;
    
	public void setHttpBinder(HttpBinder httpBinder) {
		this.httpBinder = httpBinder;
	}

	@Override
	public int getDefaultPort() {
		return 8000;
	}

	@Override
	protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
		String addr = url.getIp() + ":" + url.getPort();
		HttpServer server = serverMap.get(addr);
		if (server == null) {
			server = httpBinder.bind(url, new Http2Handler());
			serverMap.put(addr, server);
		}
        final String path = url.getAbsolutePath();
        instanceMapper.put(path.replaceAll("\\.", "/"), new ImplWrapper<T>(type, impl));
        return new Runnable() {
            public void run() {
            		instanceMapper.remove(path);
            }
        };
	}

	@Override
	protected <T> T doRefer(Class<T> type, URL url) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class Http2Handler implements HttpHandler {

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			String uri = request.getRequestURI();
			ImplWrapper<?> implWrapper = Http2Protocol.this.instanceMapper.get(uri);
			RpcContext.getContext().setRemoteAddress(request.getRemoteAddr(), request.getRemotePort());
			try {
				Object result = implWrapper.invoke(request.getParameterMap());
				printJSON(response, result);
			} catch (Throwable e) {
				throw new ServletException(e);
			}
		}
		
		private void printJSON(HttpServletResponse response, Object obj) {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {  
		        out = response.getWriter();  
		        out.write(JSON.toJSONString(obj));  
		        out.flush();
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {  
		        if (out != null) {  
		            out.close();  
		        }  
		    }  
		}
	}
	
	private static class ImplWrapper<T> {
		public Class<T> interfaceType;
		public Object impl;
		public ImplWrapper(Class<T> interfaceType, Object impl) {
			this.interfaceType = interfaceType;
			this.impl = impl;
		}
		
		public Object invoke(Map parameterMap) {
			
			return "haha";
		}
	}
}
