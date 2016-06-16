package com.smart.webapp.controller.lis.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.service.lis.TestModifyManager;
import com.smart.model.lis.TestModify;
import com.smart.webapp.util.FillFieldUtil;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;

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
//		System.out.println("test:"+test);
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
		Process process = processManager.getBySampleId(info.getId());
		json.put("request", process.getRequesttime() == null ? "" : sdf.format(process.getRequesttime()));
		executeTime = process.getExecutetime();
		json.put("execute", process.getExecutetime() == null ? "" : sdf.format(process.getExecutetime()));
		json.put("send", process.getSendtime() == null ? "" : sdf.format(process.getSendtime()));
		json.put("receive", process.getReceivetime() == null ? "" : sdf.format(process.getReceivetime()));
		json.put("ksreceive", process.getKsreceivetime() == null ? "" : sdf.format(process.getKsreceivetime()));
		checkTime = process.getChecktime();
		json.put("audit", process.getChecktime() == null ? "" : sdf.format(process.getChecktime()));
		json.put("auditor", process.getCheckoperator());
		
		Date resultTime = new Date(0);
		for (TestResult result : testResultManager.getPrintTestBySampleNo(info.getSampleNo())) {
			Date meaTime = result.getMeasureTime();
			if (meaTime != null && meaTime.getTime() > resultTime.getTime()) {
				resultTime = meaTime;
			}
		}
		json.put("result", sdf.format(resultTime));
		String diff = "";
		if (executeTime != null && checkTime != null) {
			long df = checkTime.getTime() - executeTime.getTime();
			diff = String.valueOf(df/60000);
		}
		json.put("tat", diff);
		return json.toString();
	}
	/**
	 * 样本添加检验项目
	 * 
	 * 
	 * 张晋南 2016-5-25
	 * 新增tcValues参数，修改user表的last字段
	 * @param request（test-testid:result）
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addProject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String testResults = request.getParameter("test");
			String sample = request.getParameter("sample");
			String[] testResult = testResults.split(";");
			
			String tcValues = request.getParameter("tcValues");
			String[] tcResult = tcValues.split(",");
			System.out.println(tcValues);
			List<Describe> desList = rmiService.getDescribe();
            List<Reference> refList = rmiService.getReference();
            FillFieldUtil fillUtil = FillFieldUtil.getInstance(desList, refList);
            

			Sample info = sampleManager.getBySampleNo(sample);

			for (String test : testResult) {
				String[] idValue = test.split(":");
				if (idValue.length == 2) {
					TestResult nt = new TestResult();
					/*
					 * TestResult tr = testResultManager.get(new TestResultPK(sample, idValue[0])); if (tr != null) { nt
					 * = tr; }
					 */
					nt.setTestId(idValue[0]);
					nt.setSampleNo(sample);
					nt.setTestResult(idValue[1]);
					nt.setOperator(request.getRemoteUser());
					nt.setCorrectFlag("1");
					nt.setMeasureTime(new Date());
					nt.setResultFlag("AAAAAA");
					nt.setEditMark(Constants.ADD_FLAG);
					Describe des = fillUtil.getDescribe(idValue[0]);
					if (des != null) {
						nt.setSampleType(""+ des.getSAMPLETYPE());
						nt.setUnit(des.getUNIT());
					}
					fillUtil.fillResult(nt, info.getCycle(), Integer.parseInt(info.getAge()), info.getSexValue());
					testResultManager.save(nt);
					TestModify testModify = new TestModify();
//					testModify.setModifyTime(new Date());
					testModify.setModifyUser(request.getRemoteUser());
					testModify.setSampleNo(sample);
					testModify.setTestId(idValue[0]);
					testModify.setNewValue(idValue[1]);
					testModify.setType(Constants.ADD);
					testModifyManager.save(testModify);
					info.setModifyFlag(1);
					//sampleManager.save(info);
				}
			}
			//获取user表中常用的检测类型，修改
			String username = request.getRemoteUser();
			User user = userManager.getUserByUsername(username);
			
			if(null!=user){
				for (String tc : tcResult) {
					if(null!=user.getLastProfile()&&!"".equals(user.getLastProfile())){
						if(user.getLastProfile().indexOf(tc)!=-1){
							user.setLastProfile(user.getLastProfile()+tc+",");
						}
					}else{
						user.setLastProfile(tcValues+",");
					}
				}
			}
			userManager.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}

		// System.out.println(testResult);
		return true;
	}
	
	@RequestMapping(value = "/ajax/getDictionaries*", method = RequestMethod.GET)
	@ResponseBody
	public String getDictionaries(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String error = "error";
		String inputValue = request.getParameter("inputValue");
		String indexId = request.getParameter("inputId");
		String labDepartment = request.getParameter("lab");
		List <Index>indexList = new ArrayList<Index>();
		indexList = indexManager.getIndexsByIdandLab(indexId,labDepartment);
		
		JSONArray array = new JSONArray();
		if(null!=indexList&&indexList.size()>0){
			for(Index index : indexList){
				
				String dictionaries = index.getDictionaries();
				if(null!=dictionaries&&!"".equals(dictionaries)){
					if(dictionaries.indexOf(";")!=-1){
						for(String dictionary:dictionaries.split(";")){
							JSONObject obj = new JSONObject();
							String []zd = dictionary.split(":");
							if(zd[0].toUpperCase().indexOf(inputValue.toUpperCase())!=-1){
								obj.put("id", zd[0]);
								obj.put("name", zd[1]);
								array.put(obj);
							}
						}
					}else{
						String []zd = dictionaries.split(":");
						if(zd[0].toUpperCase().indexOf(inputValue.toUpperCase())!=-1){
							JSONObject obj = new JSONObject();
							obj.put("id", zd[0]);
							obj.put("name", zd[1]);
							array.put(obj);
						}
					}
					
				}else{
					return error;
				}
			}
		}else{
			return error;
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

		
	
	@Autowired
	private TestModifyManager testModifyManager;
}
