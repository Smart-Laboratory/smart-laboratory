package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.command.GetDefaultValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;

public class instanceTest {
	

	private static Map<String, String> map = new HashMap<String, String>();
	
	public instanceTest(){
	}
	
	private static instanceTest instance = new instanceTest();
	
	public static instanceTest getInstance(SectionManager sectionManager){
		if(instance==null || map==null || map.isEmpty()){
			List<Section> list = sectionManager.getAll();
			for(Section section : list){
				map.put(section.getCode(), section.getName());
			}
		}
		return instance;
	}
	
	
	public String getValue(String key){
		return map.get(key);
	}
	
}
