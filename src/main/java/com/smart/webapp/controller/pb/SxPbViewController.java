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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.WInfoManager;

@Controller
@RequestMapping("/pb/sxpb*")
public class SxPbViewController {

	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<WInfo> wInfos = wInfoManager.getBySection("", "2");
		String month = request.getParameter("month");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int m = cal.get(Calendar.MONDAY)+2;
		if(month==null || month.isEmpty())
			month = cal.get(Calendar.YEAR) +"-" + (m<10 ? "0"+m : m);
		cal.set(Calendar.YEAR, Integer.parseInt(month.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(month.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
		System.out.println(ymd.format(cal.getTime()));
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(month.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		System.out.println(ymd.format(c.getTime()));
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DAY_OF_MONTH, 7*(startweek-1));
        
        System.out.println(ymd.format(c.getTime()));
		
		String[][] shifts = new String[wInfos.size()+1][maxWeek+1];
		shifts[0][0] = "<th>"+month+"</th>";
		for(int i=1;i<=maxWeek;i++){
			int dweek = startweek+i-1;
			if(i>1)
				c.add(GregorianCalendar.DATE, 1);
			String startDate = ymd.format(c.getTime());
			c.add(GregorianCalendar.DATE, 6);
			String endDate = ymd.format(c.getTime());
			shifts[0][i]="<th>第 "+dweek+" 周("+startDate+"-"+endDate+")</th>" ;
			
		}
		
		Map<String, SxArrange> sxMap = new HashMap<String,SxArrange>();
		
		
		List<SxArrange> sxArranges = sxArrangeManager.getByMonth(month);
		System.out.println(sxArranges.size());
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				sxMap.put(a.getWorker()+":"+a.getWeek(), a);
			}
		}
		
		for(int j=0;j<wInfos.size();j++){
			shifts[j+1][0]= "<td>"+wInfos.get(j).getName()+"</td>";
			for(int i=1;i<maxWeek+1;i++){
				if(sxMap.containsKey(wInfos.get(j).getName()+":"+(startweek+i-1)) && sxMap.get(wInfos.get(j).getName()+":"+(startweek+i-1)).getSection()!=null){
					shifts[j+1][i] = "<td name='td' id='"+wInfos.get(j).getName()+"-"+(startweek+i-1)+"'>"+sxMap.get(wInfos.get(j).getName()+":"+(startweek+i-1)).getSection()+"</td>";
				}else{
					shifts[j+1][i] = "<td name='td' id='"+wInfos.get(j).getName()+"-"+(startweek+i-1)+"'></td>";
				}
				
			}
			
		}
		
		String arrDate = "";
		for(int j=0; j<wInfos.size()+1;j++){
			arrDate += "<tr>";
			for(int i=0;i<maxWeek+1;i++){
				arrDate += shifts[j][i];
			}
			arrDate += "</tr>";
		}
		
		//班次选择
		Map<String, String> wshifts = new LinkedHashMap<String,String>();
		List<Shift> ss = shiftManager.getSx();
		for(Shift shift : ss){
			wshifts.put(shift.getAb(), shift.getName());
		}
		
		request.setAttribute("wshifts", wshifts);
		request.setAttribute("month", month);
		
		
		ModelAndView view = new ModelAndView();
		view.addObject("pbdate", arrDate);
		
		
		
		
		
        return view;
    }
	
	@Autowired
	private WInfoManager wInfoManager;
	@Autowired
	private ShiftManager shiftManager;
	@Autowired
	private SxArrangeManager sxArrangeManager;
}
