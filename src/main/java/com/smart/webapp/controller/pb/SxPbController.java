package com.smart.webapp.controller.pb;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.smart.model.pb.SxArrange;
import com.smart.service.SxArrangeManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/pb/sxpb*")
public class SxPbController {

	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String text = request.getParameter("text");
		int yearfrom = Integer.parseInt(from.split("-")[0]);
		int yearto = Integer.parseInt(to.split("-")[0]);
		if(text.isEmpty())
			return true;
		List<SxArrange> sList = new ArrayList<SxArrange>();//需要保存或更新的排班信息
		Map<String, SxArrange> sxMap = new HashMap<String,SxArrange>();
		
		int maxWeek = getMaxWeek(from, to);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(from.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
		
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(yearfrom, startweek, maxWeek);
		System.out.println(sxArranges.size());
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				System.out.println(a.getWorker()+":"+a.getMonth().split("-")[0]+":"+a.getWeek());
				sxMap.put(a.getWorker()+":"+a.getMonth().split("-")[0]+":"+a.getWeek(), a);//姓名：年：周
			}
		}
		int year = yearfrom;
		String[] list = text.split(",");
		for(int i=0;i<list.length;i++){
			String arr=list[i];
			String[] s = arr.split(":");
			if(Integer.parseInt(s[2])==1&& i!=0){
				year = yearto;
			}
			
			String nameweek = s[0]+":"+year+":"+s[2];
			System.out.println(nameweek);
			SxArrange sxArrange = null;
			
			if(!sxMap.isEmpty() && sxMap.containsKey(nameweek)){
				System.out.println("+++++++++++++"+nameweek);
				sxArrange = sxMap.get(nameweek);
				if(sxArrange.getSection().equals(s.length<4?"":s[3]))
					
					continue;
			}else if(s.length>=4){
				sxArrange = new SxArrange();
			}else{
				continue;
			}
			sxArrange.setWorker(s[0]);
			if(s[1].equals("12") && s[2].equals("1")){
				sxArrange.setMonth((year+1)+"-01"+";"+year+"-"+s[1]);
			}else
				sxArrange.setMonth(year+"-"+s[1]);
			sxArrange.setWeek(s[2]);
			if(s.length==3){
				sxArrange.setSection("");
			}else{
				sxArrange.setSection(s[3]);
			}
			sList.add(sxArrange);
			
		}
		System.out.println("保存或更新size: "+sList.size());
		sxArrangeManager.saveAll(sList);
		
		
		return true;
	}
	
	@RequestMapping(value = "/hisdata*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getHisdata(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
	
		if(id.isEmpty())
			return null;
		
		List<SxArrange> sxArranges = sxArrangeManager.getByName(id);
		
		
		int size = sxArranges.size();
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		if(size-1>end)
			sxArranges = sxArranges.subList(start, end);
		else if(size>start)
			sxArranges = sxArranges.subList(start, size);
		else {
			return null;
		}
		
		List<Map<String, Object>> datarows = new ArrayList<Map<String, Object>>();
		for(SxArrange a : sxArranges){
			Map<String, Object> datarow = new HashMap<String, Object>();
			if(!a.getSection().isEmpty()){
				datarow.put("id", a.getId());
				datarow.put("monthweek", a.getMonth());
				datarow.put("week", a.getWeek());
				datarow.put("section", a.getSection());
				datarows.add(datarow);
			}
		}
		
		
		
		dataResponse.setRows(datarows);
		dataResponse.setRecords(size);
		
		return dataResponse;
	}
	
	
	private int getMaxWeek(String  from,String to){
		int[] fromdate = {Integer.parseInt(from.split("-")[0]),Integer.parseInt(from.split("-")[1])}; 
		int[] todate = {Integer.parseInt(to.split("-")[0]),Integer.parseInt(to.split("-")[1])}; 
		int startweek=0;
		int toweek = 0;
		int totleweek=0;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(from.split("-")[1])-1);
			cal.set(Calendar.DATE, 1);
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			startweek = cal.get(Calendar.WEEK_OF_YEAR);
			
			cal.set(Calendar.YEAR, Integer.parseInt(to.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(to.split("-")[1])-1);
			int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DATE, day);
			toweek = cal.get(Calendar.WEEK_OF_YEAR);
		if(fromdate[0]==todate[0]){
			totleweek = toweek - startweek+1;
		} else {
			cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
			int weeks = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
			totleweek  = weeks - startweek + 1 + toweek;
		}
		return totleweek;
	}
	
	@Autowired
	private SxArrangeManager sxArrangeManager;
	
}
