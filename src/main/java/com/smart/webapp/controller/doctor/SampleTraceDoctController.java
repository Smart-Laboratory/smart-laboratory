package com.smart.webapp.controller.doctor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Sample;
import com.smart.model.lis.SampleLogistic;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SampleLogisticManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.Ksdm;
import com.zju.api.model.Patient;
import com.zju.api.model.SyncPatient;
import com.zju.api.model.YLSF;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/doctor/sampleTrace*")
public class SampleTraceDoctController {
	
	@Autowired
	private RMIService rmiService = null;
	
	@Autowired
	private SectionManager sectionManager = null;
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}
	
	/**
	 * 根据条件查询该检验人员的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		List<Sample> list = new ArrayList<Sample>();
		List<SampleLogistic> sList = new ArrayList<SampleLogistic>();
		//按地点查询时显示具体时间
		Map<Long, Sample> lMap = new HashMap<Long,Sample>();
		
		switch(type) {
		case 1:
			sList = sampleLogisticManager.getByReceivePoint(from, to, name);
			String docts = "";
			for(SampleLogistic s : sList){
				docts += s.getDoctadviseno() +",";
			}
			if(docts.length()>0){
				docts = docts.substring(0, docts.length()-1);
				list = sampleManager.getByIds(docts);
				for(Sample s : list){
					lMap.put(s.getId(), s);
				}
			}
			break;
		case 2:
			list = sampleManager.getSampleByPatientName(from, to, name);
			break;
		case 3:
			list = sampleManager.getByPatientId(name, "");
			rmiService.getSampleByPid(name);
			break;
		default:
			try {
				Sample s = sampleManager.get(Long.parseLong(name));
				if (s != null) {
					list.add(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		//type==1时，根据接受点显示列表，那么需要显示的是slist
		if(type==1){
			if (sList != null)
				listSize = sList.size();
		}else{
			if (list != null)
				listSize = list.size();
			
		}
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
			if(type==1){
				SampleLogistic sl = sList.get(start + index);
				Sample sample = lMap.get(sl.getDoctadviseno());
				map.put("id", sl.getId());
				map.put("doctadviseno", sl.getDoctadviseno());
				map.put("sample", sample.getSampleNo());
				map.put("examinaim", sample.getInspectionName());
				map.put("operatetime", Constants.SDF.format(sl.getOperatetime()));
			}else{
				Sample info = list.get(start + index);
				map.put("id",info.getId());
				map.put("doctadviseno",info.getId());
				map.put("sample",info.getSampleNo());
				map.put("examinaim", info.getInspectionName());
			}
			
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	
	
	/*public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		
		List<SyncPatient> list = new ArrayList<SyncPatient>();
		switch(type) {
		case 1:
			List<SampleLogistic> sList = sampleLogisticManager.getByReceivePoint(from, to, name);
			String docts = "";
			for(SampleLogistic s : sList){
				docts += s.getDoctadviseno() +",";
			}
			list = rmiService.getSampleBySection(from, to, name);
			break;
		case 2:
			list = rmiService.getSampleByPatientName(from, to, name);
			break;
		case 3:
			rmiService.getSampleByPid(name);
			break;
		default:
			SyncPatient p = rmiService.getSampleByDoct(Long.parseLong(name));
			if (p != null) {
				list.add(p);
			}
			break;
		}
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
			SyncPatient info = list.get(start + index);
			map.put("id",info.getDOCTADVISENO());
			map.put("sample",info.getSAMPLENO());
			map.put("examinaim", info.getEXAMINAIM());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}*/
	
	/**
	 * 获取样本信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPatientInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		if (id == null) {
			throw new NullPointerException();
		}

		SyncPatient p = rmiService.getSampleByDoct(Long.parseLong(id));
		
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
		if (p != null) {
			map.put("id", p.getPATIENTID());
			map.put("name", p.getPATIENTNAME());
			map.put("age", String.valueOf(p.getAge()));
			map.put("diagnostic", p.getDIAGNOSTIC());
			map.put("section", sectionutil.getValue(p.getSECTION()));
			map.put("sjSection", sectionutil.getLabValue(p.getLABDEPARTMENT()));
			YLSF ylsf = rmiService.getYlsf(p.getYLXH());
			if(ylsf != null && ylsf.getHyfl()!=null)
				map.put("cxxx", rmiService.getYlsf(p.getYLXH()).getHyfl());

			map.put("sex", p.getSexValue());
			if (StringUtils.isEmpty(p.getBLH())) {
				List<Patient> list = rmiService.getPatientList("'" + p.getPATIENTID() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", p.getBLH());
			}
			map.put("type",
					SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(p.getSAMPLETYPE())));
			
			List<SampleLogistic> sList = sampleLogisticManager.getByDoctadviseNo(p.getDOCTADVISENO());
			List<Map<String, String>> logisticList = new ArrayList<Map<String,String>>();
			
			for(SampleLogistic s : sList){
				String typeStr = "";
				switch (s.getOperatetype()) {
				case "0":
					typeStr = "标本运输";
					break;
				case "1":
					typeStr = "标本送出";
					break;
				case "2":
					typeStr = "标本送达";
					break;
				default:
					typeStr = "标本运输";
					break;
				}
				Map<String, String> logisticMap = new HashMap<String,String>();
				logisticMap.put("operatetype", typeStr);
				logisticMap.put("operatetime", s.getOperatetime()==null? "" : Constants.DF.format(s.getOperatetime()));
				logisticMap.put("operator", s.getOperator()==null ? "" : s.getOperator());
				logisticMap.put("location", s.getLocation()==null ? "" : s.getLocation());
				logisticList.add(logisticMap);
			}
			
			map.put("logisticList", logisticList);
			map.put("request", p.getREQUESTTIME() == null ? "" : Constants.DF.format(p.getREQUESTTIME()));
			map.put("execute", p.getEXECUTETIME() == null ? "" : Constants.DF.format(p.getEXECUTETIME()));
			map.put("receive", p.getRECEIVETIME() == null ? "" : Constants.DF.format(p.getRECEIVETIME()));
			map.put("audit", p.getCHECKTIME() == null ? "" : Constants.DF.format(p.getCHECKTIME()));
			//标本采集后到科室接收前的物流过程
//			map.put("send", p.getSENDTIME() == null ? "" : Constants.DF.format(p.getSENDTIME()));
//			map.put("sender", p.getSENDER() == null ? "" : p.getSENDER());
			
			map.put("ksreceive", p.getKSRECEIVETIME() == null ? "" : Constants.DF.format(p.getKSRECEIVETIME()));
			map.put("requester", p.getREQUESTER() == null ? "" : p.getREQUESTER());
			map.put("executor", p.getEXECUTOR() == null ? "" : p.getEXECUTOR());
			map.put("receiver", p.getRECEIVER() == null ? "" : p.getRECEIVER());
			
			map.put("ksreceiver", p.getKSRECEIVER() == null ? "" : p.getKSRECEIVER());
			map.put("auditor", p.getCHECKOPERATOR() == null ? "" : p.getCHECKOPERATOR());
			map.put("tester", p.getCHKOPER2() == null ? "" : p.getCHKOPER2());
			String diff = "";
			if (p.getCHECKTIME() != null && p.getEXECUTETIME() != null) {
				long df = p.getCHECKTIME().getTime() - p.getEXECUTETIME().getTime();
				diff = String.valueOf(df / 60000);
			}
			map.put("tat", diff);
			
		}
		return map;
	}

	@RequestMapping(value = "/ajax/searchSection*", method = { RequestMethod.GET })
	@ResponseBody
	public String searchSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<ReceivePoint> rList = receivePointManager.getByName(name);
		JSONArray array = new JSONArray();
		if (rList != null) {
			for (ReceivePoint s : rList) {
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
	@Autowired
	private SampleLogisticManager sampleLogisticManager;
	@Autowired
	private ReceivePointManager receivePointManager;
	@Autowired
	private SampleManager sampleManager;
}
