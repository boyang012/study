package com.study.rpc.framework.consumer;

import com.study.rpc.framework.RpcFramework;
import com.study.rpc.framework.service.HellowordService;

public class RpcConsumer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HellowordService service = RpcFramework.refer(HellowordService.class, "127.0.0.1", 8080);  
		service.sayHello();
	}

}
