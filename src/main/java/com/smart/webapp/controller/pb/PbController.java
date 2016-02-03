package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.common.params.CommonParams.EchoParamStyle;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.drools.compiler.lang.dsl.DSLMapParser.mapping_file_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.Shift;
import com.smart.model.pb.WInfo;
import com.smart.model.pb.WorkCount;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.ShiftManager;
import com.smart.service.WInfoManager;
import com.smart.service.WorkCountManager;

import javafx.scene.chart.PieChart.Data;


@Controller
@RequestMapping("/pb/pb*")
public class PbController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
	@Autowired
	private WorkCountManager workCountManager;
	
	private Map<String, Double> shiftTime = null;
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
	
	
	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String text = request.getParameter("text");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = request.getParameter("section");
		
		Map<String, Map<String, Arrange>> userShifts = new HashMap<String, Map<String, Arrange>>();
		
		List<WInfo> wInfos = wInfoManager.getBySection(section);
		
		for(WInfo wInfo : wInfos){
			
			Map<String, Arrange> dateValue = new HashMap<String,Arrange>(); //map<日期，排班>
			List<Arrange> monthArray = arrangeManager.getMonthArrangeByName(wInfo.getName(), month);
			for(Arrange a: monthArray){
				dateValue.put(a.getDate(), a);
			}
			userShifts.put(wInfo.getName(), dateValue);
		}
		
		System.out.println("数据获取完成");
		
		if(text != "") {
			for(String str : text.split(",")) {
				String[] arr = str.split(":");
				
				Arrange a = null;
				if(userShifts.containsKey(arr[0])  ){
					Map<String, Arrange> dateValue = userShifts.get(arr[0]);
					if(dateValue.containsKey(arr[1]) ){
						if(!dateValue.get(arr[1]).getShift().equals(arr.length>=3?arr[2]:"")){
							dateValue.get(arr[1]).setShift(arr.length>=3?arr[2]:"");
							list.add(dateValue.get(arr[1]));
							continue;
						}
						else{
							continue;
						}
						
					}
					else if((arr.length>=3?arr[2]:"")!="" ){
						a  = new Arrange();
					}
					else{
						continue;
					}
				} else {
					a= new Arrange();
				}
				a.setSection(section);
				a.setWorker(arr[0]);
				a.setDate(arr[1]);
				if(arr.length>=3)
					a.setShift(arr[2]);
				else 
					a.setShift("");
				list.add(a);
			}
		}
		System.out.println(list.size());
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
	}
	
	@RequestMapping(value = "/kssubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean kssubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = "1300000";
		System.out.println("开始");
		
		
		Map<String, Map<String, Arrange>> userShifts = new HashMap<String, Map<String, Arrange>>();
		
		List<WInfo> wInfos = wInfoManager.getBySection(section, type);
		for(WInfo wInfo : wInfos){
			
			Map<String, Arrange> dateValue = new HashMap<String,Arrange>(); //map<日期，排班>
			List<Arrange> monthArray = arrangeManager.getMonthArrangeByName(wInfo.getName(), month);
			for(Arrange a: monthArray){
				dateValue.put(a.getDate(), a);
			}
			userShifts.put(wInfo.getName(), dateValue);
		}
		
		System.out.println("数据获取完成");
		
		if(text != "") {
			for(String str : text.split(",")) {
				String[] arr = str.split(":");
				Arrange a = null;
				if(userShifts.containsKey(arr[0])  ){
					Map<String, Arrange> dateValue = userShifts.get(arr[0]);
					if(dateValue.containsKey(arr[1]) ){
						if(!dateValue.get(arr[1]).getShift().equals(arr.length>=3?arr[2]:"")){
							dateValue.get(arr[1]).setShift(arr.length>=3?arr[2]:"");
							list.add(dateValue.get(arr[1]));
							continue;
						}
						else{
							continue;
						}
						
					}
					else if((arr.length>=3?arr[2]:"")!="" ){
						a  = new Arrange();
					}
					else{
						continue;
					}
				} else {
					a= new Arrange();
				}
				a.setSection(section);
				a.setWorker(arr[0]);
				a.setDate(arr[1]);
				if(arr.length>=3)
					a.setShift(arr[2]);
				else 
					a.setShift("");
				if(type.equals("5")) {
					a.setType(1);
				}
				list.add(a);
			}
		}
		System.out.println(list.size());
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/workCount*")
	@ResponseBody
	public List<WorkCount> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<WorkCount> wList = new ArrayList<WorkCount>();
		if(shiftTime == null)
			initMap();
		
		String section = request.getParameter("section");
		String month = request.getParameter("month");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(month.substring(0,4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		
		List<WInfo> wInfos = wInfoManager.getBySection(section, "0");
		
		for(WInfo w : wInfos){
			List<Arrange> arranges = arrangeManager.getMonthArrangeByName(w.getName(), month);
			Set<String> workdate = new HashSet<String>();
			
			WorkCount workCount = workCountManager.getPersonByMonth(w.getName(),month,section);
			if(workCount==null){
				workCount = new WorkCount();
			}
			double holiday = 0;
			double worktime = 0;
			double monthOff = 0;
			
			String shifts = "";
			for(Arrange arrange : arranges){
				if(arrange.getShift()!="")
					workdate.add(arrange.getDate());
				shifts += arrange.getShift();
				if(arrange.getShift().contains("公休") && arrange.getShift().replace("公休", "")=="")
					monthOff += 1;
			}
			
			for(String shift : shifts.split(";")){
				if(shiftTime.containsKey(shift)){
					worktime += shiftTime.get(shift);
				}
				if(shift=="年休"){
					holiday +=1;
				}
				if(shift.contains("休") && shift != "公休"){
					monthOff += 1;
				}				
			}
			
			workCount.setHoliday(holiday);
			workCount.setWorkTime(worktime);
			workCount.setWorker(w.getName());
			workCount.setMonthOff(days-workdate.size()+monthOff);
			workCount.setSection(section);
			workCount.setWorkMonth(month);
			wList.add(workCount);
			
			workCountManager.save(workCount);
			//计算年休
			
			double nx = w.getHolidayNum()-workCountManager.getYearCount(month.substring(0, 4),w.getName());
			w.setHoliday(w.getHolidayNum() - nx);
			
			//计算积修
			double yjx = 0;
			int j = 1;
			//统计本月公休
			List<String> gxList = arrangeManager.getGXcount(month);
			if(gxList != null && gxList.size()>0){
				yjx += gxList.size();
				System.out.println(gxList.size());
			}
			
	        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
	            try {
	                Date date = sdf1.parse(month + "-" + j);
	                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
	                	if(gxList != null && gxList.size()>0 && gxList.contains(month + "-" + j)){
	        				
	        			}else{
	        				yjx+=1;
	        			}
	                }
	            } catch (Exception e) {
	            	e.printStackTrace();	
	            }
	        }
	        yjx = yjx - (days - worktime);
	        
	        
	        String defeholiday = w.getDefeHoliday();
	        if(defeholiday == null){
	        	defeholiday = month+":"+yjx+";";
	        }
	        else if(defeholiday.contains(month)){
	        	for(String jx : defeholiday.split(";")){
	        		if(jx.contains(month)){
	        			defeholiday.replace(jx, month+":"+yjx);
	        		}
	        	}
	        }
	        else{
	        	defeholiday += month+":"+yjx+";";
	        }
	        w.setDefeHoliday(defeholiday);
	        wInfoManager.save(w);
			
		}
		
		return wList;
	}
	
	public void initMap(){
		shiftTime = new HashMap<String,Double>();
		List<Shift> shifts = shiftManager.getAll();
		for(Shift shift : shifts){
			shiftTime.put(shift.getAb(), shift.getDays());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/getholiday*")
	@ResponseBody
	public List<Object> getHoliday(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String section = request.getParameter("section");
		List<WInfo> wiList = wInfoManager.getBySection(section, "0");
		List<Object> objects = new ArrayList<Object>();
		for(WInfo w : wiList){
			JSONObject o = new JSONObject();
			o.put("name", w.getName());
			o.put("holiday", w.getHoliday());
			objects.add(o);
		}
		return objects;
	}
	
	
}
