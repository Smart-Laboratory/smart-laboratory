package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.SxSchool;
import com.smart.model.pb.WInfo;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.SxSchoolManager;
import com.smart.service.WInfoManager;

@Controller
@RequestMapping("/pb/sxpb*")
public class SxPbViewController {

	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat md = new SimpleDateFormat("MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	private int pageNum = 20;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String week = request.getParameter("week");
		if(from==null||to==null){
			from = ym.format(new Date());
			to = ym.format(new Date());
		}
		if(Integer.parseInt(from.split("-")[0])>Integer.parseInt(to.split("-")[0]) || (Integer.parseInt(from.split("-")[0])==Integer.parseInt(to.split("-")[0])  && Integer.parseInt(from.split("-")[1])>Integer.parseInt(to.split("-")[1])))
			return new ModelAndView();
		
		List<WInfo> wInfos = wInfoManager.getBySection("", "2");
//		String year = request.getParameter("year");
		int size = wInfos.size();
		
		//分页
		int pages = 0;
		if(size >= pageNum)
			pages = size/pageNum + (size%pageNum==0?0:1) ;
		else if(size >0)
			pages = 1;
		String pg = request.getParameter("page");
		int page = 1;
		if(pg!=null && !pg.isEmpty())
			page=Integer.parseInt(pg);
		if(page<pages)
			wInfos = wInfos.subList((page-1)*pageNum, page*pageNum);
		else if(page == pages)
			wInfos = wInfos.subList((page-1)*pageNum, size);
		
//		System.out.println(page+"--"+pages+"--"+wInfos.size());
		
		//计算日期
		
		
		int maxWeek = getMaxWeek(from, to);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(from.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
//		System.out.println(ymd.format(cal.getTime()));
		Date date = cal.getTime();
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
//		System.out.println(ymd.format(c.getTime()));
		date = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(startweek-1));
        
//        System.out.println(ymd.format(c.getTime()));
		date = c.getTime();
        int year = Integer.parseInt(from.split("-")[0]);
//        System.out.println(wInfos.size()+":"+maxWeek);
		String[][] shifts = new String[wInfos.size()+1][maxWeek+1];
		shifts[0][0] = "<th style='width:180px;'>"+year+"</th>";
		int yeartemp = year;
		for(int i=1;i<=maxWeek;i++){
			int dweek = startweek+i-1;
			if(dweek>yearMaxWeek){
				dweek-=yearMaxWeek;
				yeartemp=year+1;
			}
			if(i>1)
				c.add(GregorianCalendar.DATE, 1);
			String startDate = ymd.format(c.getTime());
			c.add(GregorianCalendar.DATE, 6);
			String endDate = ymd.format(c.getTime());
			shifts[0][i]="<th name='"+startDate.split("-")[0]+startDate.split("-")[1]+"week' style='width:180px;'> <a onclick=\"sectionInfo("+dweek+","+yeartemp+")\">第 "+dweek+" 周<br>("+startDate+"-"+endDate+")</a></th>" ;
			
		}
		
		Map<String, SxArrange> sxMap = new HashMap<String,SxArrange>();
		
		
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(year, startweek, maxWeek);
//		System.out.println(sxArranges.size());
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
//				System.out.println(a.getWorker()+":"+a.getWeek()+":"+a.getSection()+a.getMonth());
				sxMap.put(a.getWorker()+":"+a.getMonth().substring(0, 4)+a.getWeek(), a);
			}
		}
		
		//实习生学校map
		Map<Long, String> sxschoolMap = new HashMap<Long,String>();
		List<SxSchool> schools = sxSchoolManager.getAll();
		for(SxSchool s : schools){
			sxschoolMap.put(s.getId(), s.getNamesx());
		}
		
		for(int j=0;j<wInfos.size();j++){
			
			shifts[j+1][0]= "<td><a onclick=\"stuInfo("+wInfos.get(j).getWorkid()+",'"+wInfos.get(j).getName()+"')\">"+wInfos.get(j).getName()+"("+(wInfos.get(j).getSchool()==null?"":sxschoolMap.get(Long.parseLong(wInfos.get(j).getSchool())))+")"+"</a></td>";
			yeartemp = year;
			
			
			for(int i=1;i<maxWeek+1;i++){
				int dweek = startweek+i-1;
				if(dweek>yearMaxWeek){
					dweek-=yearMaxWeek;
					yeartemp=year+1;
				}
//				if(wInfos.get(j).getWorkid().equals("9022") && dweek==23){
//					SxArrange a = sxMap.get(wInfos.get(j).getWorkid()+":"+yeartemp+dweek);
//				}
				String month = shifts[0][i].substring(10,16);
				if(sxMap.containsKey(wInfos.get(j).getWorkid()+":"+yeartemp+dweek) && sxMap.get(wInfos.get(j).getWorkid()+":"+yeartemp+dweek).getSection()!=null){
					shifts[j+1][i] = "<td name='td' id='"+wInfos.get(j).getWorkid()+"-"+dweek+"-"+month+"'>"+sxMap.get(wInfos.get(j).getWorkid()+":"+yeartemp+dweek).getSection()+"</td>";
				}else{
					shifts[j+1][i] = "<td name='td' id='"+wInfos.get(j).getWorkid()+"-"+dweek+"-"+month+"'></td>";
				}
				
			}
			
		}
		
		String arrDate = "";
		for(int i=0;i<maxWeek+1;i++){
			if(i==0)
				arrDate += "<thead class='fixedHeader'><tr>";
			else
				arrDate += "<tr>";
			for(int j=0; j<wInfos.size()+1;j++){
				arrDate += shifts[j][i];
			}
			if(i==0)
				arrDate += "</tr></thead><tbody class='scrollContent'>";
			else
				arrDate += "</tr>";
		}
		arrDate += "</tbody>";
		
		//班次选择
		Map<String, String> wshifts = new LinkedHashMap<String,String>();
		List<Shift> ss = shiftManager.getSx();
		for(Shift shift : ss){
			wshifts.put(shift.getAb(), shift.getName());
		}
		if(week!=null && !week.isEmpty())
			request.setAttribute("week", week);
		request.setAttribute("pages", pages);
		request.setAttribute("page", page);
		request.setAttribute("wshifts", wshifts);
		request.setAttribute("year", year);
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		
		
		ModelAndView view = new ModelAndView();
		view.addObject("pbdate", arrDate);
		
        return view;
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
	
	
	@Autowired
	private WInfoManager wInfoManager;
	@Autowired
	private ShiftManager shiftManager;
	@Autowired
	private SxArrangeManager sxArrangeManager;
	@Autowired
	private SxSchoolManager sxSchoolManager;
}
