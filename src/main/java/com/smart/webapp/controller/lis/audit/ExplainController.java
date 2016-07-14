package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;		
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.ReasoningModify;
import com.smart.model.lis.Sample;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.service.lis.ReasoningModifyManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.ExplainUtil;
import com.smart.Constants;

@Controller
@RequestMapping("/audit*")
public class ExplainController extends BaseAuditController {

	@Autowired
	private ReasoningModifyManager reasoningModifyManager = null;
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
		String needreason = request.getParameter("needReason");
		if(needreason==null || needreason.isEmpty())
			needreason = "true";
		boolean needReason = Boolean.parseBoolean(needreason);
		if (id.equals("null") || StringUtils.isEmpty(id))
			return null;
//			throw new NullPointerException();
		Sample info = new Sample();
		if(StringUtils.isNumeric(id))
			info = sampleManager.get(Long.parseLong(id));
		else 
			info = sampleManager.getBySampleNo(id);
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
		if (idMap.size() == 0)
			initMap();
		ExplainUtil explainUtil = new ExplainUtil(itemManager, dictionaryManager, idMap);
		for (Rule rule : rules) {
			String reason = "";
			if(needReason)
				reason = explainUtil.getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null || customResult == null || customResult.contains(re.getCategory())) {
					double rank = explainUtil.getRank(rule, re);
					if (rule.getType() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", rule.getId() + "+" + re.getId());
						if(needReason)
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
			reasoningModify.setSampleId(docNo);
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
		
//		System.out.println(modifyId+result+oldResult+content);

		if (!StringUtils.isEmpty(modifyId) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setNewResult(result);
			reasoningModify.setOldResult(oldResult);
			reasoningModify.setContent(content);
			reasoningModify.setModifyId(modifyId);
			reasoningModify.setSampleId(docNo);
			reasoningModify.setType(Constants.EDIT);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}
	
	/**
	 * 添加一条智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addResult*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String result = request.getParameter("result");
		String docNo = request.getParameter("docNo");
		int addCount = reasoningModifyManager.getAddNumber();

		if (!StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setOldResult(result);
			reasoningModify.setNewResult(result);
			reasoningModify.setContent(content);
			reasoningModify.setSampleId(docNo);
			reasoningModify.setModifyId("add" + addCount);
			reasoningModify.setType(Constants.ADD);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}
}
