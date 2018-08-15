package com.study.rpc.framework.provider;

import com.study.rpc.framework.RpcFramework;
import com.study.rpc.framework.service.HellowordService;
import com.study.rpc.framework.service.HellowordServiceImpl;

public class RpcProvider {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HellowordService service = new HellowordServiceImpl();  
		RpcFramework.export(service, 8080);
	}

}
