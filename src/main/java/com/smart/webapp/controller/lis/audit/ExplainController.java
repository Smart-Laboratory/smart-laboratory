package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Dictionary;
import com.smart.model.lis.ReasoningModify;
import com.smart.model.lis.Sample;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.reasoningModifyManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PatientUtil;
import com.smart.Constants;
import com.smart.model.rule.Item;;



@Controller
@RequestMapping("/audit*")
public class ExplainController extends BaseAuditController{

	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private reasoningModifyManager reasoningModifyManager = null;
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
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
			String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null || customResult == null || customResult.contains(re.getCategory())) {
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
	
	/**
	 * 拖拽智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/drag*", method = RequestMethod.POST)
	@ResponseBody
	public boolean dragResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String content = request.getParameter("content");
		String docNo = request.getParameter("id");
		int dragCount = reasoningModifyManager.getDragNumber();

		if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setModifyId("drag" + dragCount);
			reasoningModify.setContent(content);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setType(Constants.DRAG);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}
	
	/**
	 * 编辑智能解释的某结果值
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/explain/edit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean editExplain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String modifyId = request.getParameter("id");
		String result = request.getParameter("result");
		String oldResult = request.getParameter("oldResult");
		String docNo = request.getParameter("docNo");
		String content = request.getParameter("content");
		
		System.out.println(modifyId+result+oldResult+content);

		if (!StringUtils.isEmpty(modifyId) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setNewResult(result);
			reasoningModify.setOldResult(oldResult);
			reasoningModify.setContent(content);
			reasoningModify.setModifyId(modifyId);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setType(Constants.EDIT);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}
}
