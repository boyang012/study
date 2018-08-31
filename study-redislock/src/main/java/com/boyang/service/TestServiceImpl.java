package com.boyang.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.boyang.DB;
import com.boyang.redis.lock.annotation.RedisLock;

@Service
public class TestServiceImpl implements TestService{
	@Override
	public Map<String,String> getData(){
		return DB.map;
	}

	@RedisLock(key = DB.Redis.LOCK_KEY, expire = DB.Redis.LOCK_EXPIRE, tryTimeout = DB.Redis.LOCK_TIME_OUT)
	@Override
	public boolean modify(String uname, String uAge) {
		// TODO Auto-generated method stub
		System.out.println("输入参数uname:"+uname+"  uAge:"+uAge);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		DB.map.put("uname",uname);
		DB.map.put("uage", uAge);
		return true;
	}
}
