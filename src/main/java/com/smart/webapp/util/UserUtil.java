package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.user.User;
import com.smart.service.UserManager;

public class UserUtil {
	
	private static UserUtil instance = new UserUtil();
	private static Map<String, String> map = null;
	
	private UserUtil() {}
	
	public static UserUtil getInstance(UserManager userManager) {
		if (map == null) {
			map = new HashMap<String, String>();
			for (User s : userManager.getAll()) {
				map.put(s.getUsername(), s.getName());
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
	
	public String getKey(String value) {
		for(String name: map.keySet()) {
		    if(value.equals(map.get(name))) {
		         return name;
		    }
		}
		return value;
	}

}
