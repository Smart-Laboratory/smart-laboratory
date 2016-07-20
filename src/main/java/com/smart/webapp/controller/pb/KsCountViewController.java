package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import com.smart.model.pb.WInfo;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.WInfoManager;

@Controller
@RequestMapping("/pb/kscount")
public class KsCountViewController {

	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat md = new SimpleDateFormat("MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	private Map<String, String> students = new HashMap<String,String>();
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView kscountRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String week = request.getParameter("week");
		if(from==null){
			from = ym.format(new Date());
		}
		if(to==null)
			to = ym.format(new Date());
		
		if(students==null || students.size()==0){
			initMap();
		}
		
		List<Shift> pbsection = shiftManager.getSx();
		
		int maxWeek = getMaxWeek(from, to);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(from.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
		
        if(week!=null && !week.isEmpty()){
        	startweek = Integer.parseInt(week);
        	maxWeek=1;
        }
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(from.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		Date date = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(startweek-1));
        
		
        int year = Integer.parseInt(from.split("-")[0]);
		String[][] shifts = new String[pbsection.size()+1][maxWeek+1];
		shifts[0][0] = "<th style='width:80px;'>"+year+"</th>";
		for(int i=1;i<=maxWeek;i++){
			int dweek = startweek+i-1;
			if(dweek>yearMaxWeek)
				dweek-=yearMaxWeek;
			if(i>1)
				c.add(GregorianCalendar.DATE, 1);
			String startDate = md.format(c.getTime());
			c.add(GregorianCalendar.DATE, 6);
			String endDate = md.format(c.getTime());
			shifts[0][i]="<th name='"+startDate.split("-")[0]+"' style='width:80px;'>第 "+dweek+" 周<br>("+startDate+"-"+endDate+")</th>" ;
			
		}
		
		Map<String, String> sxMap = new HashMap<String,String>();
		
		
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(year, startweek, maxWeek);
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				if(sxMap.get(a.getSection()+":"+a.getWeek())!=null){
					sxMap.put(a.getSection()+":"+a.getWeek(), sxMap.get(a.getSection()+":"+a.getWeek())+";<br>" + students.get(a.getWorker()));
				}else{
					sxMap.put(a.getSection()+":"+a.getWeek(), students.get(a.getWorker()));
				}
				
			}
		}
		
		for(int j=0;j<pbsection.size();j++){
			shifts[j+1][0]= "<td>"+pbsection.get(j).getName()+"</a></td>";
			for(int i=1;i<maxWeek+1;i++){
				int dweek = startweek+i-1;
				if(dweek>yearMaxWeek)
					dweek-=yearMaxWeek;
				if(sxMap.containsKey(pbsection.get(j).getAb()+":"+dweek) && sxMap.get(pbsection.get(j).getAb()+":"+dweek)!=null){
					shifts[j+1][i] = "<td name='td' id='"+dweek+"'>"+sxMap.get(pbsection.get(j).getAb()+":"+dweek)+"</td>";
				}else{
					shifts[j+1][i] = "<td></td>";
				}
				
			}
		}
		
		String arrDate = "";
		for(int i=0;i<maxWeek+1;i++){
			
				arrDate += "<tr>";
			for(int j=0; j<pbsection.size()+1;j++){
				arrDate += shifts[j][i];
			}
			
				arrDate += "</tr>";
		}
		
		request.setAttribute("year", year);
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		//当月包含的科室
		String weeks = getWeek(from);
		request.setAttribute("weeks", weeks);
		
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
	
	private void initMap(){
		List<WInfo> wInfos = wInfoManager.getAll();
		for(WInfo wInfo : wInfos){
			students.put(wInfo.getWorkid(), wInfo.getName());
		}
	}
	
	public String  getWeek(String date){
		if(date==null || date.isEmpty())
			return null;
		int maxWeek = getMaxWeek(date, date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
//		System.out.println(ymd.format(cal.getTime()));
		Date datetime = cal.getTime();
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
//		System.out.println(ymd.format(c.getTime()));
		datetime = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(startweek-1));
        
//        System.out.println(ymd.format(c.getTime()));
        datetime = c.getTime();
        int year = Integer.parseInt(date.split("-")[0]);
//        System.out.println(wInfos.size()+":"+maxWeek);
        
        String html = "";
		int yeartemp = year;
		for(int i=1;i<=maxWeek;i++){
			int dweek = startweek+i-1;
			if(dweek>yearMaxWeek){
				dweek-=yearMaxWeek;
				yeartemp+=1;
			}
			if(i>1)
				c.add(GregorianCalendar.DATE, 1);
			String startDate = md.format(c.getTime());
			c.add(GregorianCalendar.DATE, 6);
			String endDate = md.format(c.getTime());
			
			html+="<input type='button' class='btn btn-info' style='margin-left:10px;' name='"+dweek+"' value='"+startDate+"||"+endDate+"' onclick='getdata(this)'/>";
		}
		
		
		
		return html;
		
	}
	
	
	@Autowired
	private ShiftManager shiftManager;
	@Autowired
	private SxArrangeManager sxArrangeManager;
	@Autowired
	private WInfoManager wInfoManager;
}
