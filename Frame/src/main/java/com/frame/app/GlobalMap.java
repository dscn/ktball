package com.frame.app;

import java.util.HashMap;

public class GlobalMap {
	
	public static HashMap<String, Object> map = new HashMap<String, Object>();
	
	private GlobalMap() {
		super();
	}

	private static GlobalMap instance = new GlobalMap();
	
	public static GlobalMap getInstance() {
		return instance;
	}
	
	public static void put(String k, Object v) {
		map.put(k, v);
	}
	
	public static Object get(String k) {
		Object obj = map.get(k);
		return obj;
	}
	
}
