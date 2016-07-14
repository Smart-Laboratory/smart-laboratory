package com.smart.webapp.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.smart.model.Dictionary;
import com.smart.model.rule.Index;
import com.smart.model.rule.Item;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.service.DictionaryManager;
import com.smart.service.rule.ItemManager;

public class ExplainUtil {
	
	private ItemManager itemManager = null ;
	private DictionaryManager dictionaryManager = null;
	private Map<String, Index> idMap = null;
	
	public ExplainUtil(ItemManager item, DictionaryManager d,
			Map<String, Index> map) {
		itemManager = item;
		dictionaryManager = d;
		idMap = map;
	}
	
	public String getItemStr(String id) {
		String result = "";
		Long ID = Long.parseLong(id.substring(1));
		if (id.startsWith("P")) {
			Dictionary lib = PatientUtil.getInstance().getInfo(ID, dictionaryManager);
			result = lib.getValue();
		} else {
			Item item = itemManager.get(ID);
			String testName = idMap.get(item.getIndexId()).getName();
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
	
	
	public double getRank(Rule rule, Result re) {
		double importance = 0;
		for (Item item : rule.getItems()) {
			String impo = idMap.get(item.getIndexId()).getImportance();
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
	
	public StringBuilder getItem(JSONObject root, StringBuilder sb) {
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
}
