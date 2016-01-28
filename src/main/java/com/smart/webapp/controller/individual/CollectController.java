package com.smart.webapp.controller.individual;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.EvaluateManager;
import com.smart.service.lis.CollectSampleManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.RuleManager;
import com.smart.model.lis.CollectSample;
import com.smart.webapp.util.DataResponse;
import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;

import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.model.lis.Patient;
import com.smart.model.user.Evaluate;




@Controller
@RequestMapping("/collect/list*")
public class CollectController {
	
	private Map<String, Index> idMap = new HashMap<String, Index>();
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
	private final static SimpleDateFormat mdf = new SimpleDateFormat("MM/dd");
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
		String select = request.getParameter("select");
		String bamc = request.getParameter("bamc");
		String type = request.getParameter("type");
		String userid=request.getRemoteUser();
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		List<CollectSample> list = collectSampleManager.getCollectSample(userid);

		if (idMap.size() == 0) initMap();
		if (!StringUtils.isEmpty(select) && select.equals("2")) {
			list = collectSampleManager.getAll();
		}
		
		if (!StringUtils.isEmpty(bamc)) {
			list = collectSampleManager.getCollectSampleByName(bamc);
		}
		
		if (!StringUtils.isEmpty(type)) {
			list = collectSampleManager.getCollectSampleByType(type.substring(0, type.length()-1));
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
			CollectSample cs = list.get(start + index);
			map.put("id", start + index);
			map.put("userid",cs.getUsername());
			map.put("name",cs.getName());
			map.put("bamc",cs.getBamc());
			map.put("sampleno",cs.getSampleno());
			map.put("type", cs.getType());
			map.put("time",sdf.format(cs.getCollecttime()));
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	synchronized private void initMap() {
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
	}
	
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
	public Map<String, Object> getPatient(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String sample = request.getParameter("sample");
		if (sample == null) {
			throw new NullPointerException();
		}

		Sample info = sampleManager.getBySampleNo(sample);
		Patient patient = info.getPatient();
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		if (info != null) {
			map.put("id", info.getId());
			map.put("name", patient.getPatientName());
			map.put("age", String.valueOf(patient.getAge()));
			String ex = info.getInspectionName().trim();
			if (ex.length() > 16) {
				ex = ex.substring(0, 16) + "...";
			}
			map.put("examinaim", ex);
			map.put("diagnostic", info.getDiagnostic());
			map.put("stayhospitalmode", info.getStayHospitalMode()==1 ? "门诊":"住院");
			map.put("section", sectionutil.getValue(info.getSection().getCode()));

			String note = info.getNotes();
			if (!StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					if (rule.getType() == 3 || rule.getType() == 4) {
						String result = rule.getResultName();
						String itemString = "";
						for (Item i : rule.getItems()) {
							itemString = itemString + i.getIndex().getName() + "、";
						}
						if (note != null && !note.isEmpty()) {
							note = note + "<br>" + itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						} else {
							note = itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						}
					}
				}
			}

			map.put("reason", note);
			map.put("mark", info.getAuditMark());
			map.put("sex", patient.getSexValue());
			if (StringUtils.isEmpty(patient.getBlh())) {
				List<com.zju.api.model.Patient> list = rmiService.getPatientList("'" + info.getPatientId() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", patient.getBlh());
			}
			map.put("patientId", info.getPatientId());
			map.put("requester", info.getProcess().getRequester());
			map.put("type",
					SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(info.getSampleType())));
			
			Calendar c = Calendar.getInstance(); 
			c.add(Calendar.DAY_OF_MONTH,-7);
			map.put("history", patient.getPatientName() + "最近7天共做检查" + sampleManager.getSampleByPatientName(df.format(c.getTime()), df.format(new Date()), patient.getPatientName()).size() + "次，点击查看详情。");
		}
		return map;
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

		String sampleNo = request.getParameter("sample");
		DataResponse dataResponse = new DataResponse();
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";
		String sameSample = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();
		
		Sample info = sampleManager.getBySampleNo(sampleNo);

		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		Map<String, String> resultMap4 = new HashMap<String, String>();
		Map<String, String> resultMap5 = new HashMap<String, String>();
		if (info != null) {
			String lab = info.getSection().getCode();
			List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getSection().getCode(), lab);
			long curInfoReceiveTime = info.getProcess().getReceivetime().getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			String day = mdf.format(info.getProcess().getReceivetime());
			
			for (Sample pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					if (testIdSet.contains(test.getTestId())) {
						isHis = true;
						break;
					}
				}
				if (pinfo.getProcess().getReceivetime() == null) {
					continue;
				}
				if (pinfo.getProcess().getReceivetime().getTime() < curInfoReceiveTime && isHis) {
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
					String pDay = mdf.format(pinfo.getProcess().getReceivetime());
					hisDate += pDay;
					index++;
					
					if (day.equals(pDay)) {
						if (!"".equals(sameSample)) {
							sameSample += ",";
						}
						sameSample += pinfo.getSampleNo();
					}
				}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = null;
		if(info.getMarkTests()!=null){
			 colorMap = StringToMap(info.getMarkTests());
			
		}
		List<TestResult> list = testResultManager.getTestBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = list.get(i).getTestId();
			if (colorMap!=null && colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("color", color);
				map.put("name", idMap.get(list.get(i).getTestId()).getName());
				map.put("ab", idMap.get(list.get(i).getTestId()).getEnglish());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(testId) ? resultMap3.get(testId) : "");
				map.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(testId) ? resultMap4.get(testId) : "");
				map.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(testId) ? resultMap5.get(testId) : "");
				String lo = list.get(i).getRefLo();
				String hi = list.get(i).getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(list.get(i).getTestId()).getKnowledgename());
				map.put("editMark", list.get(i).getEditMark());
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	private Map<String, Integer> StringToMap(String ts) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : ts.split(";")) {
			if (!"".equals(s) && s.contains(":")) {
				String[] array = s.split(":");
				map.put(array[0], Integer.parseInt(array[1]));
			}
		}
		return map;
	}
	
	/**
	 * 根据条件查询该检验人员的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/evaluatedata*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getEvaluateData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sampleno = request.getParameter("sample");
		String collector = request.getParameter("collector");
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);

		if (idMap.size() == 0) initMap();
		List<Evaluate> list = evaluateManager.getByBA(sampleno, collector);
		
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
			Evaluate e = list.get(start + index);
			map.put("id", start + index);
			map.put("evaluator",e.getEvaluator());
			map.put("content",e.getContent());
			map.put("time",sdf.format(e.getEvaluatetime()));
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	
	
	
	
	
	@Autowired
	private IndexManager indexManager;
	
	@Autowired
	private CollectSampleManager collectSampleManager;
	
	@Autowired
	private SampleManager sampleManager;
	
	@Autowired
	private TestResultManager testResultManager;
	
	@Autowired
	private RMIService rmiService;
	
	@Autowired
	private RuleManager ruleManager;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@Autowired
	private EvaluateManager evaluateManager;
}
