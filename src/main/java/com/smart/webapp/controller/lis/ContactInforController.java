package com.smart.webapp.controller.lis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.ContactInfor;
import com.smart.service.lis.ContactManager;

@Controller
@RequestMapping("/contact*")
public class ContactInforController {
	
	@Autowired
	private ContactManager contactManager = null;
	
	@RequestMapping(value = "/ajax/search*", method = { RequestMethod.GET })
	@ResponseBody
	public String searchContactInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<ContactInfor> list = contactManager.searchContact(name.toUpperCase());
		JSONArray array = new JSONArray();
		if (list != null) {
			for (ContactInfor ci : list) {
				JSONObject o = new JSONObject();
				o.put("id", ci.getWORKID());
				o.put("name", ci.getNAME());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}

}
