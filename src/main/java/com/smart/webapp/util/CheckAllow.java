package com.smart.webapp.util;

import com.smart.model.rule.Description;
import com.smart.model.rule.Index;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.user.Role;
import com.smart.model.user.User;


public class CheckAllow {

	static public boolean allow(Rule rule, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (!rule.isCore() && rule.getCreateUser() != null) {
			if (rule.getCreateUser().equals(user.getName())) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean allow(Index index, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (index.getCreateUser() != null) {
			if (index.getCreateUser().equals(user.getName())) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean allow(Result result, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (result.getCreateUser() != null) {
			if (result.getCreateUser().equals(user.getName())) {
				flag = true;
			}
		}

		return flag;
	}
	
	static public boolean isAdmin(User user) {
		
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				return true;
			}
		}
		return false;
	}

	static public boolean allow(Description index, User user) {

		boolean flag = false;
		for (Role role : user.getRoles()) {
			if ("ROLE_ADMIN".equals(role.getName())) {
				flag = true;
			}
		}
		if (index.getCreateUser() != null) {
			if (index.getCreateUser() == user.getUsername()) {
				flag = true;
			}
		}

		return flag;
	}
}
