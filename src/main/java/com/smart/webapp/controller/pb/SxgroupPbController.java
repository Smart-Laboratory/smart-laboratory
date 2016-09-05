package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;
import com.smart.model.user.User;
import com.smart.service.ArrangeManager;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/pb/sxgroupPb*")
public class SxgroupPbController {

	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RMIService rmiService;
	
	@Autowired
	private SectionManager sectionManager;
	
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat md = new SimpleDateFormat("MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");

	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String yearAndMonth = request.getParameter("date");
			
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; 
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		
		User user = userManager.getUserByUsername(request.getRemoteUser());
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
		String department = user.getPbsection();
		Map<String, String> depart = new HashMap<String, String>();
		String section = request.getParameter("section");
		if(section==null && user.getLastLab() != null) {
			section = user.getLastLab();
		}
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, sectionutil.getLabValue(s));
				if(section==null || section.isEmpty())
					section = s;
			}
		}
		if(section == null || section.isEmpty())
			return new ModelAndView();
		
		if(yearAndMonth != null && yearAndMonth !=""){
			calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth.substring(5,7))-1);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH)+1;
		}
		String tomonth = year + "-" + (month<10 ? "0" + month : month);
		
		List<WInfo> wiList = new ArrayList<WInfo>();
		Map<WInfo, String> sxList = getSxWinfoList(section,tomonth);//MAP<,{可以排班的日期，[1;2;3;...]}>
		Map<String, Boolean> sxMap = new HashMap<String,Boolean>();
		if(sxList!=null){
//			System.out.println("size="+sxList.size());
			wiList.addAll(sxList.keySet());
			for(Map.Entry<WInfo, String> entry : sxList.entrySet()){
				if(entry.getValue()==null || entry.getValue().isEmpty())
					continue;
				if(entry.getKey().getIsActive()==0)
					continue;
//				System.out.println(entry.getKey().getName()+entry.getValue());
				for(String s : entry.getValue().split(";")){
					if(StringUtils.isNumeric(s)){
						sxMap.put(entry.getKey().getName()+"-"+s, true);
					}
				}
			}
		}
		
		//获取科室排班选项表
		Map<String, String> wshifts = new LinkedHashMap<String,String>();
		List<Shift> ss = shiftManager.getShiftBySection(section);
		for(Shift shift : ss){
			wshifts.put(shift.getAb(), shift.getAb());
		}
		//备注
		Arrange bzArrange = arrangeManager.getByUser(section, tomonth);
		request.setAttribute("wshifts", wshifts);
		request.setAttribute("departList", depart);
		request.setAttribute("month", tomonth);
		request.setAttribute("section", section);
		if(bzArrange!=null && bzArrange.getShift()!=null)
			request.setAttribute("bz", bzArrange.getShift());
		else
			request.setAttribute("bz", "");
		if(wiList==null || wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0);
		}
		
		String wiNames = "";
		int i=1;
		Map<Integer, WInfo> map = new HashMap<Integer, WInfo>();
		Map<String, Arrange> arrMap = new HashMap<String, Arrange>();
		for(int m=0;m<wiList.size();m++){
			for(int n=m;n<wiList.size();n++){
				if(wiList.get(n).getOrd2()<wiList.get(m).getOrd2()){
					WInfo temp = new WInfo();
					temp = wiList.get(m);
					wiList.set(m, wiList.get(n));
					wiList.set(n, temp);
				}
			}
		}
		for(WInfo wi : wiList) {
			if(wi.getIsActive()==0)
				continue;
			map.put(i, wi);
			wiNames = wiNames + "'" + wi.getName() + "',"; 
			i++;
		}
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[][] shifts = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2][i];
		int state = 0;
		List<Arrange> arrList = arrangeManager.getArrangerd(wiNames.substring(0, wiNames.length()-1), tomonth,state);
		for(Arrange arr : arrList) {
				arrMap.put(arr.getKey2(), arr);
		}
		int j = 1;
        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
            try {
                Date date = sdf1.parse(tomonth + "-" + j);
                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
                	shifts[j][0] = "<th style='background:#7CFC00' id='day" + j + "'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
                } else {
                	shifts[j][0] = "<th style='background:#7FFFD4' id='day" + j + "'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
                }
            } catch (Exception e) {
            	e.printStackTrace();	
            }
        }
        shifts[0][0] = "<th style='background:#7FFFD4' id='nmshow'>" + (month<10 ? "0" + month : month) + "</th>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<th id='nmshow' name='nm"+map.get(m).getName()+"' style='background:#7FFFD4'>" + map.get(m).getName() + "</th>";
        }
        
        for(int k=1; k<i; k++) {
        	String name = map.get(k).getName();
        	
        	for(int l=1; l<j; l++) {
        		String background = "";
        		Date date = sdf1.parse(tomonth + "-" + l);
        		if(sdf2.format(date).contains("六") || sdf2.format(date).contains("日")){
        			background = "style='background:#7CFC00'";
        		}
        		if (arrMap.get(name + "-" + l) == null || arrMap.get(name + "-" + l).getShift()==null) {
        			String td = "";
        			td += "<td class='day' name='td" + l + "' id='" + name + "-" + l + "' "+background+">";
        			td += "</td>";
        			shifts[l][k] = td;
        		} else if(arrMap.get(name + "-" + l).getShift().contains("公休")){
        			shifts[l][k] = "<td  class='day gx' name='td" + l + "' id='" + name + "-" + l + "'  style='background:#FDFF7F;' "+background+">"+arrMap.get(name + "-" + l).getShift().replace("公休;", "")+"</td>";
        		} else{
        			shifts[l][k] = "<td "+background+" class='day' name='td" + l + "' id='" + name + "-" + l + "' >" + arrMap.get(name + "-" + l).getShift().replace(";", ";<br>") + "</td>";
        		}
        		if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getState()<5){
        			shifts[l][k] = shifts[l][k].replace("<td", "<td style='background:#63B8FF'");
        		}
        		if(sxMap!=null && sxMap.containsKey(name + "-" + l) && !section.equals(Constants.LaboratoryCode))
        			shifts[l][k] = shifts[l][k].replace("class='", "class='sx ");
//        			shifts[l][k] = shifts[l][k].replace("class='", "class='sx ").replace("</td>", "<span class='glyphicon glyphicon-ok'></span> </td>");
            }
        }
        
        for(int l = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1 ;l>0;l-- ){
        	for(int k = i-1;k>=0;k-- ){
        		shifts[l][k] = shifts[l-1][k];
        	}
        }
        
        shifts[0][0] = "<th style='background:#7FFFD4'>序号</th>";
        for(int m=1;m<i;m++) {
        	shifts[0][m] = "<th  style='background:#7FFFD4'>" + m + "</th>";
        }
        
        String arrDate = "";
		for(int k=0;k<i;k++){
			if(k==0)
				arrDate += "<thead class='fixedHeader'><tr>";
			else
				arrDate += "<tr>";
			for(int g=0; g<maxDay+2;g++){
				arrDate += shifts[g][k];
			}
			if(k==0)
				arrDate += "</tr></thead><tbody class='scrollContent'>";
			else
				arrDate += "</tr>";
		}
		arrDate += "</tbody>";
		ModelAndView v = new ModelAndView();
		v.addObject("pbdate", arrDate);
		
		return v;
	}
		
	private Map<WInfo, String> getSxWinfoList(String section,String tomonth){
		String sectionCode = section;
		section = SectionUtil.getInstance(rmiService, sectionManager).getLabValue(section);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(tomonth.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(tomonth.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
		int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(Integer.parseInt(tomonth.split("-")[0]), startweek, maxWeek);
//		System.out.println(sxArranges.size());
		
		Map<WInfo, String> sxMap = new HashMap<WInfo,String>();
		List<Shift> pbsection = shiftManager.getSx();
		Map<String, String> sectionMap = new HashMap<String,String>();
		for(Shift shift:pbsection){
			sectionMap.put(shift.getAb(), shift.getName());
		}
		
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				if(a.getWorker().equals("9119"))
					System.out.println(a.getWeek());
				if(a.getSection()==null || a.getSection().isEmpty()){
					continue;
				}
				if(sectionCode.equals(Constants.LaboratoryCode) || (sectionMap.get(a.getSection())!=null && sectionMap.get(a.getSection()).equals(section))){
					WInfo wInfo = wInfoManager.getByWorkId(a.getWorker());
					if(wInfo==null)
						continue;
					String days = getweekDays(tomonth, Integer.parseInt(a.getWeek()));
					if(sxMap.get(wInfo)!=null){
//						System.out.println(sxMap.get(wInfo));
						sxMap.put(wInfo,sxMap.get(wInfo)+days);
					}else{
						sxMap.put(wInfo,days);
					}
				}
			}
		}
		
		if(sxMap.size()>0)
			return sxMap;
		return null;
		
	}
	
	private String getweekDays(String month, int week){
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, Integer.parseInt(month.split("-")[0]));
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		Date date = c.getTime();
        c.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        c.add(GregorianCalendar.DATE, 7*(week-1));
        String[] startDate = md.format(c.getTime()).split("-");
		c.add(GregorianCalendar.DATE, 6);
		String[] endDate = md.format(c.getTime()).split("-");
		
		int startDay = 1;
		if(startDate[0].equals(month.split("-")[1])){
			startDay =Integer.parseInt(startDate[1]);
		}
		c.set(Calendar.MONTH, Integer.parseInt(month.split("-")[1])-1 );
		int endDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(endDate[0].equals(month.split("-")[1])){
			endDay = Integer.parseInt(endDate[1]);
		}
		String days = "";
		for(int i=startDay; i<=endDay; i++)
			days += i +";";
		return days;
	}
	
	@Autowired
	private SxArrangeManager sxArrangeManager;
}
