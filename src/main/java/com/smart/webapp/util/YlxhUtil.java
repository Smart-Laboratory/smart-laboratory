package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.lis.Ylxh;
import com.smart.service.lis.YlxhManager;

public class YlxhUtil {

	private static YlxhUtil instance = new YlxhUtil();

	private static Map<String, Ylxh> map = null;

	private YlxhUtil() {}

	public static YlxhUtil getInstance(YlxhManager ylxhManager) {
		if (map == null) {
			map = new HashMap<String, Ylxh>();
			for (Ylxh ylxh : ylxhManager.getAll()) {
				map.put("" + ylxh.getYlxh(), ylxh);
			}
		}
		return instance;
	}

	public Ylxh getYlxh(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return new Ylxh();
		}
	}

	public Map<String, Ylxh> getMap (){
		return map;
	}
	public static void updateMap(Ylxh ylxh) {
		map.put("" + ylxh.getYlxh(), ylxh);
	}

}
