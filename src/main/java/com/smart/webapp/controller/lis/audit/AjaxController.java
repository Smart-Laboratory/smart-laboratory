package com.smart.webapp.controller.lis.audit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.reagent.OutManager;

@Controller
@RequestMapping("/audit/ajax*")
public class AjaxController extends BaseAuditController{
	
	private static final Log log = LogFactory.getLog(AjaxController.class);
	
	@Autowired
	private OutManager outManager;
	
	@RequestMapping(value = "/singleChart*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSingleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String testid = request.getParameter("id");
		String sample = request.getParameter("sample");
		DecimalFormat deFormat = new DecimalFormat("#.##");
		
		Sample info = sampleManager.getBySampleNo(sample);
		Date measuretime = new Date();
		String patientid = info.getPatient().getBlh();
		Set<String> sameTests = new HashSet<String>();
		sameTests.add(testid);
		String tests = sameTests.toString().replace("[", "'").replace("]", "'");
		tests = tests.replaceAll(", ", "','");
		List<TestResult> list = testResultManager.getSingleHistory(tests, info.getPatient().getPatientName(), patientid);
		List<Double> loArr = new ArrayList<Double>(); 
		List<Double> reArr = new ArrayList<Double>();
		List<Double> hiArr = new ArrayList<Double>();
		List<String> timeArr = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(TestResult t: info.getResults()) {
			if (testid.equals(t.getTestId())) {
				measuretime = t.getMeasureTime();
			}
		}
		map.put("hmList", outManager.getLastHMs(testid, measuretime));
		if (idMap.size() == 0)
			initMap();
		
        if(list.size()>1) {
        	if(list.get(0).getUnit() != null && !list.get(0).getUnit().isEmpty() && idMap.containsKey(list.get(0).getTestId())) {
        		map.put("name", idMap.get(list.get(0).getTestId()).getName() + " (" + list.get(0).getUnit() + ")");
        	} else {
        		map.put("name", idMap.get(list.get(0).getTestId()).getName());
        	}
        	int num = list.size();
        	int count = 0;
        	Double average;
    		Double max = 0.0;
    		Double min = 100000.0;
    		Double total = 0.0;
    		Double sd;
    		List<Double> resultList = new ArrayList<Double>();
        	for (int i = 0; i < num; i++) {
        		if(StringUtils.isNumericSpace(list.get(i).getTestResult().replace(".", ""))) {
        			double d = Double.parseDouble(list.get(i).getTestResult());
					if(d > max){
						max = d;
					}
					if(d < min){
						min = d;
					}
					total = total + d;
					count = count +1;
					resultList.add(d);
        			loArr.add(Double.parseDouble(list.get(i).getRefLo()));
            		reArr.add(Double.parseDouble(list.get(i).getTestResult()));
            		hiArr.add(Double.parseDouble(list.get(i).getRefHi()));
            		timeArr.add(Constants.SDF.format(list.get(i).getMeasureTime()));
            	}
        	}
        	map.put("max", max);
        	map.put("min", min);
        	map.put("num", count);
        	if (resultList.size()%2 == 0) {
        		map.put("mid", resultList.get(resultList.size()/2-1));
			} else {
				map.put("mid", resultList.get(resultList.size()/2));
			}
        	
			average = (count == 0 ? 0 : total/count);
			map.put("ave", deFormat.format(average));
			Double variance = 0.0;
			for (Double d : resultList) {
				variance = variance + Math.pow(d-average, 2);
			}
			sd = Math.sqrt(variance/resultList.size());
			map.put("sd", deFormat.format(sd));
			map.put("cov", deFormat.format(sd*100/average));
        }
        map.put("lo", loArr);
        map.put("re", reArr);
        map.put("hi", hiArr);
        map.put("time", timeArr);
        return map;
	}
	
	@RequestMapping(value = "/ajax/relativeTest*", method = RequestMethod.GET)
	@ResponseBody
	public String getRelativeTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sample = request.getParameter("sample");
		String html = "";
		Sample info = sampleManager.getBySampleNo(sample);
		String history = ylxhManager.getRelativeTest(info.getYlxh());
		String[] tests = history.split(",");
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String s : tests) {
			map.put(s, 0);
		}
		if(history == null || history.isEmpty()) {
			return "";
		} else {
			history = history.substring(0, history.length()-1);
			history = "('" + history.replaceAll(",", "','") + "')";
		}
		List<TestResult> hisTests = testResultManager.getRelative(info.getPatientId(), info.getPatient().getBlh(), history);
		if(hisTests.size()>0) {
			if (idMap.size() == 0)
				initMap();
			Map<String, String> htmlMap = new HashMap<String, String>();
			html += "<table>";
			for(int i=0; i<hisTests.size(); i++) {
				TestResult tr = hisTests.get(i);
				if(map.get(tr.getTestId()) < 3) {
					if(htmlMap.containsKey(tr.getSampleNo())) {
						String s = htmlMap.get(tr.getSampleNo()) 
								+ "<td>" + idMap.get(tr.getTestId()).getName() + "</td>"
								+ "<td width='50px;'>" + tr.getTestResult() + "</td>";
						htmlMap.put(tr.getSampleNo(), s);
					} else {
						htmlMap.put(tr.getSampleNo(), 
								"<tr><td>" + tr.getSampleNo() + "</td>"
								+ "<td>" + idMap.get(tr.getTestId()).getName() + "</td>"
								+ "<td width='50px;'>" + tr.getTestResult() + "</td>");
					}
					map.put(tr.getTestId(), map.get(tr.getTestId()) + 1);
				}
			}
			for(String s : htmlMap.keySet()) {
				html += htmlMap.get(s) + "</tr>";
			}
			html += "</table>";
		}
		JSONObject obj = new JSONObject();
		obj.put("html", html);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}

}
