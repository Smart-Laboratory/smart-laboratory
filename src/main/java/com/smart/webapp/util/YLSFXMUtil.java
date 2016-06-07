package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.request.SFXM;
import com.smart.service.request.SFXMManager;

public class YLSFXMUtil {
	
	private static YLSFXMUtil instance = new YLSFXMUtil();
	private static Map<String, SFXM> map = null;

	private YLSFXMUtil() {}
	
	public static YLSFXMUtil getInstance(SFXMManager sfxmManager) {
		if (map == null) {
			map = new HashMap<String, SFXM>();
			for (SFXM s : sfxmManager.getAll()) {
				map.put("" + s.getId(), s);
			}
		}
		return instance;
	}
	
	public SFXM getSFXM(String key) {
		return map.get(key);
	}

}
