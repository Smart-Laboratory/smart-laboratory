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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;
import com.smart.model.pb.WorkCount;
import com.smart.service.ArrangeManager;
import com.smart.service.ShiftManager;
import com.smart.service.SxArrangeManager;
import com.smart.service.WInfoManager;
import com.smart.service.WorkCountManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;



@Controller
@RequestMapping("/pb/pb*")
public class PbController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
	@Autowired
	private WorkCountManager workCountManager;
	
	@Autowired
	private SxArrangeManager sxArrangeManager;
	
	private Map<String, Double> shiftTime = new HashMap<String,Double>();
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
	
	
	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		String text = request.getParameter("text");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = request.getParameter("section");
		String bz = request.getParameter("bz");
		String isStudent = request.getParameter("isStu");
		
		Arrange ksArrange = arrangeManager.getByUser(section, month);
//		if(ksArrange!=null && ksArrange.getState()>1){
//			return false;
//		}
		
		Map<String, Map<String, Arrange>> userShifts = new HashMap<String, Map<String, Arrange>>();
		List<WInfo> wInfos = new ArrayList<WInfo>();
		if(isStudent!=null && isStudent.equals("true")){
			wInfos = getWinfoBySection(month,section);
		}else{
			wInfos = wInfoManager.getBySection(section);
		}
		
		for(WInfo wInfo : wInfos){
			
			Map<String, Arrange> dateValue = new HashMap<String,Arrange>(); //map<日期，排班>
			List<Arrange> monthArray = arrangeManager.getMonthArrangeByName(wInfo.getName(), month);
			for(Arrange a: monthArray){
					dateValue.put(a.getDate(), a);
			}
			userShifts.put(wInfo.getName(), dateValue);
		}
		
		System.out.println("数据获取完成");
		
		if(ksArrange == null){
			ksArrange = new Arrange();
			ksArrange.setState(2);
		}
		ksArrange.setDate(month);
		ksArrange.setSection(section);
		ksArrange.setWorker(section);
		ksArrange.setShift(bz);
		
		ksArrange.setOperator(user.getUsername());
		ksArrange.setOperatime(new Date());
		list.add(ksArrange);
		
		if(text != "") {
			for(String str : text.split(",")) {
				String[] arr = str.split(":");
				
				Arrange a = null;
				if(userShifts.containsKey(arr[0])  ){
					Map<String, Arrange> dateValue = userShifts.get(arr[0]);
					if(dateValue.containsKey(arr[1]) ){
						if(!dateValue.get(arr[1]).getShift().equals(arr.length>=3?arr[2]:"")){
							Arrange b = dateValue.get(arr[1]);
							b.setShift(arr.length>=3?arr[2]:"");
							b.setState(2);
							b.setOperator(user.getUsername());
							b.setOperatime(new Date());
							if(!b.getSection().contains(section)){
								b.setSection(b.getSection()+","+section);
							}
							list.add(b);
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
				a.setOperator(user.getUsername());
				a.setOperatime(new Date());
				a.setState(2);
				if(arr.length>=3)
					a.setShift(arr[2]);
				else 
					a.setShift("");
				list.add(a);
			}
		}
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
	}
	
	@RequestMapping(value = "/kssubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean kssubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = "1300000";
		System.out.println("开始");
		
		Arrange ksArrange = arrangeManager.getByUser(section, month);
//		if(ksArrange!=null && ksArrange.getState()>0){
//			return false;
//		}
		
		
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
							Arrange b = dateValue.get(arr[1]);
							b.setShift(arr.length>=3?arr[2]:"");
							b.setState(0);
							b.setOperator(user.getUsername());
							b.setOperatime(new Date());
							if(!b.getSection().contains(section)){
								b.setSection(b.getSection()+","+section);
							}
							list.add(b);
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
				a.setOperator(user.getUsername());
				a.setOperatime(new Date());
				a.setState(0);
				if(arr.length>=3)
					a.setShift(arr[2]);
				else 
					a.setShift("");
				
				list.add(a);
			}
		}
		System.out.println("保存： "+list.size());
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/workCount*")
	@ResponseBody
	public List<WorkCount> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<WorkCount> wList = new ArrayList<WorkCount>();
		if(shiftTime == null || shiftTime.isEmpty())
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
				workCount.setSection(section);
			}else if(!workCount.getSection().contains(section)){
				workCount.setSection(workCount.getSection()+","+section);
			}
			double holiday = 0;
			double worktime = 0;
			double monthOff = 0;
			
			String shifts = "";
			for(Arrange arrange : arranges){
				try {
					Date date = sdf1.parse(arrange.getDate());
					if(!arrange.getShift().trim().isEmpty()){
						workdate.add(arrange.getDate());
						shifts += arrange.getShift();
						if(arrange.getShift().contains("公休")){
							if(arrange.getShift().replace("公休;", "").isEmpty())
								monthOff += 1;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("date trans error!");
				}
				
			}
			
			for(String shift : shifts.split(";")){
				if(shiftTime.containsKey(shift)){
					worktime += shiftTime.get(shift);
				}
				if(shift=="年休"){
					holiday +=1;
				}
				if(shift.contains("休") && !shift.contains("公休")){
					monthOff += 1;
				}				
			}
			//计算积修
			double yjx = 0;
			int j = 1;
			//统计本月公休
			List<String> gxList = arrangeManager.getGXcount(month);
			if(gxList != null && gxList.size()>0){
				yjx += gxList.size();
			}
			
	        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
	            try {
	            	String day = j + "";
	            	if(j<10)
	            		day = "0"+day;
	                Date date = sdf1.parse(month + "-" + day);
	                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
	                	if(gxList != null && gxList.size()>0 && gxList.contains(month + "-" + day)){
	        				
	        			}else{
	        				yjx+=1;
	        			}
	                }
	            } catch (Exception e) {
	            	e.printStackTrace();	
	            }
	        }
	        yjx = yjx - (days - worktime);
	        workCount.setHoliday(holiday);
			workCount.setWorkTime(worktime);
			workCount.setWorker(w.getName());
			workCount.setMonthOff(days-workdate.size()+monthOff);
			workCount.setYjx(yjx);
			workCount.setWorkMonth(month);
			wList.add(workCount);
			
			workCountManager.save(workCount);
	        
	        String defeholiday = w.getDefeHoliday();
	        if(defeholiday == null){
	        	defeholiday = month+":"+yjx+";";
	        }
	        else if(defeholiday.contains(month)){
	        	for(String jx : defeholiday.split(";")){
	        		if(jx.contains(month)){
	        			defeholiday = defeholiday.replace(jx, month+":"+yjx);
	        		}
	        	}
	        }
	        else{
	        	defeholiday += month+":"+yjx+";";
	        }
	        w.setDefeHoliday(defeholiday);
	        
	        //计算年休
			double nx = w.getHolidayNum()-workCountManager.getYearCount(month.substring(0, 4),w.getName());
			w.setHoliday(w.getHolidayNum() - nx);
	        wInfoManager.save(w);
			
		}
		
		return wList;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/getWorkCount*")
	@ResponseBody
	public List<WorkCount> getWorkCount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//统计信息
		String section = request.getParameter("section");
		String month = request.getParameter("month");
        List<WorkCount> wList = new ArrayList<WorkCount>();
        wList = workCountManager.getMonthBySection(section, month);
        
        return wList;
        
	}
	
	public void initMap(){
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
	
	@RequestMapping(method = RequestMethod.POST,value = "/publish*")
	@ResponseBody
	public boolean pbPublish(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String section = request.getParameter("section");
		String month = request.getParameter("month");
		int state = Integer.parseInt(request.getParameter("state"));
		
		List<Arrange> arranges = arrangeManager.getPublish(section, month, state);
		
		List<Arrange> need = new ArrayList<Arrange>();
		
		for(Arrange a: arranges){
			if(a.getState()==state){
				a.setState(5);
				need.add(a);
			}
		}
		
		Arrange a = arrangeManager.getByUser(section, month);
		if(a == null){
			a = new Arrange();
			a.setDate(month);
			a.setSection(section);
			a.setWorker(section);
			a.setOperator(user.getUsername());
			a.setOperatime(new Date());
			a.setState(state);
			a.setType(0);
		}else{
			a.setState(state);
			a.setOperator(user.getUsername());
			a.setOperatime(new Date());
		}
		
		arrangeManager.saveAll(need);
		return true;
		
	}
	
	
	public List<WInfo> getWinfoBySection(String tomonth, String section){
		section = SectionUtil.getInstance(rmiService).getValue(section);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(tomonth.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(tomonth.split("-")[1])-1);
		cal.set(Calendar.DATE, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int startweek = cal.get(Calendar.WEEK_OF_YEAR);
		int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		List<SxArrange> sxArranges = sxArrangeManager.getByWeek(Integer.parseInt(tomonth.split("-")[0]), startweek, maxWeek);
		System.out.println(sxArranges.size());
		
		List<Shift> pbsection = shiftManager.getSx();
		Map<String, String> sectionMap = new HashMap<String,String>();
		for(Shift shift:pbsection){
			sectionMap.put(shift.getAb(), shift.getName());
		}
		
		List<WInfo> wInfos = new ArrayList<WInfo>();
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				if(sectionMap.get(a.getSection()).equals(section)){
					WInfo wInfo = wInfoManager.getByWorkId(a.getWorker());
					
					wInfos.add(wInfo);
				}
			}
		}
		
		return wInfos;
				
	}
	
	@Autowired
	private RMIService rmiService;
}
