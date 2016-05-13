package com.smart.webapp.controller.print;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import com.zju.api.model.SyncResult;

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
	
	//打印类型：1-单栏正常；2-双栏正常；3-乙肝MYC；4-微生物培养和鉴定；5-微生物药敏
	@RequestMapping(value = "/sampleData*", method = RequestMethod.GET)
	public String printData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sampleno");
		int hasLast = Integer.parseInt(request.getParameter("haslast"));
		int type = 1;
		JSONObject info = new JSONObject();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		Sample s = sampleManager.getBySampleNo(sampleno);
		Patient patient = patientManager.getByBlh(s.getPatientblh());
		Process process = processManager.getBySampleId(s.getId());
		List<TestResult> list = testResultManager.getPrintTestBySampleNo(s.getSampleNo());
		List<SyncResult> wswlist = null;
		if(sampleno.substring(8, 11).equals("BAA")) {
			wswlist = rmiService.getWSWResult(s.getSampleNo());
			type = 4;
		} else {
			if(sampleno.substring(8, 11).equals("MYC")) {
				type = 3;
			}
			/*if(list.size() > 22) {
				type = 2;
			}*/
		}
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
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}
		info.put("requester", process.getRequester() == null ? " " : (contactMap.containsKey(process.getRequester()) ? contactMap.get(process.getRequester()).getNAME() : process.getRequester()));
		info.put("tester", s.getChkoper2());
		info.put("auditor", process.getCheckoperator());
		info.put("receivetime", process.getReceivetime() == null ? "" : Constants.SDF.format(process.getReceivetime()));
		info.put("checktime", Constants.SDF.format(process.getChecktime()));
		info.put("executetime", process.getExecutetime() == null ? "" : Constants.SDF.format(process.getExecutetime()));
		info.put("examinaim", s.getInspectionName());
		info.put("date", sampleno.substring(0, 4) + "年" + sampleno.substring(4, 6) + "月" + sampleno.substring(6, 8) + "日");
		Map<String, TestResult> resultMap1 = new HashMap<String, TestResult>();
		String hisTitle1 = "";
		String lab = s.getSectionId();
		if(likeLabMap.containsKey(lab)) {
			lab = likeLabMap.get(lab);
		}
		if(hasLast == 1 && type<4) {
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
					if(psampleno.equals(sampleno)) {
						continue;
					}
					boolean isHis = false;
					List<TestResult> his = hisTestMap.get(psampleno);
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
						}
						for (TestResult tr : hisTestMap.get(psampleno)) {
							rmap.put(tr.getTestId(), tr);
						}
						index++;
					}
				}
			}
		}
		String html = "";
		String dangerTest = "";
		if(s.getAuditMark() == 6) {
			for(String str : s.getMarkTests().split(";")) {
				if(Integer.parseInt(str.split(":")[1]) == 3) {
					dangerTest += str.split(":")[0] + ",";
				}
			}
		}
		if(type > 3) {
			html = getWSWHTML(type,wswlist);
		} else {
			html = getHTML(type,hasLast,list,hisTitle1,resultMap1,dangerTest);
		}
		info.put("html", html);
		info.put("advise", s.getDescription()== null ? "" : s.getDescription());
		String imghtml = "";
		if(s.getHasimages() == 1) {
			String filepath = Constants.imageUrl + sampleno;
			File dir = new File(filepath);
			if (dir.exists()) {
				for (File f : dir.listFiles()) {
					if (f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") || f.getName().endsWith(".PNG") || f.getName().endsWith(".png")) {
						imghtml += "<img src='../images/upload/" + sampleno + "/" + f.getName() + "' style='float:left;margin-left:5%;width:45%'>";
					}
				}
			}
		}
		info.put("imghtml", imghtml);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(info.toString());
		return null;
	}
	
	//获取样本的历史曲线图
	@RequestMapping(value = "/historyChart*", method = RequestMethod.GET)
	public String historyCHart(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sampleno");
		int hasLast = Integer.parseInt(request.getParameter("haslast"));
		int type = 1;
		JSONObject info = new JSONObject();
		Sample s = sampleManager.getBySampleNo(sampleno);
		List<TestResult> list = testResultManager.getPrintTestBySampleNo(s.getSampleNo());
		if(sampleno.substring(8, 11).equals("BAA")) {
			type = 4;
		} else {
			if(sampleno.substring(8, 11).equals("MYC")) {
				type = 3;
			}
			/*if(list.size() > 22) {
				type = 2;
			}*/
		}
		if(contactMap.size() == 0) {
			initContactInforMap();
		}
		if(idMap.size() == 0) {
			initMap();
		}
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}
		String lab = s.getSectionId();
		if(likeLabMap.containsKey(lab)) {
			lab = likeLabMap.get(lab);
		}
		Set<String> testIdSet = new HashSet<String>();
		for (TestResult t : list) {
			testIdSet.add(t.getTestId());
		}
		if(hasLast == 1 && type<4) {
			List<Sample> history = sampleManager.getHistorySample(s.getPatientId(), s.getPatientblh(), lab);
			String hisSampleNo = "";
			for(Sample sample : history) {
				hisSampleNo += "'" + sample.getSampleNo() + "',";
			}
			List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
			Map<String, List<TestResult>> chartTestMap = new HashMap<String, List<TestResult>>();
			for(TestResult tr : testList) {
				if(idMap.containsKey(tr.getTestId()) && idMap.get(tr.getTestId()).getNeedhistory() == 1) {
					if(chartTestMap.containsKey(tr.getTestId())) {
						chartTestMap.get(tr.getTestId()).add(tr);
					} else {
						List<TestResult> tlist = new ArrayList<TestResult>();
						tlist.add(tr);
						chartTestMap.put(tr.getTestId(), tlist);
					}
				}
			}
			JSONArray chartlist = new JSONArray();
			int count = 0;
			for(String testid : chartTestMap.keySet()) {
				if(testIdSet.contains(testid)) {
					List<TestResult> tl = chartTestMap.get(testid);
					List<Double> loArr = new ArrayList<Double>(); 
					List<Double> reArr = new ArrayList<Double>();
					List<Double> hiArr = new ArrayList<Double>();
					List<String> timeArr = new ArrayList<String>();
					JSONObject testchart = new JSONObject();
					testchart.put("title", idMap.get(testid).getName());
					int isneed = 0;
					if(s.getCharttest() != null && s.getCharttest().indexOf(testid) >= 0) {
						isneed = 1;
					}
					int hisnum = 0;
					for(TestResult tr : tl) {
						if(tr.getResultFlag().charAt(0) != 'A') {
							isneed = 1;
						}
						if(hisnum < 5) {
							try {
								reArr.add(Double.parseDouble(tr.getTestResult()));
								hiArr.add(Double.parseDouble(tr.getRefHi()));
								loArr.add(Double.parseDouble(tr.getRefLo()));
								timeArr.add(Constants.DF7.format(tr.getMeasureTime()));
							} catch(NumberFormatException nfe) {
								continue;
							}
						}
						hisnum++;
					}
					Collections.reverse(timeArr);
					Collections.reverse(reArr);
					Collections.reverse(hiArr);
					Collections.reverse(loArr);
					testchart.put("id", testid);
					testchart.put("check", isneed);
					testchart.put("time", timeArr);
					testchart.put("result", reArr);
					testchart.put("high", hiArr);
					testchart.put("low", loArr);
					chartlist.put(count, testchart);
					count++;
				}
			}
			info.put("chartlist", chartlist);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(info.toString());
		return null;
	}
	
	//获取样本的历史曲线图
	@RequestMapping(value = "/chart*", method = RequestMethod.GET)
	public String getCHart(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sampleno");
		JSONObject info = new JSONObject();
		Sample s = sampleManager.getBySampleNo(sampleno);
		if(s.getCharttest() == null || s.getCharttest().isEmpty()) {
			return null;
		}
		List<TestResult> list = testResultManager.getPrintTestBySampleNo(s.getSampleNo());
		if(contactMap.size() == 0) {
			initContactInforMap();
		}
		if(idMap.size() == 0) {
			initMap();
		}
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}
		String lab = s.getSectionId();
		if(likeLabMap.containsKey(lab)) {
			lab = likeLabMap.get(lab);
		}
		Set<String> testIdSet = new HashSet<String>();
		for (TestResult t : list) {
			testIdSet.add(t.getTestId());
		}
		List<Sample> history = sampleManager.getHistorySample(s.getPatientId(), s.getPatientblh(), lab);
		String hisSampleNo = "";
		for(Sample sample : history) {
			hisSampleNo += "'" + sample.getSampleNo() + "',";
		}
		List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
		Map<String, List<TestResult>> chartTestMap = new HashMap<String, List<TestResult>>();
		for(TestResult tr : testList) {
			if(s.getCharttest().indexOf(tr.getTestId()) >= 0) {
				if(chartTestMap.containsKey(tr.getTestId())) {
					chartTestMap.get(tr.getTestId()).add(tr);
				} else {
					List<TestResult> tlist = new ArrayList<TestResult>();
					tlist.add(tr);
					chartTestMap.put(tr.getTestId(), tlist);
				}
			}
		}
		JSONArray chartlist = new JSONArray();
		int count = 0;
		for(String testid : chartTestMap.keySet()) {
			if(testIdSet.contains(testid)) {
				List<TestResult> tl = chartTestMap.get(testid);
				List<Double> loArr = new ArrayList<Double>(); 
				List<Double> reArr = new ArrayList<Double>();
				List<Double> hiArr = new ArrayList<Double>();
				List<String> timeArr = new ArrayList<String>();
				JSONObject testchart = new JSONObject();
				testchart.put("title", idMap.get(testid).getName());
				int isneed = 0;
				int hisnum = 0;
				for(TestResult tr : tl) {
					if(tr.getResultFlag().charAt(0) != 'A') {
						isneed = 1;
					}
					if(hisnum < 5) {
						try {
							reArr.add(Double.parseDouble(tr.getTestResult()));
							hiArr.add(Double.parseDouble(tr.getRefHi()));
							loArr.add(Double.parseDouble(tr.getRefLo()));
							timeArr.add(Constants.DF7.format(tr.getMeasureTime()));
						} catch(NumberFormatException nfe) {
							continue;
						}
					}
					hisnum++;
				}
				Collections.reverse(timeArr);
				Collections.reverse(reArr);
				Collections.reverse(hiArr);
				Collections.reverse(loArr);
				testchart.put("id", testid);
				testchart.put("check", isneed);
				testchart.put("time", timeArr);
				testchart.put("result", reArr);
				testchart.put("high", hiArr);
				testchart.put("low", loArr);
				chartlist.put(count, testchart);
				count++;
			}
		}
		info.put("chartlist", chartlist);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(info.toString());
		return null;
	}

	private String getHTML(int type, int hasLast, List<TestResult> list, String hisTitle,
			Map<String, TestResult> resultMap, String dangerTest) {
		StringBuilder html = new StringBuilder("");
		if(type == 3) {
			html.append("<div style='height:25px;margin-left:2%;width:96%;font-size:14px;'><b>");
			html.append("<div style='float:left;width:5%;'>No.</div>");
			html.append("<div style='float:left;width:30%;'>项目</div>");
			html.append("<div style='float:left;width:15%;'>当次结果</div>");
			html.append("<div style='float:left;width:15%;'>");
			if(hisTitle.isEmpty()) {
				html.append("上次结果");
			} else {
				html.append(hisTitle);
			}
			html.append("</div>");
			html.append("<div style='float:left;width:10%;text-align:center;'>单位</div>");
			html.append("<div style='float:left;width:25%;text-align:center;'>注释</div>");
			html.append("</b></div>");
			for(int i = 1; i<=list.size(); i++) {
				TestResult re = list.get(i-1); 
				String testId = re.getTestId();
				Set<String> sameTests = util.getKeySet(testId);
				sameTests.add(testId);
				if(i == 1) {
					html.append("<div style='height:25px;margin-left:2%;width:96%;padding-top:5px;border-top:2px solid #000000;'>");
				} else {
					html.append("<div style='height:20px;margin-left:2%;width:96%;'>");
				}
				html.append("<div style='float:left;width:5%;'>" + i + "</div>");
				html.append("<div style='float:left;width:25%;'>" + idMap.get(re.getTestId()).getName());
				if(re.getMethod() != null && !re.getMethod().isEmpty()) {
					html.append("[" + re.getMethod() + "]");
				}
				html.append("</div>");
				if(re.getTestResult().indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:12%;background:#C0C0C0;'>" + re.getTestResult() + "</div>");
				} else {
					html.append("<div style='float:left;width:12%;'>" + re.getTestResult() + "</div>");
				}
				String resultflag = "&nbsp;";
				String last = "&nbsp;";
				String lastflag = "&nbsp;";
				if (Integer.parseInt(idMap.get(re.getTestId()).getPrintord()) <=2015) {
					if(re.getResultFlag().charAt(0) == 'C') {
						resultflag = "↓";
					} else if(re.getResultFlag().charAt(0) == 'B') {
						resultflag = "↑";
					}
				}
				html.append("<div style='float:left;width:3%;'>" + resultflag + "</div>");
				if(hasLast == 1) {
					for(String tid : sameTests) {
						if(resultMap.size() != 0 && resultMap.containsKey(tid)) {
							last = resultMap.get(tid).getTestResult();
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap.get(tid).getResultFlag().charAt(0) == 'C') {
									lastflag = "↓";
								} else if(resultMap.get(tid).getResultFlag().charAt(0) == 'B') {
									lastflag = "↑";
								}
							}
						}
					}
				}
				if(last.indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:12%;background:#C0C0C0;'>" + last + "</div>");
				} else {
					html.append("<div style='float:left;width:12%;'>" + last + "</div>");
				}
				html.append("<div style='float:left;width:3%;'>" + lastflag + "</div>");
				html.append("<div style='float:left;width:10%;text-align:center;'>" + (re.getUnit() == null ? "&nbsp;" : re.getUnit()) + "</div>");
				html.append("<div style='float:left;width:25%;text-align:center;'>" + idMap.get(re.getTestId()).getDescription() + "</div>");
				html.append("</div>");
			}
		} else if(type == 2) {
			html.append("<div style='height:25px;margin-left:2%;width:96%;font-size:12px;'><b>");
			html.append("<div style='float:left;width:17%;'>项目</div>");
			html.append("<div style='float:left;width:8%;'>结果</div>");
			html.append("<div style='float:left;width:8%;'>");
			if(hisTitle.isEmpty()) {
				html.append("历史");
			} else {
				html.append(hisTitle);
			}
			html.append("</div>");
			html.append("<div style='float:left;width:12%;text-align:center;'>参考范围</div>");
			html.append("<div style='float:left;width:5%;text-align:center;'>单位</div>");
			html.append("<div style='float:left;width:17%;border-left:1px solid #000000;'>项目</div>");
			html.append("<div style='float:left;width:8%;'>结果</div>");
			html.append("<div style='float:left;width:8%;'>");
			if(hisTitle.isEmpty()) {
				html.append("历史");
			} else {
				html.append(hisTitle);
			}
			html.append("</div>");
			html.append("<div style='float:left;width:12%;text-align:center;'>参考范围</div>");
			html.append("<div style='float:left;width:5%;'>单位</div>");
			html.append("</b></div>");
			int num = 0;
			if(list.size() % 2 == 0) {
				num = list.size()/2;
			} else {
				num = list.size()/2 + 1;
			}
			for(int i = 1; i<=list.size(); i++) {
				TestResult re = list.get(i-1); 
				String testId = re.getTestId();
				Set<String> sameTests = util.getKeySet(testId);
				sameTests.add(testId);
				if(i == 1) {
					html.append("<div style='float:left;width:50%;border-top:2px solid #000000;font-size:10px;'>");
				}
				if(i == num + 1) {
					html.append("<div style='float:left;width:50%;border-left:1px solid #000000;border-top:2px solid #000000;font-size:10px;'>");
				}
				html.append("<div style='float:left;width:100%;'>");
				html.append("<div style='float:left;width:34%;'>" + idMap.get(re.getTestId()).getName() + "</div>");
				if(re.getTestResult().indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:13%;background:#C0C0C0;'>" + re.getTestResult() + "</div>");
				} else {
					html.append("<div style='float:left;width:13%;'>" + re.getTestResult() + "</div>");
				}
				String lo = re.getRefLo();
				String hi = re.getRefHi();
				String scope = "&nbsp;";
				String resultflag = "&nbsp;";
				String last = "&nbsp;";
				String lastflag = "&nbsp;";
				if (lo != null && hi != null && !lo.isEmpty() && !hi.isEmpty()) {
					scope = lo + "-" + hi;
				}
				if(lo != null && !lo.isEmpty() && (hi.isEmpty() || hi == null)) {
					scope = lo;
				}
				if (Integer.parseInt(idMap.get(re.getTestId()).getPrintord()) <=2015) {
					if(re.getResultFlag().charAt(0) == 'C') {
						resultflag = "↓";
					} else if(re.getResultFlag().charAt(0) == 'B') {
						resultflag = "↑";
					}
				}
				html.append("<div style='float:left;width:3%;'>" + resultflag + "</div>");
				if(hasLast == 1) {
					for(String tid : sameTests) {
						if(resultMap.size() != 0 && resultMap.containsKey(tid)) {
							last = resultMap.get(tid).getTestResult();
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap.get(tid).getResultFlag().charAt(0) == 'C') {
									lastflag = "↓";
								} else if(resultMap.get(tid).getResultFlag().charAt(0) == 'B') {
									lastflag = "↑";
								}
							}
						}
					}
				}
				if(last.indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:13%;background:#C0C0C0;'>" + last + "</div>");
				} else {
					html.append("<div style='float:left;width:13%;'>" + last + "</div>");
				}
				html.append("<div style='float:left;width:3%;'>" + lastflag + "</div>");
				html.append("<div style='float:left;width:24%;text-align:center;'>" + scope + "</div>");
				html.append("<div style='float:left;width:10%;'>" + (re.getUnit() == null ? "&nbsp;" : re.getUnit()) + "</div>");
				html.append("</div>");
				
				if(i == num || i == list.size()) {
					html.append("</div>");
				}
			}
		} else {
			html.append("<div style='height:25px;margin-left:2%;width:96%;font-size:14px;'><b>");
			html.append("<div style='float:left;width:5%;'>&nbsp;</div>");
			html.append("<div style='float:left;width:5%;'>No.</div>");
			html.append("<div style='float:left;width:30%;'>项目</div>");
			html.append("<div style='float:left;width:15%;'>当次结果</div>");
			html.append("<div style='float:left;width:15%;'>");
			if(hisTitle.isEmpty()) {
				html.append("上次结果");
			} else {
				html.append(hisTitle);
			}
			html.append("</div>");
			html.append("<div style='float:left;width:20%;text-align:center;'>参考范围</div>");
			html.append("<div style='float:left;width:10%;text-align:center;'>单位</div>");
			html.append("</b></div>");
			for(int i = 1; i<=list.size(); i++) {
				TestResult re = list.get(i-1); 
				String testId = re.getTestId();
				Set<String> sameTests = util.getKeySet(testId);
				sameTests.add(testId);
				if(i == 1) {
					html.append("<div style='height:25px;margin-left:2%;width:96%;padding-top:5px;border-top:2px solid #000000;'>");
				} else {
					html.append("<div style='height:20px;margin-left:2%;width:96%;'>");
				}
				if(dangerTest.indexOf(testId) >= 0) {
					html.append("<div style='float:left;width:5%;'><red>危急</red></div>");
				} else {
					html.append("<div style='float:left;width:5%;'>&nbsp;</div>");
				}
				html.append("<div style='float:left;width:5%;'>" + i + "</div>");
				html.append("<div style='float:left;width:30%;'>" + idMap.get(re.getTestId()).getName());
				if(re.getMethod() != null && !re.getMethod().isEmpty()) {
					html.append("[" + re.getMethod() + "]");
				}
				html.append("</div>");
				if(re.getTestResult().indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:12%;background:#C0C0C0;'>" + re.getTestResult() + "</div>");
				} else {
					html.append("<div style='float:left;width:12%;'>" + re.getTestResult() + "</div>");
				}
				String lo = re.getRefLo();
				String hi = re.getRefHi();
				String scope = "&nbsp;";
				String resultflag = "&nbsp;";
				String last = "&nbsp;";
				String lastflag = "&nbsp;";
				if (lo != null && hi != null && !lo.isEmpty() && !hi.isEmpty()) {
					scope = lo + "-" + hi;
				}
				if(lo != null && !lo.isEmpty() && (hi.isEmpty() || hi == null)) {
					scope = lo;
				}
				if (Integer.parseInt(idMap.get(re.getTestId()).getPrintord()) <=2015) {
					if(re.getResultFlag().charAt(0) == 'C') {
						resultflag = "↓";
					} else if(re.getResultFlag().charAt(0) == 'B') {
						resultflag = "↑";
					}
				}
				html.append("<div style='float:left;width:3%;'>" + resultflag + "</div>");
				if(hasLast == 1) {
					for(String tid : sameTests) {
						if(resultMap.size() != 0 && resultMap.containsKey(tid)) {
							last = resultMap.get(tid).getTestResult();
							if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
								if(resultMap.get(tid).getResultFlag().charAt(0) == 'C') {
									lastflag = "↓";
								} else if(resultMap.get(tid).getResultFlag().charAt(0) == 'B') {
									lastflag = "↑";
								}
							}
						}
					}
				}
				if(last.indexOf(Constants.YANG) >= 0) {
					html.append("<div style='float:left;width:12%;background:#C0C0C0;'>" + last + "</div>");
				} else {
					html.append("<div style='float:left;width:12%;'>" + last + "</div>");
				}
				html.append("<div style='float:left;width:3%;'>" + lastflag + "</div>");
				html.append("<div style='float:left;width:20%;text-align:center;'>" + scope + "</div>");
				html.append("<div style='float:left;width:10%;text-align:center;'>" + (re.getUnit() == null ? "&nbsp;" : re.getUnit()) + "</div>");
				html.append("</div>");
			}
		}
		return html.toString();
	}

	private String getWSWHTML(int type, List<SyncResult> wswlist) {
		StringBuilder html = new StringBuilder("");
		Map<String, List<SyncResult>> wswMap = new HashMap<String, List<SyncResult>>();
		for(SyncResult sr : wswlist) {
			if(sr.getRESULTFLAG().charAt(0) == 'N') {
				if(wswMap.containsKey("N")) {
					wswMap.get("N").add(sr);
				} else {
					List<SyncResult> list = new ArrayList<SyncResult>();
					list.add(sr);
					wswMap.put("N", list);
				}
			} else if(sr.getRESULTFLAG().charAt(0) == 'O') {
				if(wswMap.containsKey("O")) {
					wswMap.get("O").add(sr);
				} else {
					List<SyncResult> list = new ArrayList<SyncResult>();
					list.add(sr);
					wswMap.put("O", list);
				}
			} else if(sr.getRESULTFLAG().charAt(0) == 'B') {
				if(wswMap.containsKey("B")) {
					wswMap.get("B").add(sr);
				} else {
					List<SyncResult> list = new ArrayList<SyncResult>();
					list.add(sr);
					wswMap.put("B", list);
				}
			} else {
				if(wswMap.containsKey("A")) {
					wswMap.get("A").add(sr);
				} else {
					List<SyncResult> list = new ArrayList<SyncResult>();
					list.add(sr);
					wswMap.put("A", list);
				}
			}
		}
		
		if(wswMap.containsKey("N")) {
			html.append("<div style='float:left;height:25px;margin-left:10%;width:90%;font-size:14px;'><b>检验结果</b></div>");
			List<SyncResult> list = wswMap.get("N");
			for(SyncResult sr : list) {
				html.append("<div style='float:left;height:20px;margin-left:20%;width:60%;font-size:16px;'>" + sr.getTESTID() + "</div>");
			}
		} else if (wswMap.containsKey("O")) {
			html.append("<div style='float:left;height:25px;margin-left:10%;width:90%;font-size:14px;'><b>检验结果</b></div>");
			List<SyncResult> list = wswMap.get("O");
			for(SyncResult sr : list) {
				html.append("<div style='float:left;height:20px;margin-left:20%;width:60%;font-size:16px;'>" + sr.getTESTID() + "</div>");
			}
		} else if (wswMap.containsKey("B")){
			List<SyncResult> list = wswMap.get("B");
			List<SyncResult> ymlist = wswMap.get("A");
			if(list.size() == 1) {
				html.append("<div style='float:left;height:25px;margin-left:10%;width:90%;font-size:14px;'><b>细菌名</b></div>");
				html.append("<div style='float:left;height:20px;margin-left:20%;width:50%;font-size:16px;'>" + list.get(0).getTESTID() + "</div>");
				html.append("<div style='float:left;height:20px;width:10%;font-size:16px;'>");
				html.append(list.get(0).getHINT() != null ? list.get(0).getHINT() : "&nbsp;");
				html.append("</div>");
				boolean isFirst = true;
				if(ymlist != null) {
					for(SyncResult sr2 : ymlist) {
						if(isFirst) {
							isFirst = false;
							html.append("<div style='float:left;height:25px;margin-left:20%;width:20%;font-size:14px;'><b>抗生素名</b></div>");
							html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>结果</b></div>");
							html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>解释</b></div>");
							html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>折点</b></div>");
							html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>单位</b></div>");
							
							html.append("<div style='float:left;height:20px;margin-left:20%;width:20%;font-size:14px;'>" + sr2.getTESTID() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getTESTRESULT() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getHINT() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
							html.append(sr2.getREFHI()!=null && sr2.getREFLO()!= null ? sr2.getREFLO() + "~" + sr2.getREFHI() : "&nbsp;");
							html.append("</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
							html.append(sr2.getUNIT() != null ? sr2.getUNIT() : "&nbsp;");
							html.append("</div>");
						} else {
							html.append("<div style='float:left;height:20px;margin-left:20%;width:20%;font-size:14px;'>" + sr2.getTESTID() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getTESTRESULT() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getHINT() + "</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
							html.append(sr2.getREFHI()!=null && sr2.getREFLO()!= null ? sr2.getREFLO() + "~" + sr2.getREFHI() : "&nbsp;");
							html.append("</div>");
							html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
							html.append(sr2.getUNIT() != null ? sr2.getUNIT() : "&nbsp;");
							html.append("</div>");
						}
					}
				}
			} else {
				int count = 0;
				for(SyncResult sr : list) {
					count++;
					html.append("<div style='float:left;height:25px;margin-left:10%;width:90%;font-size:14px;'><b>细菌名" + count + "</b></div>");
					html.append("<div style='float:left;height:20px;margin-left:20%;width:50%;font-size:16px;'>" + sr.getTESTID() + "</div>");
					html.append("<div style='float:left;height:20px;width:10%;font-size:16px;'>");
					html.append(list.get(0).getHINT() != null ? list.get(0).getHINT() : "&nbsp;");
					html.append("</div>");
					char num = sr.getRESULTFLAG().charAt(sr.getRESULTFLAG().length()-1);
					boolean isFirst = true;
					if(ymlist != null) {
						for(SyncResult sr2 : ymlist) {
							if(sr2.getRESULTFLAG().charAt(sr.getRESULTFLAG().length()-1) == num) {
								if(isFirst) {
									isFirst = false;
									html.append("<div style='float:left;height:25px;margin-left:20%;width:20%;font-size:14px;'><b>抗生素名</b></div>");
									html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>结果</b></div>");
									html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>解释</b></div>");
									html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>折点</b></div>");
									html.append("<div style='float:left;height:25px;width:10%;font-size:14px;'><b>单位</b></div>");
									html.append("<div style='float:left;height:20px;margin-left:20%;width:20%;font-size:14px;'>" + sr2.getTESTID() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getTESTRESULT() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getHINT() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
									html.append(sr2.getREFHI()!=null && sr2.getREFLO()!= null ? sr2.getREFLO() + "~" + sr2.getREFHI() : "&nbsp;");
									html.append("</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
									html.append(sr2.getUNIT() != null ? sr2.getUNIT() : "&nbsp;");
									html.append("</div>");
								} else {
									html.append("<div style='float:left;height:20px;margin-left:20%;width:20%;font-size:14px;'>" + sr2.getTESTID() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getTESTRESULT() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>" + sr2.getHINT() + "</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
									html.append(sr2.getREFHI()!=null && sr2.getREFLO()!= null ? sr2.getREFLO() + "~" + sr2.getREFHI() : "&nbsp;");
									html.append("</div>");
									html.append("<div style='float:left;height:20px;width:10%;font-size:14px;'>");
									html.append(sr2.getUNIT() != null ? sr2.getUNIT() : "&nbsp;");
									html.append("</div>");
								}
							}
							
						}
					}
				}
			}
		}
		return html.toString();
	}
}
