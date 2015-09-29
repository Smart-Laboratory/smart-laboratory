package com.smart.webapp.controller.lis.audit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;

@Controller
@RequestMapping("/audit*")
public class TestResultAjaxController extends BaseAuditController{

	@RequestMapping(value = "/ajax/profileList*", method = RequestMethod.GET)
	@ResponseBody
	public void getProfileList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String lab = request.getParameter("lab");
//		List<Profile> profileList = rmiService.getProfiles(lab);
//
		JSONArray array = new JSONArray();
//		for (Profile profile : profileList) {
//			JSONObject obj = new JSONObject();
//			obj.put("name", profile.getName());
//			obj.put("describe", profile.getDescribe());
//			obj.put("device", profile.getDeviceId());
//			// System.out.println(profile.getDeviceId() + ":" + profile.getJyz());
//			obj.put("test", profile.getTest());
//			array.put(obj);
//		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
	
	@RequestMapping(value = "/ajax/profileTest*", method = RequestMethod.POST)
	@ResponseBody
	public void getProfileTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = request.getParameter("sample");
		String test = request.getParameter("test");
		User operator =	userManager.getUserByUsername(request.getRemoteUser());
//		operator.setLastProfile(test);
		userManager.saveUser(operator);
		if (test.endsWith(","))
			test = test.substring(0, test.length() - 1);
		if (idMap.size() == 0)
			initMap();
		String[] testId = test.split(",");
		Set<String> testSet = new HashSet<String>();
		List<TestResult> testResult = testResultManager.getTestBySampleNo(sample);
		for (TestResult tr : testResult)
			testSet.add(tr.getTestId());
		JSONArray array = new JSONArray();
		for (String t : testId) {
			if (testSet.contains(t))
				continue;
			String name = idMap.get(t).getName();
			JSONObject obj = new JSONObject();
			obj.put("test", t);
			obj.put("name", name);
			array.put(obj);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
	
	@RequestMapping(value = "/tat*", method = RequestMethod.GET)
	@ResponseBody
	public String getTAT(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

		if (id == null)
			throw new NullPointerException();

		JSONObject json = new JSONObject();
		Sample info = sampleManager.get(Long.parseLong(id));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date executeTime = new Date();
		Date checkTime = new Date();
		for(Process process : info.getProcess()){
			String operation = process.getOperation();
			if(operation.equals(Constants.PROCESS_REQUEST)){
				json.put("request", process.getTime() == null ? "" : sdf.format(process.getTime()));
			}
			else if(operation.equals(Constants.PROCESS_EXECUTE)){
				executeTime = process.getTime();
				json.put("execute", process.getTime() == null ? "" : sdf.format(process.getTime()));
			}
			else if(operation.equals(Constants.PROCESS_SEND)){
				json.put("send", process.getTime() == null ? "" : sdf.format(process.getTime()));
			}
			else if(operation.equals(Constants.PROCESS_RECEIVE)){
				json.put("receive", process.getTime() == null ? "" : sdf.format(process.getTime()));
			}
			else if(operation.equals(Constants.PROCESS_KSRECEIVE)){
				json.put("ksreceive", process.getTime() == null ? "" : sdf.format(process.getTime()));
			}
			else if(operation.equals(Constants.PROCESS_CKECK)){
				checkTime = process.getTime();
				json.put("audit", process.getTime() == null ? "" : sdf.format(process.getTime()));
				json.put("auditor", process.getTime() == null ? "" : process.getTime());
			}
		}
		
		Date resultTime = new Date(0);
		for (TestResult result : info.getResults()) {
			Date meaTime = result.getMeasureTime();
			if (meaTime != null && meaTime.getTime() > resultTime.getTime()) {
				resultTime = meaTime;
			}
		}
		json.put("result", sdf.format(resultTime));
		String diff = "";
		if (executeTime != null && checkTime != null) {
			long df = checkTime.getTime() - executeTime.getTime();
			diff = String.valueOf(df / 60000);
		}
		json.put("tat", diff);
		return json.toString();
	}
}