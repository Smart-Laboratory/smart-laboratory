package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart.model.Dictionary;
import com.smart.service.DictionaryManager;

public class PatientUtil {

	private static PatientUtil instance = new PatientUtil();
	
	private List<Dictionary> patientInfo = null;
	private Map<String, String> map1 = new HashMap<String, String>();
	private Map<Long, Dictionary> map2 = new HashMap<Long, Dictionary>();
	
	private PatientUtil() {}
	
	public static PatientUtil getInstance() {
		return instance;
	}
	
	synchronized private void initMap(DictionaryManager manager) {
		if (patientInfo != null) {
			return;
		}
		if (patientInfo == null) {
			patientInfo = manager.getPatientInfo("");
		}
		for (Dictionary p : patientInfo) {
			map1.put(p.getSign(), p.getValue());
			map2.put(p.getId(), p);
		}
	}
	
	public Dictionary getInfo(Long id, DictionaryManager manager) {
		if (patientInfo == null) {
			initMap(manager);
		}
		if (map2.containsKey(id)) {
			return map2.get(id);
		} else {
			return null;
		}
	}
	
	public String getValue(String sign, DictionaryManager manager) {
		if (patientInfo == null) {
			initMap(manager);
		}
		if (map1.containsKey(sign)) {
			return map1.get(sign);
		} else {
			return null;
		}
	}
	
}
