
package com.smart.webapp.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.model.user.User;
import com.smart.service.UserManager;

public class UserUtil {
	
	private static UserUtil instance = new UserUtil();
	private static Map<String, String> map = null;
	private static Map<String, User> usermap = null;
	
	private UserUtil() {}
	
	public static UserUtil getInstance(UserManager userManager) {
		if (map == null || usermap == null) {
			map = new HashMap<String, String>();
			usermap = new HashMap<String, User>();
			for (User s : userManager.getAll()) {
				map.put(s.getUsername(), s.getName());
				usermap.put(s.getUsername(), s);
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
	
	public User getUser(String key) {
		if (usermap.containsKey(key)) {
			return usermap.get(key);
		} else {
			return new User();
		}
	}

	public void updateMap( User user) {
		map.put(user.getUsername(), user.getName());
		usermap.put(user.getUsername(), user);
	}

}
