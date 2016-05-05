package com.smart.webapp.controller.individual;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.smart.service.DictionaryManager;
import com.smart.service.EvaluateManager;
import com.smart.service.UserManager;
import com.smart.service.lis.CollectSampleManager;
import com.smart.service.lis.ReasoningModifyManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PatientUtil;
import com.zju.api.service.RMIService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.smart.Constants;
import com.smart.Dictionary;
import com.smart.model.lis.CollectSample;
import com.smart.model.lis.ReasoningModify;
import com.smart.model.lis.Sample;
import com.smart.model.rule.Item;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.user.Evaluate;
import com.smart.model.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/collect/list*")
public class CollectDialogController {

	@RequestMapping(value = "/evaluate*", method = RequestMethod.POST)
	@ResponseBody
	public void evaluateSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String sampleno = request.getParameter("sample");
		String text = request.getParameter("text");
		String collector = request.getParameter("collector");
		String username = request.getRemoteUser();
		User user = userManager.getUserByUsername(username);
		String name = user.getName();
		
		Evaluate e = new Evaluate();
		e.setCollector(collector);
		e.setContent(text);
		e.setEvaluatetime(new Date());
		e.setEvaluator(name);
		e.setSampleno(sampleno);
		evaluateManager.save(e);
		
		user.setEvaluatenum(user.getEvaluatenum()+1);
		userManager.save(user);
	}
	
	@RequestMapping(value = "/cancel*", method = RequestMethod.POST)
	@ResponseBody
	public boolean collectCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String collector = request.getParameter("userid");
		String sampleno = request.getParameter("sampleno");
		String userid=request.getRemoteUser();
		User user = userManager.getUserByUsername(userid);

		if (!StringUtils.isEmpty(userid) && !StringUtils.isEmpty(sampleno)) {
			if(collector.equals(userid)) {
				collectSampleManager.removeCollectSample(collector, sampleno);
				user.setCollectNum(user.getCollectNum()-1);
				userManager.save(user);
			} else {
				return false;
			}
		}
		return true;
	}
	
	@RequestMapping(value = "/collect*", method = RequestMethod.POST)
	@ResponseBody
	public boolean collectSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleno = request.getParameter("sample");
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		String bamc = request.getParameter("bamc");
		String username = request.getRemoteUser();
		User user = userManager.getUserByUsername(username);
		String name = user.getName();
		
		if(!collectSampleManager.isSampleCollected(username, sampleno)) {
			CollectSample cs = new CollectSample();
			cs.setName(name);
			cs.setUsername(username);
			cs.setSampleno(sampleno);
			cs.setBamc(bamc);
			cs.setType(type);
			cs.setCollecttime(new Date());
			collectSampleManager.collectSample(cs);
			
			Evaluate e = new Evaluate();
			e.setCollector(username);
			e.setContent(text);
			e.setEvaluatetime(new Date());
			e.setEvaluator(name);
			e.setSampleno(sampleno);
			evaluateManager.save(e);
			System.out.println(e.getId());
			user.setCollectNum(user.getCollectNum()+1);
			userManager.save(user);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/searchBAMC", method = { RequestMethod.GET })
	@ResponseBody
	public String searchBAMC(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}

		List<CollectSample> csList = collectSampleManager.getCollectSampleByName(name);
		JSONArray array = new JSONArray();
		
		if (csList != null) {
			for (int i=0; i<csList.size(); i++) {
				CollectSample cs = csList.get(i);
				JSONObject o = new JSONObject();
				o.put("id", i);
				o.put("name", cs.getBamc());
				array.put(o);
			}
		}

		response.setContentType("charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
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
			return null;
//			throw new NullPointerException();

		Sample info = sampleManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String ruleIds = info.getRuleIds();
//		String customResult = user.getReasoningResult();
		String customResult = null;

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
//			String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null || customResult == null || customResult.contains(re.getCategory())) {
					double rank = getRank(rule, re);
					if (rule.getType() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", rule.getId() + "+" + re.getId());
//						map.put("content", reason);
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
//			log.error(e.getMessage());
		}
		
		return sb;

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
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ReasoningModifyManager reasoningModifyManager;
	
	@Autowired
	private ItemManager itemManager;
}
