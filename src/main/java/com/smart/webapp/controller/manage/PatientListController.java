package com.smart.webapp.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.rule.Index;
import com.smart.model.rule.Item;
import com.smart.Dictionary;
import com.zju.api.model.Patient;
import com.smart.model.lis.Sample;
import com.smart.model.lis.ReasoningModify;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.lis.ReasoningModifyManager;
import com.smart.service.rule.RuleManager;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.IndexMapUtil;
import com.smart.webapp.util.PatientUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;

@Controller
@RequestMapping("/manage/patientList*")
public class PatientListController {

	private static Log log = LogFactory.getLog(PatientListController.class);
	private RMIService rmiService = null;
	private SampleManager sampleManager = null;
	private IndexManager indexManager = null;
	private ItemManager itemManager = null;
	private RuleManager ruleManager = null;
	private UserManager userManager = null;
	private ReasoningModifyManager reasoningModifyManager = null;
	private Map<String, Index> idMap = new HashMap<String, Index>();
	private static IndexMapUtil util = IndexMapUtil.getInstance();
	
	@Autowired
	private TestResultManager testResultManager = null;
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
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
		Map<String, Object> map = new HashMap<String, Object>();
//		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		if (info != null) {
			map.put("id", info.getPatientId());
			map.put("name", info.getPatient().getPatientName());
			map.put("age", String.valueOf(info.getPatient().getAge()));
			String ex = info.getDiagnostic().trim();
//			if (ex.length() > 16) {
//				ex = ex.substring(0, 16) + "...";
//			}
			map.put("examinaim", ex);
			map.put("diagnostic", info.getDiagnostic());
			map.put("section", info.getSection().getName());
			map.put("sex", info.getPatient().getSexValue());
			if (StringUtils.isEmpty(info.getPatient().getBlh())) {
				List<Patient> list = rmiService.getPatientList("'" + info.getPatientId() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", info.getPatient().getBlh());
			}
			map.put("type",info.getSampleType());
//			map.put("type",
//					SampleUtil.getInstance().getSampleList(indexManager).get(String.valueOf(info.getSampleType())));
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
		User user = userManager.getUserByUsername(request.getRemoteUser());
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

		for (Rule rule : rules) {
			String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null ) {
					double rank = getRank(rule, re);
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
	
	private StringBuilder getItem(JSONObject root, StringBuilder sb) {
		try {
			if ("and".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 并 ");
					}
				}
			} else if ("or".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 或 ");
					}
				}
				sb.append(")");
			} else if ("not".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("非(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
				}
				sb.append(")");
			} else {
				sb.append(getItemStr(root.get("id").toString()));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return sb;

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

	private String getItemStr(String id) {
		String result = "";
		Long ID = Long.parseLong(id.substring(1));
		if (id.startsWith("P")) {
			Dictionary lib = PatientUtil.getInstance().getInfo(ID, dictionaryManager);
			result = lib.getValue();
		} else {
			Item item = itemManager.get(ID);
			String testName = item.getIndex().getName();
			String value = item.getValue();
			if (value.contains("||")) {
				return testName + value.replace("||", "或");
			} else if (value.contains("&&")) {
				return testName + value.replace("&&", "且");
			}
			result = testName + value;
		}
		return result;
	}

	private double getRank(Rule rule, Result re) {
		double importance = 0;
		for (Item item : rule.getItems()) {
			String impo = item.getIndex().getImportance();
			if (impo != null && !StringUtils.isEmpty(impo)) {
				importance = Double.parseDouble(impo) + importance;
			}
		}
		double level = 0;
		if (re.getLevel() != null && !StringUtils.isEmpty(re.getLevel())) {
			level = Double.parseDouble(re.getLevel());
		}
		double precent = 0;
		if (re.getPercent() != null && !StringUtils.isEmpty(re.getPercent())) {
			precent = Double.parseDouble(re.getPercent());
		}
		return importance * 0.5 + level * 0.3 + precent * 0.1;
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

		Sample info = sampleManager.getBySampleNo(sampleNo);
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		if (info != null) {
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getYlxh());
			if (list.size() >= 2) {
				for (TestResult result : list.get(1).getResults()) {
					resultMap1.put(result.getTestId(), result.getTestResult());
				}
			}
			if (list.size() >= 3) {
				for (TestResult result : list.get(2).getResults()) {
					resultMap2.put(result.getTestId(), result.getTestResult());
				}
			}
			if (list.size() >= 4) {
				for (TestResult result : list.get(3).getResults()) {
					resultMap3.put(result.getTestId(), result.getTestResult());
				}
			}
		}
		Set<String> tests = new HashSet<String>();
		String ts = info.getMarkTests();
		if (!StringUtils.isEmpty(ts)) {
			for (String s : ts.split(",")) {
				tests.add(s);
			}
		}

		List<TestResult> list = testResultManager.getTestBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).getTestId();
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)||idMap.containsKey(util.getValue(id))) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("name", idMap.get(util.getValue(list.get(i).getTestId())).getName());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(testId) ? resultMap3.get(testId) : "");
				map.put("scope", list.get(i).getRefLo() + "-" + list.get(i).getRefHi());
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(util.getValue(list.get(i).getTestId())).getKnowledgename());
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);

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
		String type = request.getParameter("type");
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int select = Integer.parseInt(type);

		List<Sample> list = new ArrayList<Sample>();
		if (select == 1) {
			list = sampleManager.getHistorySample(text,text);
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
			map.put("examinaim", info.getDiagnostic());
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
		if(patientId == null){
			return view;
		}
		return view.addObject("patientId",patientId).addObject("blh", blh);
	}

	synchronized private void initMap() {
		//List<TestDescribe> list = testDescribeManager.getAll();
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
	}

	@Autowired
	public void setSampleManager(SampleManager sampleManager) {
		this.sampleManager = sampleManager;
	}
	
	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	
	@Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setReasoningModifyManager(
			ReasoningModifyManager reasoningModifyManager) {
		this.reasoningModifyManager = reasoningModifyManager;
	}
	
	@Autowired
	public void setRmiService(RMIService rmiService) {
		this.rmiService = rmiService;
	}
}
