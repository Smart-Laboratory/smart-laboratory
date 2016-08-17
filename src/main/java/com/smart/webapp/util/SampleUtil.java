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
	
	public static SampleUtil getInstance(DictionaryManager dictionaryManager) {
		if (map == null) {
			map = new HashMap<String, String>();
			List<Dictionary> dList = dictionaryManager.getSampleType();
			for (Dictionary dictionary : dictionaryManager.getSampleType()) {
				map.put(dictionary.getSign(), dictionary.getValue());
			}
		}
		return instance;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public String getValue(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return key;
		}
	}

	public static void updateMap( Dictionary dictionary) {
		map.put(dictionary.getSign(), dictionary.getValue());
	}

	public static void remove( Dictionary dictionary) {
		map.remove(dictionary.getSign());
	}
}
