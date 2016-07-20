package com.smart.webapp.controller.pb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.SxSchool;
import com.smart.model.pb.WInfo;
import com.smart.model.user.User;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.SxSchoolManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;
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
		if(yearfrom>yearto || (yearfrom == yearto && Integer.parseInt(from.split("-")[1])>Integer.parseInt(to.split("-")[1])))
			return false;
		String username = request.getRemoteUser();
		User user = userManager.getUserByUsername(username);
		
		
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
//		System.out.println(startweek+"||"+maxWeek+"||"+sxArranges.size());
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
//				System.out.println(a.getWorker()+":"+a.getMonth().split("-")[0]+":"+a.getWeek());
				sxMap.put(a.getWorker()+":"+a.getMonth().split("-")[0]+":"+a.getWeek(), a);//姓名：年：周
			}
		}
		int year = 0;
		String[] list = text.split(",");
		for(int i=0;i<list.length;i++){
			String arr=list[i];
			String[] s = arr.split(":");
//			if(Integer.parseInt(s[2])==1&& i!=0){
//				year = yearfrom+1;
//			}
			year = Integer.parseInt(s[1].substring(0, 4));
			if(s[1].substring(4, 6).equals("12") && s[2].equals("1")){
				year = year+1;
			}
			String nameweek = s[0]+":"+year+":"+s[2];
//			System.out.println(nameweek);
			SxArrange sxArrange = null;
			
			if(!sxMap.isEmpty() && sxMap.containsKey(nameweek)){
//				System.out.println("+++++++++++++"+nameweek);
				sxArrange = sxMap.get(nameweek);
				if(sxArrange.getSection()==null && s.length<4)
					continue;
				if(sxArrange.getSection()!=null && sxArrange.getSection().equals(s.length<4?"":s[3]))
					continue;
			}else if(s.length>=4){
				sxArrange = new SxArrange();
			}else{
				continue;
			}
			sxArrange.setWorker(s[0]);
			if(s[1].substring(4, 6).equals("12") && s[2].equals("1")){
				sxArrange.setMonth(year+"-01"+";"+(year-1)+"-"+s[1].substring(4, 6));
			}else
				sxArrange.setMonth(year+"-"+s[1].substring(4, 6));
			sxArrange.setWeek(s[2]);
			if(s.length==3){
				sxArrange.setSection("");
			}else{
				sxArrange.setSection(s[3]);
			}
			sxArrange.setOperator(user.getUsername());
			sxArrange.setOperatetime(new Date());
			sList.add(sxArrange);
			
		}
		for(SxArrange sx : sList){
			System.out.println(sx.getSection()+sx.getWeek()+sx.getWorker()+sx.getMonth());
		}
		System.out.println("保存或更新size: "+sList.size());
		sxArrangeManager.saveAll(sList);
		
		
		return true;
	}
	
	Map<Long, Map<String, String>> schools = new HashMap<Long, Map<String, String>>();
	public void inintSchoolsMap(){
		List<SxSchool> sxSchools = sxSchoolManager.getAll();
		for(SxSchool s : sxSchools){
			String system = s.getSystem();
			if(system==null || system.isEmpty())
				continue;
			Map<String, String> systems = new HashMap<String,String>();
			if(schools.get(s.getId())!=null)
				systems = schools.get(s.getId());
			for(String str : system.split(";")){
				if(str == null)
					continue;
				systems.put(str.split("=")[0], str.split("=")[1]);
			}
			schools.put(s.getId(), systems);
		}
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
		inintSchoolsMap();
		WInfo wInfo = wInfoManager.getByWorkId(id);
		Map<String, String> systems = new HashMap<String,String>();
		if(wInfo.getSchool()!=null)
			systems = schools.get(Long.parseLong(wInfo.getSchool()));
		
		List<SxArrange> sxArranges = sxArrangeManager.getByName(id);
		
		List<Shift> sections = shiftManager.getSx();
		
		Map<String, Integer> stuCount = new HashMap<String, Integer>();
		
		for(Shift shift: sections){
			stuCount.put(shift.getAb(), 0);
		}
		
		for(SxArrange arrange : sxArranges){
			if(stuCount.containsKey(arrange.getSection()))
				stuCount.put(arrange.getSection(), stuCount.get(arrange.getSection())+1);
			else
				System.out.println("不能理解的排班："+arrange.getSection());
		}
		
		int size = sections.size();
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		
		List<Map<String, Object>> datarows = new ArrayList<Map<String, Object>>();
		for(Map.Entry<String, Integer> a : stuCount.entrySet()){
			Map<String, Object> datarow = new HashMap<String, Object>();
				datarow.put("section", a.getKey());
				datarow.put("num", a.getValue());
				if(systems!=null)
					datarow.put("schoolnum", systems.get(a.getKey())==null?0:systems.get(a.getKey()));
				datarows.add(datarow);
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
		if(fromdate[0]==todate[0] && toweek>startweek){
			totleweek = toweek - startweek+1;
		} else {
			cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
			int weeks = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
			totleweek  = weeks - startweek + 1 + toweek;
		}
		return totleweek;
	}
	
	@RequestMapping(value = "/sectionCount*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSectionCount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String week = request.getParameter("week");
		String year = request.getParameter("year");
		if(week == null || week.isEmpty())
			return null;
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(Integer.parseInt(year), Integer.parseInt(week), 1);
		
		List<Shift> sections = shiftManager.getSx();
		Map<String, Long> idMap = new HashMap<String,Long>();
		Map<String, Integer> countMap = new HashMap<String,Integer>();
		for(Shift shift : sections){
			
			countMap.put(shift.getAb(), 0);
			idMap.put(shift.getAb(), shift.getId());
		}
		
		for(SxArrange a : sxArranges){
			if(countMap.containsKey(a.getSection())){
				countMap.put(a.getSection(), countMap.get(a.getSection())+1);
			}else{
				countMap.put(a.getSection(), 1);
			}
		}
		
		DataResponse dataResponse = new DataResponse();
		
		List<Map<String, Object>> datarows = new ArrayList<Map<String, Object>>();
		Map<String, Object> datarow = new HashMap<String, Object>();
		for(Map.Entry<String, Integer> a : countMap.entrySet()){
//			System.out.print(a.getKey()+a.getValue());
			datarow.put(idMap.get(a.getKey())+"", a.getValue());
		}
		datarows.add(datarow);
		
		dataResponse.setRows(datarows);
		dataResponse.setRecords(1);
		
		return dataResponse;
	}
	
	@Autowired
	private SxArrangeManager sxArrangeManager;
	@Autowired
	private ShiftManager shiftManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private WInfoManager wInfoManager;
	@Autowired
	private SxSchoolManager sxSchoolManager;
}
