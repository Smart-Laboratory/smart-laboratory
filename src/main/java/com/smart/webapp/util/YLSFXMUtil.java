package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.request.SFXM;
import com.smart.service.request.SFXMManager;

public class YLSFXMUtil {
	
	private static YLSFXMUtil instance = new YLSFXMUtil();
	private static Map<String, String> map = null;

	private YLSFXMUtil() {}
	
	public static YLSFXMUtil getInstance(SFXMManager sfxmManager) {
		if (map == null) {
			map = new HashMap<String, String>();
			for (SFXM s : sfxmManager.getAll()) {
				map.put("" + s.getId(), s.getName());
			}
		}
		return instance;
	}
	
	public String getValue(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return key;
		}
	}

}
