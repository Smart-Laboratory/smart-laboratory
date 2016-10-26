package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.drools.P;
import com.smart.lisservice.WebService;
import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;
import com.smart.util.ConvertUtil;
import com.zju.api.model.Ksdm;
import com.zju.api.service.RMIService;

public class SectionUtil {

	private static SectionUtil instance = new SectionUtil();
	private static Map<String, String> map = null;
	private static Map<String, String> labMap = null;
	private static Map<String, Section> departMap = null;

	private SectionUtil () {}
	
	//public static SectionUtil getInstance(SyncManager manager) {
	public static SectionUtil getInstance(SectionManager sectionManager) {
		if (map == null || map.size() == 0) {
			map = new HashMap<String, String>();
			for(Section s : new WebService().getSectionList()) {
				map.put(ConvertUtil.null2String(s.getId()), s.getName());
				map.put(s.getCode(), s.getName());
			}
		}
		if (labMap == null || labMap.size() == 0 || departMap == null || departMap.size() == 0) {
			labMap = new HashMap<String, String>();
			departMap = new HashMap<String, Section>();
			for (Section s : sectionManager.getAll()) {
				labMap.put(s.getCode(), s.getName());
				departMap.put(s.getCode(), s);
			}
		}
		return instance;
	}

	public String getValue(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return key;
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

	public Map<String,String> getLabMap() {
		return labMap;
	}

	public void updateSection(Section section) {
		departMap.put(section.getCode(), section);
		labMap.put(section.getCode(), section.getName());
	}

	public void removeSectionFromMap(Section section) {
		departMap.remove(section.getCode());
		labMap.remove(section.getCode());
	}

	public String getLabCode(String code) {
		return ConvertUtil.null2String(departMap.get(code).getSegment());
	}
}
