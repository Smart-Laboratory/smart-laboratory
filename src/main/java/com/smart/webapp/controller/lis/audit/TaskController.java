package com.smart.webapp.controller.lis.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.drools.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.smart.webapp.util.TaskManagerUtil;
import com.smart.Constants;
import com.smart.model.lis.Task;

@Controller
@RequestMapping("/task*")
public class TaskController {
	
	@RequestMapping(value = "/ajax/cancel*", method = RequestMethod.GET)
	public void stopThread(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			TaskManagerUtil.getInstance().stopThread(Long.parseLong(id));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/audit*", method = RequestMethod.GET)
	public void getAuditProgress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		JSONArray array = new JSONArray();
		for (Task t : manager.getTaskList()) {
			if (request.getRemoteUser().equals(t.getStartBy())) {
				JSONObject obj = new JSONObject();
				obj.put("id", t.getId());
				obj.put("status", t.getStatus());
				obj.put("value", (int)(t.getProValue() * 100));
				obj.put("text", t.getSearchText());
				obj.put("ratio", t.getFinishCount() + "/" + t.getSampleCount());
				obj.put("start", Constants.DF6.format(t.getStartTime()));
				if (t.getEndTime() == null) {
					obj.put("end", "");
				} else {
					obj.put("end", Constants.DF6.format(t.getEndTime()));
				}
				array.put(obj);
			}
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
}
