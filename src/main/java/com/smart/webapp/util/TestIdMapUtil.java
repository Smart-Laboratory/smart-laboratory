package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.rule.Index;
import com.smart.service.rule.IndexManager;

public class TestIdMapUtil {
	private Map<String, Index> idMap = new HashMap<String,Index>();
	
	private static TestIdMapUtil instance = new TestIdMapUtil();
	
	public TestIdMapUtil() {}

    public  TestIdMapUtil getInstance() {
		if(instance.idMap == null){
			synchronized (instance) {
				initMap();
			}
		}
		return instance;
	}
	
	public  Map<String, Index> initMap(){
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
		return idMap;
	}
	
	@Autowired
	private  IndexManager indexManager;
}
