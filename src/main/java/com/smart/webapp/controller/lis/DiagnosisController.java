package com.smart.webapp.controller.lis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.drools.compiler.rule.builder.dialect.java.parser.JavaParser.parExpression_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.Diagnosis;
import com.smart.model.lis.Sample;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.model.rule.Result;
import com.smart.model.rule.Rule;
import com.smart.service.lis.DiagnosisManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.rule.DesBagManager;
import com.smart.service.rule.DescriptionManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/diagnosis*")
public class DiagnosisController {

	@RequestMapping(value = "/getInfo*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
	
		if(id.isEmpty())
			return null;
		
		List<Diagnosis> dList = diagnosisManager.getByDid(id);
		
		
		int size = dList.size();
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		
		List<Map<String, Object>> datarows = new ArrayList<Map<String, Object>>();
		for(Diagnosis a : dList){
			Map<String, Object> datarow = new HashMap<String, Object>();
				datarow.put("id", a.getId());
				datarow.put("diagnosisName", a.getDiagnosisName());
				datarows.add(datarow);
		}
		
		dataResponse.setRows(datarows);
		dataResponse.setRecords(size);
		
		return dataResponse;
	}
	
	@RequestMapping(value = "/edit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		
		if(oper.equals("del")) {
			diagnosisManager.remove(Long.parseLong(id));
			return true;
		}
		
		String diagnosisName = request.getParameter("diagnosisName");
		String desId = request.getParameter("desId");
		
		Diagnosis sh = new Diagnosis();
		if(oper.equals("add")) {
			sh.setDescriptionId(Long.parseLong(desId));
			String desName = desBagManager.get(Long.parseLong(desId)).getName();
			sh.setDiagnosisName(diagnosisName);
			sh.setKnowledgeName(desName);
			diagnosisManager.save(sh);
		} else if (oper.equals("edit")) {
			sh = diagnosisManager.get(Long.parseLong(id));
			sh.setDescriptionId(Long.parseLong(desId));
			sh.setDiagnosisName(diagnosisName);
			diagnosisManager.save(sh);
		} 
		return true;
	}
	
	@RequestMapping(value = "/getDes*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String diagnosisName = request.getParameter("diagnosis");
		String id = request.getParameter("id");
		if(diagnosisName==null && id==null)
			return null;
		long bId=0;
		
		Map<String, Object> map = new HashMap<String,Object>();
		List<String> results = new ArrayList<String>();
		String sampleNo = request.getParameter("sampleNo");
		if(sampleNo!=null && !sampleNo.isEmpty()){
			Sample sample = sampleManager.getBySampleNo(sampleNo);
			String ruleIds = sample.getRuleIds();
			if(ruleIds!=null && !ruleIds.isEmpty()){
				List<Rule> rules = ruleManager.getRuleList(ruleIds);
				for(Rule rule : rules){
					if(rule.getType() == 8){
						for(Result result:rule.getResults()){
							results.add(result.getContent());
						}
					}
				}
			}
			map.put("sample", sample);
		}
		map.put("guides", results);
		
		Diagnosis diagnosis = new Diagnosis();
		if(diagnosisName!=null){
			diagnosis = diagnosisManager.getByDiagnosisName(diagnosisName);
			if(diagnosis==null)
				return map;
			bId=diagnosis.getDescriptionId();
		}else if(id!=null){
			bId = Long.parseLong(id);
			diagnosis = diagnosisManager.get(bId);
			
		}
		
		map.put("disease", diagnosis.getKnowledgeName());
		List<Description> dList = descriptionManager.getDescriptionsByBagID(bId);
		
		map.put("dlist", dList);
		
		
		
		
		
		return map;
	}
	
	
	@Autowired
	private DiagnosisManager diagnosisManager;
	@Autowired
	private DesBagManager desBagManager;
	@Autowired
	private DescriptionManager descriptionManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private RuleManager ruleManager;
}
