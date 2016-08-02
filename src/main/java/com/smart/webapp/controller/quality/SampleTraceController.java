package com.smart.webapp.controller.quality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.service.DictionaryManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.Patient;
import com.zju.api.model.SyncPatient;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/quality/sampleTrace*")
public class SampleTraceController {
	
	@Autowired
	private RMIService rmiService = null;
	
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
		
		List<SyncPatient> list = new ArrayList<SyncPatient>();
		switch(type) {
		case 1:
			list = rmiService.getSampleByPatientName(from, to, name);
			break;
		case 2:
			list = rmiService.getSampleBySection(from, to, name);
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
	}
	
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
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		if (p != null) {
			map.put("id", p.getPATIENTID());
			map.put("name", p.getPATIENTNAME());
			map.put("age", String.valueOf(p.getAge()));
			map.put("diagnostic", p.getDIAGNOSTIC());
			map.put("section", sectionutil.getValue(p.getSECTION()));
			map.put("sjSection", sectionutil.getValue(p.getLABDEPARTMENT()));
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
			
			map.put("request", p.getREQUESTTIME() == null ? "" : Constants.DF.format(p.getREQUESTTIME()));
			map.put("execute", p.getEXECUTETIME() == null ? "" : Constants.DF.format(p.getEXECUTETIME()));
			map.put("receive", p.getRECEIVETIME() == null ? "" : Constants.DF.format(p.getRECEIVETIME()));
			map.put("audit", p.getCHECKTIME() == null ? "" : Constants.DF.format(p.getCHECKTIME()));
			map.put("send", p.getSENDTIME() == null ? "" : Constants.DF.format(p.getSENDTIME()));
			map.put("ksreceive", p.getKSRECEIVETIME() == null ? "" : Constants.DF.format(p.getKSRECEIVETIME()));
			map.put("requester", p.getREQUESTER() == null ? "" : p.getREQUESTER());
			map.put("executor", p.getEXECUTOR() == null ? "" : p.getEXECUTOR());
			map.put("receiver", p.getRECEIVER() == null ? "" : p.getRECEIVER());
			map.put("sender", p.getSENDER() == null ? "" : p.getSENDER());
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

}
