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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestModify;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.TestModifyManager;
import com.smart.webapp.util.DataResponse;
import com.zju.api.model.SyncResult;


@Controller
@RequestMapping("/audit*")
public class GetTestResultController extends BaseAuditController {
	
	@Autowired
	private TestModifyManager testModifyManager;
	
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
		
		if (deviceMap.size() == 0)
			initDeviceMap();
		
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}
		
		Sample info = sampleManager.getListBySampleNo(sampleNo).get(0);
		Process process = processManager.getBySampleId(info.getId());
		List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleNo);
		
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		Map<String, String> resultMap4 = new HashMap<String, String>();
		Map<String, String> resultMap5 = new HashMap<String, String>();
		int isLastYear = 5;
		int isLastTwoYear = 5;
		if (info != null) {
			String lab = info.getSectionId();
			if(likeLabMap.containsKey(lab)) {
				lab = likeLabMap.get(lab);
			}
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatientblh(), lab);
			String hisSampleId = "";
			String hisSampleNo = "";
			for(Sample sample : list) {
				hisSampleId += sample.getId() + ",";
				hisSampleNo += "'" + sample.getSampleNo() + "',";
			}
			List<Process> processList = processManager.getHisProcess(hisSampleId.substring(0, hisSampleId.length()-1));
			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
			Map<Long, Process> hisProcessMap = new HashMap<Long, Process>();
			Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
			for(Process p : processList) {
				hisProcessMap.put(p.getSampleid(), p);
			}
			for(TestResult tr : testList) {
				if(hisTestMap.containsKey(tr.getSampleNo())) {
					hisTestMap.get(tr.getSampleNo()).add(tr);
				} else {
					List<TestResult> tlist = new ArrayList<TestResult>();
					tlist.add(tr);
					hisTestMap.put(tr.getSampleNo(), tlist);
				}
			}
			
			Date receivetime = null;
			receivetime = process.getReceivetime();
			long curInfoReceiveTime = receivetime.getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : testResults) {
				testIdSet.add(t.getTestId());
			}
			String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);
			
			int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
			if(list!=null && list.size()>0){
			for (Sample pinfo : list) {
				boolean isHis = false;
				List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
				if(his != null) {
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
				}
				Date preceivetime = null;
				Process hisProcess = hisProcessMap.get(pinfo.getId());
				if (hisProcess == null || pinfo.getSampleNo() == null || hisProcess.getReceivetime() == null) {
					continue;
				}
				preceivetime = hisProcess.getReceivetime();
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
					for (TestResult result : his) {
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
		
		for (TestResult tr : testResults) {
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
				map.put("device", deviceMap.get(tr.getDeviceId()) == null ? tr.getOperator() : deviceMap.get(tr.getDeviceId()));
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

		if (deviceMap.size() == 0)
			initDeviceMap();
		
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}

		Sample info = sampleManager.getListBySampleNo(sampleNo).get(0);
		Process process = processManager.getBySampleId(info.getId());
		List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleNo);
		
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		int isLastYear = 2;
		if (info != null) {
			String lab = info.getSectionId();
			if(likeLabMap.containsKey(lab)) {
				lab = likeLabMap.get(lab);
			}
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatientblh(), lab);
			String hisSampleId = "";
			String hisSampleNo = "";
			for(Sample sample : list) {
				hisSampleId += sample.getId() + ",";
				hisSampleNo += "'" + sample.getSampleNo() + "',";
			}
			List<Process> processList = processManager.getHisProcess(hisSampleId.substring(0, hisSampleId.length()-1));
			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
			Map<Long, Process> hisProcessMap = new HashMap<Long, Process>();
			Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
			for(Process p : processList) {
				hisProcessMap.put(p.getSampleid(), p);
			}
			for(TestResult tr : testList) {
				if(hisTestMap.containsKey(tr.getSampleNo())) {
					hisTestMap.get(tr.getSampleNo()).add(tr);
				} else {
					List<TestResult> tlist = new ArrayList<TestResult>();
					tlist.add(tr);
					hisTestMap.put(tr.getSampleNo(), tlist);
				}
			}
			Date receivetime = null;
			receivetime = process.getReceivetime();
			long curInfoReceiveTime = receivetime.getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : testResults) {
				if (t.getEditMark() != Constants.DELETE_FLAG) {
					testIdSet.add(t.getTestId());
				} else {
					testResults.remove(t);
				}
			}
			String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);
			if(list!=null && list.size()>0){
				for (Sample pinfo : list) {
					boolean isHis = false;
					List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
					if(his != null) {
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
					}
					int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
					Date preceivetime = null;
					Process hisProcess = hisProcessMap.get(pinfo.getId());
					if (hisProcess == null || pinfo.getSampleNo() == null || hisProcess.getReceivetime() == null) {
						continue;
					}
					preceivetime = hisProcess.getReceivetime();
					
					String pDay = pinfo.getSampleNo().substring(4, 6) + "/" + pinfo.getSampleNo().substring(6, 8);
					int pyear = Integer.parseInt(pinfo.getSampleNo().substring(0, 4));
					if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
						if (index > 1)
							break;
						switch (index) {
						case 0:
							rmap = resultMap1;
							break;
						case 1:
							rmap = resultMap2;
							break;
						}
						for (TestResult result : his) {
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
		int size = 0;
		if(testResults.size()/2 == 0) {
			size = testResults.size()/2-1;
		} else {
			size = testResults.size()/2;
		}
		for (int i =0; i< testResults.size(); i++) {
			TestResult tr  = testResults.get(i);
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
				map.put("device", deviceMap.get(tr.getDeviceId()) == null ? tr.getOperator() : deviceMap.get(tr.getDeviceId()));
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
				map.put("checktime", Constants.DF5.format(tr.getMeasureTime()));
				if(i<= size) {
					dataRows.add(map);
				} else {
					dataRows2.add(map);
				}
			} else {
				continue;
			}

		}
		dataArray[0] = new DataResponse();
		dataArray[1] = new DataResponse();
		dataArray[0].setRows(dataRows);
		dataArray[0].setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		userdata.put("isLastYear", isLastYear);
		dataArray[0].setUserdata(userdata);
		dataArray[1].setRows(dataRows2);
		dataArray[1].setRecords(dataRows2.size());
		dataArray[1].setUserdata(userdata);
		response.setContentType("text/html;charset=UTF-8");
		return dataArray;
	}

	@RequestMapping(value = "/edit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean editTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = request.getParameter("result");
		String testId = request.getParameter("id");
		String sampleNo = request.getParameter("sampleNo");
		
//		if (fillUtil == null) {
//			List<Describe> desList = syncManager.getAllDescribe();
//			List<ReferenceValue> refList = syncManager.getAllReferenceValue();
//			fillUtil = FillFieldUtil.getInstance(desList, refList);
//		}
		
//		if (formulaUtil == null) {
//			formulaUtil = FormulaUtil.getInstance(rmiService, syncManager, testResultManager, patientInfoManager, idMap, fillUtil);
//		}

		if (!StringUtils.isEmpty(testId) && !StringUtils.isEmpty(sampleNo)) {
			TestResult testResult = testResultManager.getSingleTestResult(sampleNo, testId);
			Sample info = sampleManager.getBySampleNo(sampleNo);
			
			if (testResult == null)
				return false;
			
			List<TestResult> tr = null;
			if (testId.equals("9046")) {
				tr = testResultManager.getListByTestString(sampleNo, "9045,9051");
			}
			if (testId.equals("9047")) {
				tr = testResultManager.getListByTestString(sampleNo, "9045,9055");
			}
			if (testId.equals("9048")) {
				tr = testResultManager.getListByTestString(sampleNo, "9045,9089");
			}
			if (testId.equals("9049")) {
				tr = testResultManager.getListByTestString(sampleNo, "9045,9091");
			}
			if (testId.equals("9050")) {
				tr = testResultManager.getListByTestString(sampleNo, "9045,9090");
			}
			
			Double rbc = 0.0;
			TestResult testresult2 = null;
			if (tr != null) {
				if(tr.get(0).getTestId().equals("9045")) {
					rbc = Double.parseDouble(tr.get(0).getTestResult());
					testresult2 = tr.get(1);
				} else {
					rbc = Double.parseDouble(tr.get(1).getTestResult());
					testresult2 = tr.get(0);
				}
				Double newresult2 = Double.parseDouble(result)*rbc/100;
				if (testresult2.getTestId().equals("9051") || testresult2.getTestId().equals("9055")) {
					DecimalFormat df = new DecimalFormat("0.0");
					testresult2.setTestResult(df.format(newresult2));
					testresult2.setOperator(request.getRemoteUser());
					testresult2.setMeasureTime(new Date());
					testresult2.setResultFlag("AAAAAA");
					testresult2.setEditMark(Constants.MANUAL_EDIT_FLAG);
//					fillUtil.fillResult(testresult2, info);
					testResultManager.save(testresult2);
				} else {
					DecimalFormat df = new DecimalFormat("0.00");
					testresult2.setTestResult(df.format(newresult2));
					testresult2.setOperator(request.getRemoteUser());
					testresult2.setMeasureTime(new Date());
					testresult2.setResultFlag("AAAAAA");
					testresult2.setEditMark(Constants.MANUAL_EDIT_FLAG);
//					fillUtil.fillResult(testresult2, info);
					testResultManager.save(testresult2);
				}
			}
			
			String oldResult = testResult.getTestResult();
			testResult.setTestResult(result);
			testResult.setOperator(request.getRemoteUser());
			testResult.setMeasureTime(new Date()); 
			
			testResult.setResultFlag("AAAAAA");
//			fillUtil.fillResult(testResult, info);
			if (testResult.getEditMark() == 0) {
				testResult.setEditMark(Constants.MANUAL_EDIT_FLAG);
			} else if (testResult.getEditMark() % Constants.MANUAL_EDIT_FLAG != 0) {
				testResult.setEditMark(Constants.MANUAL_EDIT_FLAG * testResult.getEditMark());
			}
			testResultManager.save(testResult);
			
//			formulaUtil.formula(info, request.getRemoteUser());
			
			info.setModifyFlag(1);
			info.setAuditStatus(0);
			sampleManager.save(info);
			
			TestModify testModify = new TestModify();
			Date date = new Date();
			System.out.println(date);
//			testModify.setModifyTime(date);
//			System.out.println(ymd.format(new Date()));
			testModify.setModifyTime(new Date());
			System.out.println(testModify.getModifyTime());
			testModify.setModifyUser(request.getRemoteUser());
			testModify.setSampleNo(sampleNo);
			testModify.setTestId(testId);
			testModify.setNewValue(result);
			testModify.setOldValue(oldResult);
			testModify.setType(Constants.EDIT);
			testModifyManager.save(testModify);
		} else {
			return false;
		}

		return true;
	}
	
	/**
	 * 删除一条检验项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteProject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String testId = request.getParameter("id");
		String sampleNo = request.getParameter("sampleNo");

		if (!StringUtils.isEmpty(testId) && !StringUtils.isEmpty(sampleNo)) {
			TestResult testResult = testResultManager.getSingleTestResult(sampleNo, testId);
			testResult.setEditMark(Constants.DELETE_FLAG);
			testResultManager.save(testResult);
			// testResultManager.remove(new TestResultPK(sampleNo, testId));
//			TestModify testModify = new TestModify();
//			testModify.setModifyTime(new Date());
//			testModify.setModifyUser(request.getRemoteUser());
//			testModify.setSampleNo(sampleNo);
//			testModify.setTestId(testId);
//			testModify.setNewValue(testResult.getTestResult());
//			testModify.setType(Constants.DELETE);
//			testModifyManager.save(testModify);
			Sample info = sampleManager.getBySampleNo(sampleNo);
			info.setModifyFlag(1);
			// info.setWriteBack(1);
			sampleManager.save(info);
		} else {
			return false;
		}

		return true;
	}
	
	
	/**
	 * 获取样本中检验项目的修改记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/testModify*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTestModify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sampleNo");
		if (StringUtils.isEmpty(sampleNo))
			throw new NullPointerException();

		List<TestModify> modifyList = testModifyManager.getBySampleNo(sampleNo);
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (modifyList.size() == 0) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(modifyList.size());
		if (idMap.size() == 0)
			initMap();

		for (TestModify t : modifyList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("test",  idMap.get(t.getTestId()).getName());
			map.put("type", t.getType());
			map.put("oldValue", t.getOldValue());
			map.put("newValue", t.getNewValue());
			map.put("modifyTime", Constants.SDF.format(t.getModifyTime()));
			map.put("modifyUser", t.getModifyUser());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
}
