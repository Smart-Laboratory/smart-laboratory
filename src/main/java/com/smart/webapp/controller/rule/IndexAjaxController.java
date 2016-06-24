package com.smart.webapp.controller.rule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.service.rule.IndexManager;
import com.smart.service.DictionaryManager;
import com.smart.model.rule.Index;
import com.smart.model.rule.Result;
import com.smart.service.rule.ResultManager;
import com.smart.model.Dictionary;

import com.smart.webapp.util.IndexMapUtil;

@Controller
@RequestMapping("/ajax")
public class IndexAjaxController {

	@Autowired
	private IndexManager indexManager = null;
	@Autowired
	private ResultManager resultManager = null;
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
	/**
	 *  给规则添加知识点时，调用的url
	 * @param request
	 * @param response
	 * @return 指标(I)、疾病和病人信息(P)的json格式的数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIndex*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		
		List<Index> indexs = indexManager.getIndexs(name);
		List<Result> results = resultManager.getResults(name);
		List<Dictionary> pInfo = dictionaryManager.getPatientInfo(name);
		boolean needMap = true;
		JSONArray array = new JSONArray();
		if (name.length() == 4) {
			Index i = indexManager.getIndex(name);
			if (i != null) {
				indexs.add(0, i);
				needMap = false;
			}
		}
		
		// 添加病人信息的json数据
		if (pInfo != null) {
			for (Dictionary p : pInfo) {
				JSONObject o = new JSONObject();
				o.put("id", "P" + p.getId().toString());
				o.put("name", p.getValue());			
				o.put("category", "P");
				array.put(o);
			}
		}
		// 添加指标的json数据
		if (indexs != null) {
			// 获取样本来源映射表
//			Map<String, String> map = SampleUtil.getInstance().getSampleList(indexManager);
			IndexMapUtil util = IndexMapUtil.getInstance();
			
			for (Index index : indexs) {
				if (needMap && util.isNeedMap(index.getIndexId())) {
					continue;
				}
				
				JSONObject o = new JSONObject();
				//String unit = index.getUnit();
				String sample = index.getSampleFrom();
				
				/*if (StringUtils.isEmpty(unit)) {
					unit = "";
				}*/
//				if (map.containsKey(sample)) {
//					sample = map.get(sample);
//				}
				
				o.put("id", "I" + index.getId().toString());
				o.put("indexId", index.getIndexId());
				o.put("sample", sample);
				//o.put("unit", unit);
				o.put("name", index.getName());
				
				if (StringUtils.isEmpty(index.getType())) {
					o.put("type", "S");
				} else {
					o.put("type", index.getType());
					if ("E".equals(index.getType())) {
						o.put("data", index.getEnumData());
					}
				}
				o.put("category", "I");
				array.put(o);
			}
		}
		
		// 添加结果的json数据
		if (results != null) {
			for (Result result : results) {
				JSONObject o = new JSONObject();
				o.put("id", "R" + result.getId().toString());
				o.put("name", result.getContent());
				o.put("category", "R");
				array.put(o);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		
		return null;
	}
}
