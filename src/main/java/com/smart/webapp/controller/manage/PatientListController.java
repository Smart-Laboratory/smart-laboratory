package com.smart.webapp.controller.manage;

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
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.model.SyncResult;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Section;
import com.smart.model.lis.Process;
import com.smart.model.lis.ReasoningModify;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.ReasoningModifyManager;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.IndexMapUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.ExplainUtil;

@Controller
@RequestMapping("/manage/patientList*")
public class PatientListController extends BaseAuditController {

	private static IndexMapUtil util = IndexMapUtil.getInstance();
	
	@Autowired
	private SectionManager sectionManager;
	
	@Autowired
	private ReasoningModifyManager reasoningModifyManager;
	
	/**
	 * 获取样本中的病人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/patient*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPatientInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		if (id == null) {
			throw new NullPointerException();
		}

		Sample info = sampleManager.get(Long.parseLong(id));
		Section section = sectionManager.getByCode(info.getSectionId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (info != null) {
			map.put("id", info.getPatientId());
			map.put("name", info.getPatientname());
			map.put("age", info.getAge());
			/*String ex = info.getInspectionName();
			if (ex.length() > 16) {
				ex = ex.substring(0, 16) + "...";
			}*/
			map.put("examinaim", info.getInspectionName());
			map.put("diagnostic", info.getDiagnostic());
			map.put("section", section.getName());
			map.put("sex", info.getSexValue());
			map.put("blh", info.getPatientblh());
			map.put("type", SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(info.getSampleType())));
		}
		return map;
	}
	
	/**
	 * 获取样本中的智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/explain*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getIntelExplain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id))
			throw new NullPointerException();

		Sample info = sampleManager.get(Long.parseLong(id));
		String ruleIds = info.getRuleIds();

		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (StringUtils.isEmpty(ruleIds)) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Rule> rules = ruleManager.getRuleList(ruleIds);

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(rules.size());

		if (idMap.size() == 0)
			initMap();
		
		ExplainUtil explainUtil = new ExplainUtil(itemManager, dictionaryManager, idMap);
		for (Rule rule : rules) {
			String reason = explainUtil.getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null ) {
					double rank = explainUtil.getRank(rule, re);
					if (rule.getType() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", rule.getId() + "+" + re.getId());
						map.put("content", reason);
						map.put("rank", rank);  
						map.put("oldResult", re.getContent());
						map.put("result", re.getContent());
						dataRows.add(map);
					}
				}
			}
		}
		List<ReasoningModify> modifyList = reasoningModifyManager.getBySampleId(id);
		dataResponse.setRows(modifyData(modifyList, dataRows));
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	

	private List<Map<String, Object>> modifyData(List<ReasoningModify> modifyList, List<Map<String, Object>> dataRows) {
		Map<String, ReasoningModify> modifyMap = new HashMap<String, ReasoningModify>();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> returnRows = new ArrayList<Map<String, Object>>();
		String dragResult = null;
		for (ReasoningModify r : modifyList) {
			if (r.getType().equals(Constants.ADD)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.getModifyId());
				map.put("content", r.getContent());
				map.put("rank", 0);
				map.put("oldResult", r.getOldResult());
				map.put("result", r.getNewResult());
				dataRows.add(map);
			} else if (r.getType().equals(Constants.DRAG)) {
				dragResult = r.getContent();
			} else {
				modifyMap.put(r.getModifyId(), r);
			}
		}

		for (Map<String, Object> m : dataRows) {
			if (modifyMap.containsKey(m.get("id"))) {
				ReasoningModify rm = modifyMap.get(m.get("id"));
				if (rm.getType().equals(Constants.EDIT)) {
					m.put("oldResult", rm.getOldResult());
					m.put("result", rm.getNewResult());
					m.put("content", rm.getContent());
					rows.add(m);
				}
			} else {
				rows.add(m);
			}
		}

		if (dragResult != null) {
			for (String s : dragResult.split(",")) {
				for (Map<String, Object> ma : rows) {
					if (ma.get("id").equals(s)) {
						returnRows.add(ma);
					}
				}
			}
		} else {
			return rows;
		}
		return returnRows;
	}
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
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}

		Sample info = sampleManager.getListBySampleNo(sampleNo).get(0);
		Process process = processManager.getBySampleId(info.getId());
		List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleNo);
		
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
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
			if(list!=null && list.size()>0){
			for (Sample pinfo : list) {
				boolean isHis = false;
				List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
				if(his==null)
					continue;
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
						break;
					case 1:
						rmap = resultMap2;
						break;
					case 2:
						rmap = resultMap3;
						break;
					}
					for (TestResult result : his) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					index++;
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
				map.put("knowledgeName", idMap.get(tr.getTestId()).getKnowledgename());
				map.put("editMark", tr.getEditMark());
				map.put("lastEdit", editMap.size() == 0 || !editMap.containsKey(id) ? "" : "上次结果 " + editMap.get(id));
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
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
		String text = request.getParameter("text");
		String patientId = request.getParameter("patientId");
		String blh = request.getParameter("blh");
		String type = request.getParameter("type");
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int select = Integer.parseInt(type);

		List<Sample> list = new ArrayList<Sample>();
		if (select == 1) {
			list = sampleManager.getHistorySample(patientId,blh,"");
		} else if (select==2 && text != null && from != null && to != null) {
			list = sampleManager.getSampleByPatientName(from, to, text);
		} else if (select==3) {
			Sample p = sampleManager.get(Long.parseLong(text));
			if (p != null) {
				list.add(p);  
			}
		}
		if(list.size()==0)
			return dataResponse;
		
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
			Sample info = list.get(start + index);
			map.put("id",info.getId());
			map.put("sample",info.getSampleNo());
			//map.put("receivetime", info.getExecutetime() == null ? "" : sdf.format(info.getExecutetime()));
			if (info.getAuditStatus() == -1) {
				map.put("type", "<font color='red'>无结果</font>");
			} else {
				map.put("type", "有结果");
			}
			if (info.getSampleStatus()>=6) {
				map.put("type", "已打印");
			}
			map.put("examinaim", info.getInspectionName());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(idMap.size()==0){
			initMap();
		}
		ModelAndView view=new ModelAndView();
		String patientId = request.getParameter("patientId");
		String blh = request.getParameter("blh");
		return view.addObject("patientId",patientId).addObject("blh", blh);
	}

}
