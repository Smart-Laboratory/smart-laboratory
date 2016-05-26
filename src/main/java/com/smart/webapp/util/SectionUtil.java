package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.zju.api.model.Ksdm;
import com.zju.api.service.RMIService;

public class SectionUtil {

	private static SectionUtil instance = new SectionUtil();
	private static Map<String, String> map = null;
	
	private SectionUtil () {}
	
	//public static SectionUtil getInstance(SyncManager manager) {
	public static SectionUtil getInstance(RMIService rmi) {
		if (map == null) {
			map = new HashMap<String, String>();
			for (Ksdm s : rmi.getAllKsdm()) {
				map.put(s.getId(), s.getName());
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
	
	public String getKey(String value) {
		for(String ks: map.keySet()) {
		    if(value.equals(map.get(ks))) {
		         return ks;
		    }
		}
		return value;
	}
}
