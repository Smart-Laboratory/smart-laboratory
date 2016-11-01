package com.smart.webapp.controller.qc;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.model.qc.QcData;
import com.smart.model.qc.QcTest;
import com.smart.model.user.User;
import com.smart.service.qc.QcDataManager;
import com.smart.service.qc.QcTestManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.TestIdMapUtil;
import com.smart.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.model.lis.Device;
import com.smart.model.qc.QcBatch;

@Controller
@RequestMapping("/qc/data*")
public class QcDataController {

	@Autowired
	private QcBatchManager qcBatchManager = null;

	@Autowired
	private QcDataManager qcDataManager = null;

	@Autowired
	private QcTestManager qcTestManager = null;
	
	@Autowired
	private DeviceManager deviceManager = null;
	
	@Autowired
	private UserManager userManager = null;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView view = new ModelAndView();
		List<Device> list = 
				deviceManager.getDeviceByLab(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
		view.addObject("today", Constants.DF2.format(new Date()));
		view.addObject("deviceList", list);
		return view;
	}
	
	@RequestMapping(value = "/ajax/getQcBatch*", method = RequestMethod.GET)
	@ResponseBody
	public String getSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String deviceid = request.getParameter("deviceid");
		List<QcBatch> list = qcBatchManager.getByDevice(deviceid);
		JSONArray jsonArray = new JSONArray();
		if(list!=null && list.size()>0){
			for(QcBatch qcBatch: list) {
				JSONObject o = new JSONObject();
				o.put("id", qcBatch.getQcBatch());
				o.put("name",qcBatch.getQcBatchName());
				jsonArray.add(o);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsonArray.toString());
		return null;
	}

	@RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
	@ResponseBody
	public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String deviceId = ConvertUtil.null2String(request.getParameter("deviceid"));
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");
		String qcbatch = ConvertUtil.null2String(request.getParameter("qcbatch"));
		String measuretime = ConvertUtil.null2String(request.getParameter("measuretime"));
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;

		DataResponse dataResponse = new DataResponse();

		int size = 0;
		if(qcbatch.isEmpty())
			return  null;
		size =  qcTestManager.getCount(qcbatch,start, end, sidx, sord);
		dataResponse.setRecords(size);
		List<QcTest> list =  qcTestManager.getDetails(qcbatch, start,end,sidx,sord);
//		List<QcData> qcDatas = qcDataManager
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		Map<String, String> nameMap = TestIdMapUtil.getInstance(indexManager).getNameMap();
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(QcTest info :list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ConvertUtil.null2String(info.getId()));
			map.put("qcBatch", ConvertUtil.null2String(info.getQcBatch()));
			map.put("testid", ConvertUtil.null2String(info.getTestId()));
			map.put("testname", ConvertUtil.null2String(nameMap.get(info.getTestId())));
			map.put("testresult", "");
			map.put("measuretime", Constants.SDF.format(new Date()));
			map.put("qcLevel", ConvertUtil.null2String(""));
			map.put("analyser", ConvertUtil.null2String(request.getRemoteUser()));
			map.put("inquality", ConvertUtil.null2String("1"));
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping( value = "/saveData" ,method = {RequestMethod.GET,RequestMethod.POST} )
	@ResponseBody
	public void saveData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = UserUtil.getInstance().getUser(request.getRemoteUser());
		String deviceId = ConvertUtil.null2String(request.getParameter("deviceid"));
		String qcbatch = ConvertUtil.null2String(request.getParameter("qcbatch"));
		String measuretime = ConvertUtil.null2String(request.getParameter("measuretime"));

		Long id = ConvertUtil.getLongValue("id");
		String testid = ConvertUtil.null2String(request.getParameter("testid"));
		String testresult = ConvertUtil.null2String(request.getParameter("testresult"));
		String analyser = ConvertUtil.null2String(request.getParameter("analyser"));
		String inquality = ConvertUtil.null2String(request.getParameter("inquality"));

		QcData qcData = new QcData();
		if(id>0)
			qcData = qcDataManager.get(id);
		else if(testresult == null)
			return;
		qcData.setQcBatch(qcbatch);
		qcData.setDeviceid(deviceId+"");
		qcData.setMeasuretime(Constants.SDF.parse(measuretime));
		qcData.setTestid(testid);
		qcData.setTestresult(testresult);
		qcData.setAnalyser(analyser);
		qcData.setInquality(inquality);
		qcData.setLabDepart(user.getLastLab());
		qcDataManager.save(qcData);
	}


	@Autowired
	private IndexManager indexManager = null;
}
