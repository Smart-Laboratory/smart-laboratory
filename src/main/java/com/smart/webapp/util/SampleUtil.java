package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.Dictionary;
import com.smart.service.DictionaryManager;

public class SampleUtil {

	private static SampleUtil instance = new SampleUtil();
	
	private Map<String, String> map = null;
	
	private SampleUtil() {}
	
	public static SampleUtil getInstance() {
		return instance;
	}
	
	public Map<String, String> getSampleList(DictionaryManager dictionaryManager) {
		if (map == null) {
			synchronized (instance) {
				map = new HashMap<String, String>();
				for (Dictionary sample : dictionaryManager.getSampleType()) {
					map.put(sample.getSign(), sample.getValue());
				}
			}
		}
		return map;
	}
}
