package com.boyang;

import java.util.HashMap;
import java.util.Map;

public  interface DB {
	public static Map<String,String> map = new HashMap<String,String>(); 
	
	interface Redis{
		public static final String LOCK_KEY  = "lock_key";	//配置key
		public static final long LOCK_EXPIRE  = 10000l;	//配置key
		public static final long LOCK_TIME_OUT  = 10000l;	//配置key
	}
}
