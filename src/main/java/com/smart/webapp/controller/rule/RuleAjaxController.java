package com.smart.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.Dictionary;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Index;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.model.user.User;
import com.smart.model.rule.Item;
import com.smart.webapp.controller.lis.audit.BaseAuditController;

@Controller
@RequestMapping("/ajax*")
public class RuleAjaxController extends BaseAuditController {

	private String bagJson = null;
	private AtomicBoolean isChanged = new AtomicBoolean(true);
	
	@RequestMapping(value = "/getBag", method = { RequestMethod.GET })
	@ResponseBody
	public String getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		if (isChanged.get()) {
			List<Bag> bags = bagManager.getBagByHospital(hospital);
			JSONArray cell = new JSONArray();
			for (int i = 0; i < bags.size(); i++) {
				JSONObject sb = new JSONObject();
				sb.put("id", bags.get(i).getId());
				sb.put("pId", bags.get(i).getParenetID());
				sb.put("name", bags.get(i).getName());
				cell.put(sb);
			}
			bagJson = cell.toString();
			if (bags.size() != 0)
				isChanged.set(false);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(bagJson);
		return null;
	}

	@RequestMapping(value = "/editBag", method = { RequestMethod.POST })
	public void updateData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		Long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		long hospital = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		Bag bag = new Bag();
		if (action.equals("add")) {
			bag.setParenetID(id);
			bag.setName(name);
			bag.setHospitalId(hospital);
			bagManager.save(bag);
		} else if (action.equals("rename")) {
			bag = bagManager.get(id);
			bag.setName(name);
			bag.setHospitalId(hospital);
			bagManager.save(bag);
		} else if (action.equals("remove")) {
			List<Bag> bags = bagManager.getBag(id);
			if (bags.size() > 0) {
				for (Bag b : bags) {
					bagManager.remove(b.getId());
				}
			}
			bagManager.remove(id);
		} else if (action.equals("draginner")) {
			Long id2 = Long.parseLong(name);
			bag = bagManager.get(id);
			bag.setParenetID(id2);
			bagManager.save(bag);
		} else {
			Long id2 = Long.parseLong(name);
			Bag bag2 = bagManager.get(id2);
			bag = bagManager.get(id);
			bag.setParenetID(bag2.getParenetID());
			bagManager.save(bag);
		}
		isChanged.set(true);
	}
	
	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String percent = request.getParameter("percent");
		Result result = new Result();
		result.setContent(content);
		result.setCategory(category);
		result.setPercent(percent);
		
		// 创建者信息保存
		String userName = request.getRemoteUser();
		User user = userManager.getUserByUsername(userName);
		Date now = new Date();
		result.setCreateUser(user.getName());
		result.setCreateTime(now);
		result.setModifyUser(user.getName());
		result.setModifyTime(now);
		
		Result newResult = resultManager.save(result);
		return newResult.getId().toString();
	}
	
	@RequestMapping(value = "/getInfo*", method = RequestMethod.GET)
	@ResponseBody
	public String searchAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Rule> rules = ruleManager.searchRule(name);
		List<Index> indexs = indexManager.getIndexs(name);
		List<Result> results = resultManager.getResults(name);
		if (name.length() == 4) {
			Index i = indexManager.getIndex(name);
			if (i != null) {
				indexs.add(0, i);
			}
		}
		
		JSONArray array = new JSONArray();
		
		for (Rule rule : rules) {
			JSONObject r = new JSONObject();
			r.put("id", rule.getId());
			r.put("name", rule.getName());
			r.put("category", "R");
			array.put(r);
		}
		Map<String, String> map = new HashMap<String, String>();
		for (Index index : indexs) {
			//String unit = index.getUnit();
			String sample = index.getSampleFrom();
			if (map.containsKey(sample)) {
				sample = map.get(index.getSampleFrom());
			}
			String indexStr = index.getName() + " (" + sample + ")";
			/*String indexStr = index.getName() + " (" + sample;
			if (!StringUtils.isEmpty(unit)) {
				unit = "," + unit;
			} else {
				unit = "";
			}
			indexStr += unit + ")";*/
			
			JSONObject i = new JSONObject();
			i.put("id", index.getId());
			i.put("name", indexStr);
			i.put("category", "I");
			array.put(i);
		}
		for (Result result : results) {
			JSONObject t = new JSONObject();
			t.put("id", result.getId());
			t.put("name", result.getContent());
			t.put("category", "T");
			array.put(t);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		
		return null;
	}
	
	@RequestMapping(value = "/getRule*", method = RequestMethod.GET)
	@ResponseBody
	public String getRuleJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		String json = null;
		
		if(idMap.size() == 0) initMap();

		if (!StringUtils.isEmpty(id)) {
			Rule r = ruleManager.get(Long.parseLong(id));
			try {
				json = getItemJson(r);
			} catch (Exception e) {
				e.printStackTrace();
				json = null;
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		return null;
	}
	
	private String getItemJson(Rule r) throws Exception {

		// 将知识点，病人信息放入map
		HashMap<String, String> map = new HashMap<String, String>();
		for (Item i : r.getItems()) {
			String unit = i.getUnit();
			unit = StringUtils.isEmpty(unit) ? "" : ("," + unit);
			map.put("I" + i.getId().toString(),
					idMap.get(i.getIndexId()).getName() + ":" + i.getValue() + " (" + idMap.get(i.getIndexId()).getSampleFrom()
							+ unit + ")");
		}
		List<Dictionary> pInfo = dictionaryManager.getPatientInfo("");
		if (pInfo != null) {
			for (Dictionary p : pInfo) {
				map.put("P" + p.getId().toString(), p.getValue());
			}
		}

		if (StringUtils.isEmpty(r.getRelation()))
			return null;

		JSONObject json = new JSONObject(r.getRelation());

		return addJsonData(json, map).toString();
	}

	/**
	 * 递归调用，补全知识点的信息
	 * 
	 * @param obj
	 * @param map
	 *            知识点id和内容的映射表
	 * @return
	 * @throws JSONException
	 */
	private JSONObject addJsonData(JSONObject obj, HashMap<String, String> map) throws JSONException {

		if (!obj.has("id")) {
			return null;
		}

		String id = obj.getString("id");

		if ("and".equals(id)) {
			obj.put("data", "并且");
		} else if ("or".equals(id)) {
			obj.put("data", "或者");
		} else if ("not".equals(id)) {
			obj.put("data", "非");
		} else if (id.startsWith("R")) {
			obj.put("data", resultManager.get(Long.parseLong(id.substring(1))).getContent());
		} else {
			obj.put("data", map.get(id));
		}
		// 移除id，将其加到attr中
		obj.put("metadata", new JSONObject().put("id", id));
		obj.remove("id");

		if (obj.has("children")) {
			JSONArray array = obj.getJSONArray("children");
			// System.out.println(array.length());
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				this.addJsonData(o, map);
			}
		}
		return obj;
	}
	
	
/*	@RequestMapping(value = "/buildkbase*", method = RequestMethod.POST)
	@ResponseBody
	public void rebuildKBase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnalyticUtil util = new AnalyticUtil(itemManager, resultManager);
		Reader reader = util.getReader(ruleManager.getRuleByTypes("0,3,4,5,6,7"));
		DroolsRunner.getInstance().rebuildKbase(reader);
		reader.close();
	}*/
}
