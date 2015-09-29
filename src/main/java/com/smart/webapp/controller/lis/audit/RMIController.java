package com.smart.webapp.controller.lis.audit;

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

import com.zju.api.model.Ksdm;
import com.zju.api.service.RMIService;


@Controller
@RequestMapping("/rmi*")
public class RMIController {
	
	@Autowired
	private RMIService rmiService = null;

	@RequestMapping(value = "/ajax/searchSection*", method = { RequestMethod.GET })
	@ResponseBody
	public String searchSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Ksdm> list = rmiService.searchSection(name.toUpperCase());
		JSONArray array = new JSONArray();
		if (list != null) {
			for (Ksdm s : list) {
				JSONObject o = new JSONObject();
				o.put("id", s.getId());
				o.put("name", s.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
}
