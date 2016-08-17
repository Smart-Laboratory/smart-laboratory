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

import com.smart.service.DictionaryManager;
import com.smart.service.EvaluateManager;
import com.smart.service.lis.CollectSampleManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.model.lis.CollectSample;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.DataResponse;
import com.zju.api.service.RMIService;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;

import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.model.lis.Process;
import com.smart.model.user.Evaluate;

@Controller
@RequestMapping("/collect/list*")
public class CollectController extends BaseAuditController {
	
	
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
		if (idMap.size() == 0) initMap();
		Sample info = sampleManager.getBySampleNo(sample);
		Process process = processManager.getBySampleId(info.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
		if (info != null) {
			map.put("id", info.getId());
			map.put("name", info.getPatientname());
			map.put("age", info.getAge());
			String ex = info.getInspectionName().trim();
			if (ex.length() > 16) {
				ex = ex.substring(0, 16) + "...";
			}
			map.put("examinaim", ex);
			map.put("diagnostic", info.getDiagnostic());
			map.put("stayhospitalmode", info.getStayHospitalMode()==1 ? "门诊":"住院");
			map.put("section", sectionutil.getLabValue(info.getSectionId()));

			String note = info.getNotes();
			if (!StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					if (rule.getType() == 3 || rule.getType() == 4) {
						String result = rule.getResultName();
						String itemString = "";
						for (Item i : rule.getItems()) {
							itemString = itemString + idMap.get(i.getIndexId()).getName() + "、";
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
			map.put("sex", info.getSexValue());
			if (StringUtils.isEmpty(info.getPatientblh())) {
				List<com.zju.api.model.Patient> list = rmiService.getPatientList("'" + info.getPatientId() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", info.getPatientblh());
			}
			map.put("patientId", info.getPatientId());
			map.put("requester", process.getRequester());
			map.put("type",
					SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(info.getSampleType())));
			
			Calendar c = Calendar.getInstance(); 
			c.add(Calendar.DAY_OF_MONTH,-7);
			map.put("history", info.getPatientname() + "最近7天共做检查" + sampleManager.getSampleByPatientName(df.format(c.getTime()), df.format(new Date()), info.getPatientname()).size() + "次，点击查看详情。");
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
		if (deviceMap.size() == 0)
			initDeviceMap();
		
		if(likeLabMap.size() == 0) {
			initLikeLabMap();
		}
		
		Sample info = sampleManager.getBySampleNo(sampleNo);
		if (info == null) {
			return null;
		}

		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		Map<String, String> resultMap4 = new HashMap<String, String>();
		Map<String, String> resultMap5 = new HashMap<String, String>();
			
		Process process = processManager.getBySampleId(info.getId());
		List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatientblh(), info.getSectionId());
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
		long curInfoReceiveTime = process.getReceivetime().getTime();
		int index = 0;
		Map<String, String> rmap = null;
		List<TestResult> now = testResultManager.getTestBySampleNo(info.getSampleNo());
		Set<String> testIdSet = new HashSet<String>();
		for (TestResult t : now) {
			testIdSet.add(t.getTestId());
		}
		String day = mdf.format(process.getReceivetime());
		
		if(list.size() > 0) {
			for (Sample pinfo : list) {
				boolean isHis = false;
				List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
				if(his == null)
					continue;
				for (TestResult test: his) {
					if (testIdSet.contains(test.getTestId())) {
						isHis = true;
						break;
					}
				}
				if (hisProcessMap.get(pinfo.getId()).getReceivetime() == null) {
					continue;
				}
				if (hisProcessMap.get(pinfo.getId()).getReceivetime().getTime() < curInfoReceiveTime && isHis) {
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
					for (TestResult result : hisTestMap.get(pinfo.getSampleNo())) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					String pDay = mdf.format(hisProcessMap.get(pinfo.getId()).getReceivetime());
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
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < now.size(); i++) {
			if (now.get(i).getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = now.get(i).getTestId();
			if (colorMap!=null && colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = now.get(i).getTestId();
				map.put("id", id);
				map.put("color", color);
				map.put("name", idMap.get(now.get(i).getTestId()).getName());
				map.put("ab", idMap.get(now.get(i).getTestId()).getEnglish());
				map.put("result", now.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(testId) ? resultMap3.get(testId) : "");
				map.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(testId) ? resultMap4.get(testId) : "");
				map.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(testId) ? resultMap5.get(testId) : "");
				String lo = now.get(i).getRefLo();
				String hi = now.get(i).getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", now.get(i).getUnit());
				map.put("knowledgeName", idMap.get(now.get(i).getTestId()).getKnowledgename());
				map.put("editMark", now.get(i).getEditMark());
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
	private CollectSampleManager collectSampleManager;
	
	@Autowired
	private SampleManager sampleManager;
	
	@Autowired
	private ProcessManager processManager;
	
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
