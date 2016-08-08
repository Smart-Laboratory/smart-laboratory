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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.model.lis.Ylxh;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.UserUtil;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;
import com.smart.util.ConvertUtil;

import java.util.List;

@Controller
@RequestMapping("/set/ylsf*")
public class YlsfController extends BaseAuditController {
	
	private static Log log = LogFactory.getLog(YlsfController.class);
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
        String lab = "";
		String department = user.getDepartment();
		if (user.getLastLab() != null) {
			lab = user.getLastLab();
		}
		if (department != null) {
			if(lab.isEmpty()) {
				if(department.indexOf(",") > 0) {
					lab = department.substring(0, department.indexOf(","));
				} else {
					lab = department;
				}
			}
		}
        return new ModelAndView().addObject("lab", lab);
    }
	
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String lab = request.getParameter("lab");
		String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        String query = request.getParameter("query");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        List<Ylxh> list = new ArrayList<Ylxh>();
        int size = 0;
        try{
        	size = ylxhManager.getSizeByLab(query,lab);
        }catch (Exception e){
            e.printStackTrace();
        }
		DataResponse dataResponse = new DataResponse();
		list = ylxhManager.getYlxhByLab(query,lab,start,end,sidx,sord);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
		for(Ylxh y : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ylxh", y.getYlxh());
			map.put("ylmc", y.getYlmc());
			map.put("english", y.getEnglish());
			map.put("mzpb", y.getMzpb());
			map.put("zypb", y.getZypb());
			map.put("mzpbStr", y.getMzpb() == 1 ? "是" : "否");
			map.put("zypbStr", y.getZypb() == 1 ? "是" : "否");
			map.put("price", y.getPrice());
			map.put("qbgdd", y.getQbgdd());
			map.put("qbgsj", y.getQbgsj());
			map.put("yblx", y.getYblx());
			map.put("bbl", y.getBbl());
			map.put("sglx", y.getSglx());
			map.put("ptest", ConvertUtil.null2String(y.getProfiletest()));
			map.put("ptest2", ConvertUtil.null2String(y.getProfiletest2()));
			map.put("ptest3", ConvertUtil.null2String(y.getProfiletest3()));
			dataRows.add(map);
		}

		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/editYlsf*", method = RequestMethod.POST)
	@ResponseBody
	public String editYlxh(@ModelAttribute("ylxh") Ylxh ylxh) throws Exception {
		JSONObject success = new JSONObject();
		try {
			ylxhManager.save(ylxh);
			success.put("success", "0");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			success.put("success", "失败");
		}
		return success.toString();
	}
	
	@RequestMapping(value = "/editProfile*", method = RequestMethod.POST)
	@ResponseBody
	public String editProfile(HttpServletRequest request) throws Exception {
		JSONObject success = new JSONObject();
		try {
			Long ylxh = Long.parseLong(request.getParameter("ylxh"));
			int type = Integer.parseInt(request.getParameter("type"));
			String edit = request.getParameter("edit");
			String indexId = request.getParameter("index");
			Ylxh y = ylxhManager.get(ylxh);
			String profiletest = "";
			if(type == 1) {
				profiletest = ConvertUtil.null2String(y.getProfiletest());
			} else if(type == 2) {
				profiletest = ConvertUtil.null2String(y.getProfiletest2());
			} else {
				profiletest = ConvertUtil.null2String(y.getProfiletest3());
			}
			if(edit.equals("add")) {
				profiletest += indexId + ",";
			} else {
				profiletest = profiletest.replace(indexId + ",", "");
			}
			if(type == 1) {
				y.setProfiletest(profiletest);
			} else if(type == 2) {
				y.setProfiletest2(profiletest);
			} else {
				y.setProfiletest3(profiletest);
			}
			ylxhManager.save(y);
			success.put("success", profiletest);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			success.put("success", "0");
		}
		return success.toString();
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
	/**
	 * 张晋南 2016-05-25
	 * test 用户上一次选择的项目，多次添加时默认上次的项目
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/profileTest*", method = RequestMethod.POST)
	@ResponseBody
	public void getProfileTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = request.getParameter("sample");
		String test = request.getParameter("test");
		if(!"".equals(test)){
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
		}else{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("".toString());
		}
	}
}
