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
import com.smart.Constants;
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
import com.smart.service.lis.SectionManager;
import com.smart.util.ConvertUtil;
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
	
	@Autowired
	private SectionManager sectionManager;
	
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
	
	
	@RequestMapping(value = "/ajax/submit*", method = RequestMethod.POST)
	@ResponseBody
	public String submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject o = new JSONObject();
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
		
		String isLxsy = IsLxsyOutOfRange(text, wInfos);
		if(isLxsy!=null && !isLxsy.isEmpty()){
			o.put("data", isLxsy);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(o.toString());
			return null;
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
						if((dateValue.get(arr[1]).getShift()==null && arr.length>=3) || (dateValue.get(arr[1]).getShift()!=null && !dateValue.get(arr[1]).getShift().equals(arr.length>=3?arr[2]:""))){
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
				userShifts.get(arr[0]).put(arr[1], a);
			}
		}
//		for(Arrange a:list){
//			System.out.println("-------------------------------------");
//			System.out.println(a.getWorker()+a.getDate());
//		}
		
		
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		o.put("data", "true");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(o.toString());
		return null;
	}
	/**
	 * 判断用户历休使用是否超标
	 * @param str
	 * @return
	 */
	public String IsLxsyOutOfRange(String str, List<WInfo> wList){
		Map<String, Integer> pMap = new HashMap<String ,Integer>();
		for(String s : str.split(",")){
			String[] info = s.split(":");
			int shifts = 0;
			if(info.length<3 || !info[2].equalsIgnoreCase(Constants.defeholidayhis)){
				continue;
			}
			if(pMap.keySet().contains(info[0])){
				shifts = pMap.get(info[0]);
				pMap.put(info[0], ++shifts);
			}else{
				pMap.put(info[0], 1);
			}
		}
		//list转map
		Map<String, WInfo> wMap = new HashMap<String,WInfo>();
		
		for(WInfo w: wList)
			wMap.put(w.getName(), w);
		
		for(String name : pMap.keySet()){
			if(wMap.get(name)==null || wMap.get(name).getDefeHolidayNum()<pMap.get(name)){
				return name+" 使用历休超过限制！";
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/kssubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean kssubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = ""+Constants.LaboratoryCode+"";
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
						Arrange test = dateValue.get(arr[1]);
						if((dateValue.get(arr[1]).getShift()==null && arr.length>=3) || (dateValue.get(arr[1]).getShift()!=null && !dateValue.get(arr[1]).getShift().equals(arr.length>=3?arr[2]:""))){
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
					else if(!(arr.length>=3?arr[2]:"").isEmpty() ){
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
				userShifts.get(arr[0]).put(arr[1], a);
			}
		}
		System.out.println("保存： "+list.size());
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
	}
	
	@RequestMapping(value = "/bpbsubmit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean bpbsubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		String text = request.getParameter("text");
		String month = request.getParameter("date");
		List<Arrange> list = new ArrayList<Arrange>();
		String section = request.getParameter("section");
		String special = request.getParameter("special");
		String selperson = request.getParameter("selperson");
		
		if(selperson!=null && !selperson.isEmpty()){
			Arrange ksArrange = arrangeManager.getByUser(section, month);
			if(ksArrange == null){
				ksArrange = new Arrange();
				ksArrange.setState(2);
			}
			ksArrange.setDate(month);
			ksArrange.setSection(section);
			ksArrange.setWorker(section);
			ksArrange.setShift(selperson);
			
			ksArrange.setOperator(user.getUsername());
			ksArrange.setOperatime(new Date());
			list.add(ksArrange);
		}
		//map<班次，排班>
		Map<String, Map<String, Arrange>> userShifts = new HashMap<String, Map<String, Arrange>>();
		List<Shift> shifts = shiftManager.getShiftBySection(section);
		
		for(Shift shift : shifts){
			Map<String, Arrange> dateValue = new HashMap<String,Arrange>(); //map<日期，排班>
			//获取B超3个月内的排班记录
			List<Arrange> monthArray = arrangeManager.getMonthArrangeByshift(shift.getAb(), month,section);
			for(Arrange a: monthArray){
				dateValue.put(a.getDate(), a);
			}
			userShifts.put(shift.getAb(), dateValue);
		}
		
		
		System.out.println("数据获取完成");
		
		if(text != "") {
			for(String str : text.split(",")) {
				String[] arr = str.split(":");
				
				Arrange a = null;
				if(userShifts.containsKey(arr[0])  ){
					Map<String, Arrange> dateValue = userShifts.get(arr[0]);
					if(dateValue.containsKey(arr[1]) ){
						if( (dateValue.get(arr[1]).getWorker()==null && arr.length>=3) || (dateValue.get(arr[1]).getWorker()!=null && !dateValue.get(arr[1]).getWorker().equals(arr.length>=3?arr[2]:""))){
							Arrange b = dateValue.get(arr[1]);
							b.setWorker(arr.length>=3?arr[2]:"");
							b.setState(2);
							b.setOperator(user.getUsername());
							b.setOperatime(new Date());
							b.setSection(section);
							if(special.contains(arr[0]+"-"+arr[1]))
								b.setState(5);
							else
								b.setState(2);
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
					System.out.println("未搜索到到班次："+arr[0]);
					continue;
				}
				a.setSection(section);
				a.setShift(arr[0]);
				a.setDate(arr[1]);
				a.setOperator(user.getUsername());
				a.setOperatime(new Date());
				a.setState(2);
				if(arr.length>=3)
					a.setWorker(arr[2]);
				else 
					a.setWorker("");
				if(special.contains(arr[0]+"-"+arr[1]))
					a.setState(5);
				else
					a.setState(2);
				list.add(a);
				userShifts.get(arr[0]).put(arr[0], a);
			}
		}
		
		
		arrangeManager.saveAll(list);
		System.out.println(list.size()+"保存完成");
		return true;
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
		if(section.equals(Constants.LaboratoryCode)){
			return wInfoManager.getByType(2);
		}
		
		section = SectionUtil.getInstance(rmiService, sectionManager).getLabValue(section);
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
				if(a.getSection()!=null && sectionMap.get(a.getSection())!=null && sectionMap.get(a.getSection()).equals(section)){
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
