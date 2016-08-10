package com.smart.webapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IndexMapUtil {

	private Map<String, String> map = null;
	
	private static IndexMapUtil instance = new IndexMapUtil();
	
	private IndexMapUtil() {}
	
	public static IndexMapUtil getInstance() {
		if (instance.map == null) {
			synchronized (instance) {
				instance.initMap();
			}
		}
		return instance;
	}
	
	private void initMap() {
		
		map = new HashMap<String, String>();
		String dic = this.getClass().getResource("/").getPath();
		File file = new File(dic + "/map.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] mp = line.split(":");
				map.put(mp[0], mp[1]);
//				System.out.println("mp[]=="+mp[0]+"---"+mp[1]);
				line = reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   指标ID映射
	 * @param indexId  原始指标ID
	 * @return	映射后的指标ID
	 */
	public String getValue(String testId) {
		if (map.containsKey(testId)) {
			return map.get(testId);
		} else {
			return testId;
		}
	}
	
	public Set<String> getKey(String indexId) {
//		String str = indexId;
		Set<String> setr = new HashSet<String>();
		if (map.containsValue(indexId)) {
			for(String s : map.keySet()) {
				if(map.get(s).equals(indexId))
					setr.add(s);
			}
		} 
		else {
			setr.add(indexId);
		}
		return setr;
	}
	
	public Set<String> getKeySet(String indexId) {
		Set<String> set = new HashSet<String>();
		if (map.containsValue(indexId)) {
			for(String s : map.keySet()) {
				if(map.get(s).equals(indexId))
					set.add(s);
			}
		} 
		return set;
	}
	
	public boolean isNeedMap(String indexId) {
        return map.containsKey(indexId);
	}
}

