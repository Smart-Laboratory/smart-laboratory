package com.smart.webapp.controller.print;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;

@Controller
@RequestMapping("/print*")
public class SamplePrintController extends BaseAuditController {
	
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("docId", request.getParameter("docId"));
		request.setAttribute("sampleNo", request.getParameter("sampleNo"));
		request.setAttribute("showLast", request.getParameter("last"));
		return new ModelAndView("print/sample");
	}
	
	@RequestMapping(value = "/sampleData*", method = RequestMethod.GET)
	public String printData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sampleno");
		int hasLast = Integer.parseInt(request.getParameter("haslast"));
		JSONObject info = new JSONObject();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		Sample s = sampleManager.getBySampleNo(sampleno);
		Patient patient = patientManager.getByBlh(s.getPatientblh());
		Process process = processManager.getBySampleId(s.getId());
		List<TestResult> list = testResultManager.getPrintTestBySampleNo(s.getSampleNo());
		
		info.put("blh", patient.getBlh());
		info.put("pName", patient.getPatientName());
		info.put("sex", patient.getSexValue());
		info.put("age", patient.getAge());
		info.put("pType", SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(s.getSampleType())));
		info.put("diagnostic", s.getDiagnostic());
		if(s.getStayHospitalMode() == 2) {
			info.put("staymodetitle", "住 院 号");
			info.put("staymodesection", "病区");
			info.put("bed", s.getDepartBed());
		} else {
			info.put("staymodetitle", "就诊卡号");
			info.put("staymodesection", "科室");
		}
		info.put("staymode", s.getStayHospitalMode());
		info.put("pId", s.getPatientId());
		info.put("section", sectionutil.getValue(s.getHosSection()));
		if(contactMap.size() == 0) {
			initContactInforMap();
		}
		if(idMap.size() == 0) {
			initMap();
		}
		info.put("requester", process.getRequester() == null ? " " : contactMap.get(process.getRequester()).getNAME());
		info.put("tester", s.getChkoper2());
		info.put("auditor", process.getCheckoperator());
		info.put("receivetime", process.getReceivetime() == null ? "" : Constants.SDF.format(process.getReceivetime()));
		info.put("checktime", Constants.SDF.format(process.getChecktime()));
		info.put("executetime", process.getExecutetime() == null ? "" : Constants.SDF.format(process.getExecutetime()));
		info.put("examinaim", s.getInspectionName());
		info.put("date", sampleno.substring(0, 4) + "年" + sampleno.substring(4, 6) + "月" + sampleno.substring(6, 8) + "日");
		
		Map<String, TestResult> resultMap1 = new HashMap<String, TestResult>();
		Map<String, TestResult> resultMap2 = new HashMap<String, TestResult>();
		Map<String, TestResult> resultMap3 = new HashMap<String, TestResult>();
		String hisTitle1 = "";
		String hisTitle2 = "";
		String hisTitle3 = "";
		String lab = s.getSectionId();
		if(likeLabMap.containsKey(lab)) {
			lab = likeLabMap.get(lab);
		}
		if(hasLast == 1) {
			List<Sample> history = sampleManager.getHistorySample(s.getPatientId(), s.getPatientblh(), lab);
			String hisSampleId = "";
			String hisSampleNo = "";
			for(Sample sample : history) {
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
			Map<String, TestResult> rmap = null;
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : list) {
				testIdSet.add(t.getTestId());
			}
			if(history != null && history.size()>0){
				for (Sample pinfo : history) {
					String psampleno = pinfo.getSampleNo();
					boolean isHis = false;
					List<TestResult> his = hisTestMap.get(psampleno);
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
					preceivetime = hisProcessMap.get(pinfo.getId()).getReceivetime();
					if (preceivetime == null || pinfo.getSampleNo() == null) {
						continue;
					}
					if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
						if (index > 4)
							break;
						switch (index) {
						case 0:
							rmap = resultMap1;
							hisTitle1 = psampleno.substring(2,4) + "/" + psampleno.substring(4,6) + "/" + psampleno.substring(6,8);
							break;
						case 1:
							rmap = resultMap2;
							hisTitle2 = psampleno.substring(2,4) + "/" + psampleno.substring(4,6) + "/" + psampleno.substring(6,8);
							break;
						case 2:
							rmap = resultMap3;
							hisTitle3 = psampleno.substring(2,4) + "/" + psampleno.substring(4,6) + "/" + psampleno.substring(6,8);
							break;
						}
						for (TestResult tr : hisTestMap.get(psampleno)) {
							rmap.put(tr.getTestId(), tr);
						}
						index++;
					}
				}
			}
		}
		
		info.put("hisTitle1", hisTitle1);
		info.put("hisTitle2", hisTitle2);
		info.put("hisTitle3", hisTitle3);
		
		JSONArray result = new JSONArray();
		for(int i = 1; i<=list.size(); i++) {
			TestResult re = list.get(i-1);
			String testId = re.getTestId();
			Set<String> sameTests = util.getKeySet(testId);
			sameTests.add(testId);
			JSONObject o = new JSONObject();
			o.put("num", i);
			o.put("name", idMap.get(re.getTestId()).getName());
			o.put("last","&nbsp;");
			o.put("last1","&nbsp;");
			o.put("last2","&nbsp;");
			o.put("result", re.getTestResult());
			o.put("lastflag","&nbsp;");
			o.put("lastflag1","&nbsp;");
			o.put("lastflag2","&nbsp;");
			o.put("resultflag", "&nbsp;");
			String lo = re.getRefLo();
			String hi = re.getRefHi();
			if (lo != null && hi != null) {
				o.put("scope", lo + "-" + hi);
			} else {
				o.put("scope", "");
			}
			if (Integer.parseInt(idMap.get(re.getTestId()).getPrintord()) <=2015) {
				if(re.getResultFlag().charAt(0) == 'C') {
					o.put("resultflag", "↓");
				} else if(re.getResultFlag().charAt(0) == 'B') {
					o.put("resultflag", "↑");
				}
			}
			if(hasLast == 1) {
				for(String tid : sameTests) {
					if(o.get("last").equals("&nbsp;")) {
						if(resultMap1.size() != 0 && resultMap1.containsKey(tid)) {
							o.put("last", resultMap1.get(tid).getTestResult());
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap1.get(tid).getResultFlag().charAt(0) == 'C') {
									o.put("lastflag", "↓");
								} else if(resultMap1.get(tid).getResultFlag().charAt(0) == 'B') {
									o.put("lastflag", "↑");
								}
							}
						}
					}
					if(o.get("last1").equals("&nbsp;")) {
						if(resultMap2.size() != 0 && resultMap2.containsKey(tid)) {
							o.put("last1", resultMap2.get(tid).getTestResult());
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap2.get(tid).getResultFlag().charAt(0) == 'C') {
									o.put("lastflag1", "↓");
								} else if(resultMap2.get(tid).getResultFlag().charAt(0) == 'B') {
									o.put("lastflag1", "↑");
								}
							}
						}
					}
					if(o.get("last2").equals("&nbsp;")) {
						if(resultMap3.size() != 0 && resultMap3.containsKey(tid)) {
							o.put("last2", resultMap3.get(tid).getTestResult());
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap3.get(tid).getResultFlag().charAt(0) == 'C') {
									o.put("lastflag2", "↓");
								} else if(resultMap3.get(tid).getResultFlag().charAt(0) == 'B') {
									o.put("lastflag2", "↑");
								}
							}
						}
					}
				}
			}
			o.put("unit", re.getUnit());
			result.put(o);
		}
		info.put("testresult", result);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(info.toString());
		return null;
	}
}
