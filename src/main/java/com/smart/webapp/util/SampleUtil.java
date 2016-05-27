package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.compiler.lang.dsl.DSLMapParser.mapping_file_return;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.smart.model.Dictionary;
import com.smart.service.DictionaryManager;

public class SampleUtil {

	private static SampleUtil instance = new SampleUtil();
	
	private static Map<String, String> map = null;
	
	private SampleUtil() {}
	
	public static SampleUtil getInstance() {
		return instance;
	}
	
	public Map<String, String> getSampleList(DictionaryManager dictionaryManager) {
		if (map == null) {
			synchronized (instance) {
				map = new HashMap<String, String>();
				List<Dictionary> dList = dictionaryManager.getSampleType();
				for (Dictionary sample : dictionaryManager.getSampleType()) {
					map.put(sample.getSign(), sample.getValue());
				}
			}
		}
		return map;
	}
}
