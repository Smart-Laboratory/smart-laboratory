package com.smart.webapp.controller.set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.model.lis.Ylxh;
import com.smart.webapp.util.DataResponse;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;

import java.util.List;

@Controller
@RequestMapping("/set/ylsf*")
public class YlsfController extends BaseAuditController {
	
	private static Log log = LogFactory.getLog(YlsfController.class);
	@Autowired
	private SectionManager sectionManager;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Section> section = sectionManager.getAll();
		Map<String, String> depart = new HashMap<String, String>();
		if (section != null && section.size()>0) {
			for (Section s : section) {
				depart.put(s.getCode(), s.getName());
			}
		}
		request.setAttribute("departList", depart);
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String lab = request.getParameter("lab");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Ylxh> list = ylxhManager.getTest(lab);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			Ylxh y = list.get(start + index);
			map.put("id", y.getYlxh());
			map.put("ylmc", y.getYlmc());
			map.put("ptest", y.getProfiletest());
			
			dataRows.add(map);
			index++;
		}

		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(value = "/ajax/ylsfList*", method = RequestMethod.GET)
	@ResponseBody
	public String getYlshList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String lab = request.getParameter("lab");
		
		List<Ylxh> list = ylxhManager.getTest(lab);
		for(Ylxh y : list){
			if(y.getProfiletest()==null)
				list.remove(y);
		};
		JSONArray array = new JSONArray();
		for (Ylxh y : list) {
			JSONObject obj = new JSONObject();
			obj.put("ksdm", y.getKsdm());
			obj.put("ylmc", y.getYlmc());
			obj.put("test", y.getProfiletest());
			array.put(obj);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/test*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTestList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String xhStr = request.getParameter("ylsf");
		long ylxh = Long.parseLong(xhStr);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		Ylxh y = ylxhManager.get(ylxh);
		int size = 0;
		if(y.getProfiletest() != null) {
			for (String s : y.getProfiletest().split(",")) {
				if (s != null && idMap.containsKey(s)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", s);
					map.put("name", idMap.get(s) == null ? s : idMap.get(s).getName());
					map.put("english", idMap.get(s).getEnglish()==null ? "":idMap.get(s).getEnglish());
					dataRows.add(map);
					size++;
				}
			}
		}
		
		dataResponse.setRecords(size);
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/test2*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTestList2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String xhStr = request.getParameter("ylsf");
		long ylxh = Long.parseLong(xhStr);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		Ylxh y = ylxhManager.get(ylxh);
		int size = 0;
		if(y.getProfiletest2() != null) {
			for (String s : y.getProfiletest2().split(",")) {
				if (s != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", s);
					map.put("name", idMap.get(s) == null ? s : idMap.get(s).getName());
					map.put("english", idMap.get(s).getEnglish());
					dataRows.add(map);
					size++;
				}
			}
		}
		
		dataResponse.setRecords(size);
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String addTest = request.getParameter("add");
			long ylxh = Long.parseLong(request.getParameter("id"));
			if(addTest==null)
				return false;
			Ylxh y = ylxhManager.get(ylxh);
			if (y.getProfiletest() != null) {
				y.setProfiletest(y.getProfiletest() + addTest + ",");
			} else {
				y.setProfiletest(addTest + ",");
			}
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/add2*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addTest2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String addTest = request.getParameter("add");
			long ylxh = Long.parseLong(request.getParameter("id"));
			if(addTest==null)
				return false;
			Ylxh y = ylxhManager.get(ylxh);
			if (y.getProfiletest2() != null) {
				y.setProfiletest2(y.getProfiletest2() + addTest + ",");
			} else {
				y.setProfiletest2(addTest + ",");
			}
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/delete*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String deleteTest = request.getParameter("del");
			long ylxh = Long.parseLong(request.getParameter("id"));
			Ylxh y = ylxhManager.get(ylxh);
			y.setProfiletest(y.getProfiletest().replace(deleteTest +",", ""));
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/delete2*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteTest2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String deleteTest = request.getParameter("del");
			long ylxh = Long.parseLong(request.getParameter("id"));
			Ylxh y = ylxhManager.get(ylxh);
			y.setProfiletest2(y.getProfiletest2().replace(deleteTest +",", ""));
			ylxhManager.save(y);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/search*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSearchData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String text = request.getParameter("text");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		if (idMap.size() == 0)
			initMap();

		DataResponse dataResponse = new DataResponse();
		List<Ylxh> list = ylxhManager.getSearchData(text);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			Ylxh y = list.get(start + index);
			map.put("id", y.getYlxh());
			map.put("ylmc", y.getYlmc());
			map.put("ptest", y.getProfiletest());
			
			dataRows.add(map);
			index++;
		}

		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/ajax/profileTest*", method = RequestMethod.POST)
	@ResponseBody
	public void getProfileTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = request.getParameter("sample");
		String test = request.getParameter("test");
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		operator.setLastProfile(test);
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
}
