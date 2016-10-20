package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.lis.Ylxh;
import com.smart.service.lis.YlxhManager;
import com.smart.util.ConvertUtil;
import com.smart.util.SpringContextUtil;

public class YlxhUtil {

	private static YlxhUtil instance = null;

	private static Map<String, Ylxh> map = null;

	private static YlxhManager ylxhManager = null;

	private YlxhUtil() {
		ylxhManager = (YlxhManager) SpringContextUtil.getBean("ylxhManager");
	}

	public static YlxhUtil getInstance() {
		if(instance == null || instance.map == null) {
			instance = new YlxhUtil();
			instance.map = new HashMap<String, Ylxh>();
			for (Ylxh ylxh : ylxhManager.getAll()) {
				instance.map.put(ConvertUtil.null2String(ylxh.getYlxh()), ylxh);
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
		map.put(ConvertUtil.null2String(ylxh.getYlxh()), ylxh);
	}

}
