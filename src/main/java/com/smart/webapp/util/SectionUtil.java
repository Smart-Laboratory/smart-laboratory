package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.lisservice.WebService;
import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;
import com.zju.api.model.Ksdm;
import com.zju.api.service.RMIService;

public class SectionUtil {

	private static SectionUtil instance = new SectionUtil();
	private static Map<String, String> map = null;
	private static Map<String, String> labMap = null;
	
	private SectionUtil () {}
	
	//public static SectionUtil getInstance(SyncManager manager) {
	public static SectionUtil getInstance(RMIService rmi, SectionManager sectionManager) {
		if (map == null || map.size() == 0) {
			map = new HashMap<String, String>();
			for(Section s : new WebService().getSectionList()) {
				map.put(s.getCode(), s.getName());
			}
		}
		if (labMap == null || labMap.size() == 0) {
			labMap = new HashMap<String, String>();
			for (Section s : sectionManager.getAll()) {
				labMap.put(s.getCode(), s.getName());
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
	
	public String getLabValue(String key) {
		if (labMap.containsKey(key)) {
			return labMap.get(key);
		} else {
			return key;
		}
	}
	
	public String getLabKey(String value) {
		for(String ks: labMap.keySet()) {
		    if(value.equals(labMap.get(ks))) {
		         return ks;
		    }
		}
		return value;
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
