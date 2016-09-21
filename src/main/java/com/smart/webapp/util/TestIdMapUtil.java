package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.rule.Index;
import com.smart.service.rule.IndexManager;

public class TestIdMapUtil {
	private static Map<String, Index> idMap = null;
	private static Map<String, String> nameMap = null;
	
	private static TestIdMapUtil instance = new TestIdMapUtil();
	
	private TestIdMapUtil() {}

<<<<<<< HEAD
    public static synchronized TestIdMapUtil getInstance(IndexManager indexManager) {
		if(instance.idMap == null || instance.nameMap == null){
			List<Index> list = indexManager.getAll();
			idMap = new HashMap<String, Index>();
			nameMap = new HashMap<String, String>();
			for (Index t : list) {
				idMap.put(t.getIndexId(), t);
				nameMap.put(t.getIndexId(), t.getName());
=======
    public  static TestIdMapUtil getInstance() {
		if(instance.idMap == null){
			synchronized (instance) {
				instance.initMap();
>>>>>>> origin/master
			}
		}
		return instance;
	}

<<<<<<< HEAD
	public Map<String, Index> getIdMap() {
=======
	public  Map<String, Index> initMap(){
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
>>>>>>> origin/master
		return idMap;
	}

	public Map<String, String> getNameMap() {
		return nameMap;
	}
}
