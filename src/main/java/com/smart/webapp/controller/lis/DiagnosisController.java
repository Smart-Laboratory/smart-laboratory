package com.smart.webapp.controller.lis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.Diagnosis;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.service.lis.DiagnosisManager;
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
	public boolean bcedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			sh.setDiagnosisName(diagnosisName);
			diagnosisManager.save(sh);
		} else if (oper.equals("edit")) {
			sh = diagnosisManager.get(Long.parseLong(id));
			sh.setDescriptionId(Long.parseLong(desId));
			sh.setDiagnosisName(diagnosisName);
			diagnosisManager.save(sh);
		} 
		return true;
	}
	
	
	
	
	
	@Autowired
	private DiagnosisManager diagnosisManager;
}
