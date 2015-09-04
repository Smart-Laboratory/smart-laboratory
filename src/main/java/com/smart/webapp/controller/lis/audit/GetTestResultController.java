package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.webapp.util.DataResponse;
import com.zju.api.model.SyncResult;

@Controller
@RequestMapping("/audit*")
public class GetTestResultController extends BaseAuditController {
	
	/**
	 * 获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse dataResponse = new DataResponse();
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";
		String sameSample = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();
		
		Sample info = sampleManager.getListBySampleNo(sampleNo).get(0);

		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		Map<String, String> resultMap4 = new HashMap<String, String>();
		Map<String, String> resultMap5 = new HashMap<String, String>();
		int isLastYear = 5;
		int isLastTwoYear = 5;
		if (info != null) {
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatient().getBlh());
			Date receivetime = null;
			for(Process process : info.getProcess()) {
				if(process.getOperation().equals(Constants.PROCESS_RECEIVE)) {
					receivetime = process.getTime();
				}
			}
			long curInfoReceiveTime = receivetime.getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);
			
			int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
			if(list!=null && list.size()>0){
			for (Sample pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					String testid = test.getTestId();
					Set<String> sameTests = util.getKeySet(testid);
					sameTests.add(testid);
					for (String id : sameTests) {
						if (testIdSet.contains(id)) {
							isHis = true;
							break;
						}
					}
					if (isHis) {
						break;
					}
				}
				Date preceivetime = null;
				for(Process process : info.getProcess()) {
					if(process.getOperation().equals(Constants.PROCESS_RECEIVE)) {
						preceivetime = process.getTime();
					}
				}
				if (preceivetime == null || pinfo.getSampleNo() == null) {
					continue;
				}
				String pDay = pinfo.getSampleNo().substring(4, 6) + "/" + pinfo.getSampleNo().substring(6, 8);
				int pyear = Integer.parseInt(pinfo.getSampleNo().substring(0, 4));
				if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
					if (index > 4)
						break;
					switch (index) {
					case 0:
						rmap = resultMap1;
						break;
					case 1:
						rmap = resultMap2;
						break;
					case 2:
						rmap = resultMap3;
						break;
					case 3:
						rmap = resultMap4;
						break;
					case 4:
						rmap = resultMap5;
						break;
					}
					for (TestResult result : pinfo.getResults()) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					if(pyear == year) {
						isLastYear--;
					}
					if(pyear >= year-1) {
						isLastTwoYear--;
					}
					hisDate += pDay + ":" + pinfo.getSampleNo();
					index++;
				}
				if (day.equals(pDay) && sameSample(info, pinfo) && pyear == year) {
					if (!"".equals(sameSample)) {
						sameSample += ",";
					}
					sameSample += pinfo.getSampleNo();
				}
			}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<SyncResult> editTests = rmiService.getEditTests(sampleNo);
		Map<String, String> editMap = new HashMap<String, String>();
		if (editTests.size() > 0) {
			for (SyncResult sr : editTests) {
				editMap.put(sr.getTESTID(), sr.getTESTRESULT());
			}
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		for (TestResult tr : info.getResults()) {
			System.out.println(tr.getTestId());
			if (tr.getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = tr.getTestId();
			if (colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
//			if(true){
				String testId = tr.getTestId();
				Set<String> sameTests = util.getKeySet(testId);
				sameTests.add(testId);
				map.put("id", id);
				map.put("color", color);
				map.put("ab", idMap.get(tr.getTestId()).getEnglish());
				map.put("name", idMap.get(tr.getTestId()).getName());
				map.put("result", tr.getTestResult());
				map.put("last","");
				map.put("last1","");
				map.put("last2","");
				map.put("last3","");
				map.put("last4","");
				for(String tid : sameTests) {
					if(map.get("last").equals("")) {
						map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(tid) ? resultMap1.get(tid) : "");
					}
					if(map.get("last1").equals("")) {
						map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(tid) ? resultMap2.get(tid) : "");
					}
					if(map.get("last2").equals("")) {
						map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(tid) ? resultMap3.get(tid) : "");
					}
					if(map.get("last3").equals("")) {
						map.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(tid) ? resultMap4.get(tid) : "");
					}
					if(map.get("last4").equals("")) {
						map.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(tid) ? resultMap5.get(tid) : "");
					}
				}
				map.put("checktime", Constants.DF5.format(tr.getMeasureTime()));
				map.put("device", tr.getOperator());
				String lo = tr.getRefLo();
				String hi = tr.getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", tr.getUnit());
//				map.put("knowledgeName", idMap.get(tr.getTestId()).getKnowledgename());
				map.put("editMark", tr.getEditMark());
				map.put("lastEdit", editMap.size() == 0 || !editMap.containsKey(id) ? "" : "上次结果 " + editMap.get(id));
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		userdata.put("isLastYear", isLastYear);
		userdata.put("isLastTwoYear", isLastTwoYear);
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 分两列获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/twosample*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse[] getSample0(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse[] dataArray = new DataResponse[2];
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";
		String sameSample = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();

		Sample info = sampleManager.getListBySampleNo(sampleNo).get(0);;
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		int isLastYear = 2;
		if (info != null) {
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatient().getBlh());
			Date receivetime = null;
			for(Process process : info.getProcess()) {
				if(process.getOperation().equals(Constants.PROCESS_RECEIVE)) {
					receivetime = process.getTime();
				}
			}
			long curInfoReceiveTime = receivetime.getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);
			for (Sample pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					String testid = test.getTestId();
					Set<String> sameTests = util.getKeySet(testid);
					sameTests.add(testid);
					for (String id : sameTests) {
						if (testIdSet.contains(id)) {
							isHis = true;
							break;
						}
					}
					if (isHis) {
						break;
					}
				}
				int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
				Date preceivetime = null;
				for(Process process : info.getProcess()) {
					if(process.getOperation().equals(Constants.PROCESS_RECEIVE)) {
						preceivetime = process.getTime();
					}
				}
				if (preceivetime == null || pinfo.getSampleNo() == null) {
					continue;
				}
				String pDay = pinfo.getSampleNo().substring(4, 6) + "/" + pinfo.getSampleNo().substring(6, 8);
				int pyear = Integer.parseInt(pinfo.getSampleNo().substring(0, 4));
				if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
					if (index > 2)
						break;
					switch (index) {
					case 0:
						rmap = resultMap1;
						break;
					case 1:
						rmap = resultMap2;
						break;
					}
					for (TestResult result : pinfo.getResults()) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					if(pyear == year) {
						isLastYear--;
					}
					hisDate += pDay + ":" + pinfo.getSampleNo();
					index++;
				}
				if (day.equals(pDay) && sameSample(info, pinfo) && pyear == year) {
					if (!"".equals(sameSample)) {
						sameSample += ",";
					}
					sameSample += pinfo.getSampleNo();
				}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<SyncResult> editTests = rmiService.getEditTests(sampleNo);
		Map<String, String> editMap = new HashMap<String, String>();
		if (editTests.size() > 0) {
			for (SyncResult sr : editTests) {
				editMap.put(sr.getTESTID(), sr.getTESTRESULT());
			}
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataRows2 = new ArrayList<Map<String, Object>>();
		int i = 2;
		for (TestResult tr : info.getResults()) {
			if (tr.getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = tr.getTestId();
			if (colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = tr.getTestId();
				Set<String> sameTests = util.getKeySet(testId);
				sameTests.add(testId);
				map.put("id", id);
				map.put("color", color);
				map.put("ab", idMap.get(tr.getTestId()).getEnglish());
				map.put("name", idMap.get(tr.getTestId()).getName());
				map.put("result", tr.getTestResult());
				map.put("last","");
				map.put("last1","");
				for(String tid : sameTests) {
					if(map.get("last").equals("")) {
						map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(tid) ? resultMap1.get(tid) : "");
					}
					if(map.get("last1").equals("")) {
						map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(tid) ? resultMap2.get(tid) : "");
					}
				}
				map.put("device", tr.getOperator());
				String lo = tr.getRefLo();
				String hi = tr.getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", tr.getUnit());
				map.put("knowledgeName", idMap.get(tr.getTestId()).getKnowledgename());
				map.put("editMark", tr.getEditMark());
				map.put("lastEdit", editMap.size() == 0 || !editMap.containsKey(id) ? "" : "上次结果 " + editMap.get(id));
				if(i%2 == 0) {
					dataRows.add(map);
				} else {
					dataRows2.add(map);
				}
				i++;
			} else {
				continue;
			}

		}
		dataArray[0].setRows(dataRows);
		dataArray[0].setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		userdata.put("isLastYear", isLastYear);
		dataArray[0].setUserdata(userdata);
		dataArray[1].setRows(dataRows2);
		dataArray[1].setRecords(dataRows2.size());
		response.setContentType("text/html;charset=UTF-8");
		return dataArray;
	}

}
