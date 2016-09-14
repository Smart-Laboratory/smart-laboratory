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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.Shift;
import com.smart.model.pb.WInfo;
import com.smart.model.pb.WorkCount;
import com.smart.service.ArrangeManager;
import com.smart.service.ShiftManager;
import com.smart.service.UserManager;
import com.smart.service.WInfoManager;
import com.smart.service.WorkCountManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/pb/pb*")
public class PbszCountController extends PbBaseController{
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
	
	private Map<String, Double> shiftTime = new HashMap<String,Double>();

	@RequestMapping(method = RequestMethod.GET,value = "/workCount*")
	@ResponseBody
	public List<WorkCount> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String section = request.getParameter("section");
		String month = request.getParameter("month");
		
		return countWork(section, month);
	}
	
	
	public List<WorkCount> countWork(String section, String month){
		
		List<WorkCount> wList = new ArrayList<WorkCount>();
		if(shiftTime == null || shiftTime.isEmpty())
			initMap();
		
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(month.substring(0,4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		
		List<WInfo> wInfos = wInfoManager.getBySection(section, "0");
		List<WInfo> newWinfos = new ArrayList<WInfo>();
		String names = "";
		for(WInfo w: wInfos){
			names+= "'"+w.getName()+"',";
		}
		names = names.substring(0, names.length()-1);
		List<Arrange> arranges = arrangeManager.getArrangerd(names, month, 0);
		
		List<WorkCount> workCounts = workCountManager.getMonthBySection(section, month);
		//统计本月公休
		List<String> gxList = arrangeManager.getGXcount(month);
		
		int isnew = 1;
		double lxsyOld = 0;
		for(WInfo w : wInfos){
			Set<String> workdate = new HashSet<String>();
			isnew = 1;
			lxsyOld = 0;
			WorkCount workCount = new WorkCount();
			for(WorkCount wc: workCounts){
				if(wc.getWorker().equals(w.getName())){
					workCount = wc;
					isnew = 0;
					lxsyOld = workCount.getDefeholiday();
				}
				
			}
			if(workCount.getSection()==null){
				workCount.setSection(section);
			}else if(!workCount.getSection().contains(section)){
				workCount.setSection(workCount.getSection()+","+section);
			}
			double holiday = 0;
			double worktime = 0;
			double monthOff = 0;
			double lxsy = 0;
			
			
			String shifts = "";
			boolean isxx = true;
			for(Arrange arrange : arranges){
				if(!arrange.getWorker().equals(w.getName()))
					continue;
				isxx = true;
				if(arrange.getShift()!=null && !arrange.getShift().trim().isEmpty()){
					workdate.add(arrange.getDate());
					shifts += arrange.getShift();
					if(arrange.getShift().contains("公休")){
						for(String str : arrange.getShift().replace("公休;", "").split(";")){
							if(!str.contains("休")){
								worktime += 1;
								isxx = false;
								break;
							}
						}
						if(isxx)
							monthOff += 1;
					}
					if(arrange.getShift().contains("日休")){
						for(String str : arrange.getShift().replace("日休;", "").split(";")){
							if(!str.contains("休")){
								isxx = false;
								break;
							}
						}
						if(isxx)
							monthOff += 1;
					}
				}
				
			}
			
			for(String shift : shifts.split(";")){
				if(shiftTime.containsKey(shift)){
					worktime += shiftTime.get(shift);
				}
				if(shift=="年休"){
					holiday +=1;
				}
				if(shift.contains("休") && !shift.contains("公休")&& !shift.contains("日休")){
					monthOff += 1;
				}		
				if(shift.equals(Constants.defeholidayhis)){
					lxsy += 1;
				}
			}
			//计算积修
			double yjx = 0;
			int j = 1;
			
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
			workCount.setDefeholiday(lxsy);
			wList.add(workCount);
			
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
	        if(isnew==1){
	        	w.setLxsy(w.getLxsy()+lxsy);
	        }else{
	        	w.setLxsy(w.getLxsy()+lxsy-lxsyOld);
	        }
	        
	        //计算年休
//			double nx = w.getHolidayNum()-workCountManager.getYearCount(month.substring(0, 4),w.getName());
//			w.setHoliday(w.getHolidayNum() - nx);
			
			newWinfos.add(w);
	        
			
		}
		for(WorkCount w: wList){
			workCountManager.save(w);
		}
		for(WInfo w: newWinfos){
			wInfoManager.save(w);
		}
		return wList;
	}
	
	public void initMap(){
		List<Shift> shifts = shiftManager.getAll();
		for(Shift shift : shifts){
			shiftTime.put(shift.getAb(), shift.getDays());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/ajax/countWorkall*")
	@ResponseBody
	public DataResponse countWorkall(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//先统计各个月科室的工作量
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String section = request.getParameter("section");
		String row = request.getParameter("row");
		String page = request.getParameter("page");
		if(page==null)
			page="1";
		int rownum=0;
		if(row == null)
			rownum = 20;
		else
			rownum = Integer.parseInt(row);
		if(from==null|| from.isEmpty() || to ==null)
			return null;
		
		List<String> workername = wInfoManager.getNameBySection(section);
		List<String> shiftname = shiftManager.getBySection(section);
		//map<姓名，map<工作时间=  ；休息时间= ；>>
		Map<String, Map<String, Double>> workertime = new HashMap<String,Map<String, Double>>();
		//map<姓名，map<班次：次数；>>
		Map<String, Map<String, Integer>> workshift =  new HashMap<String,Map<String, Integer>>();
		
		while(Integer.parseInt(to.replace("-", "")) >= Integer.parseInt(from.replace("-", ""))){
			//统计工作量
			List<WorkCount> workCounts = countWork(section, from);
			for(WorkCount wc : workCounts){
				Map<String, Double> map = new HashMap<String,Double>();
				if(workertime.get(wc.getWorker())!=null){
					map = workertime.get(wc.getWorker());
				}
				if(map.get("worktime")==null){
					map.put("worktime", wc.getWorkTime());
				}else{
					map.put("worktime", wc.getWorkTime()+map.get("worktime"));
				}
				if(map.get("monthOff")==null){
					map.put("monthOff", wc.getMonthOff());
				}else{
					map.put("monthOff", wc.getMonthOff()+map.get("monthOff"));
				}
				workertime.put(wc.getWorker(), map);
			}
			//统计班次种类
			List<Arrange> arranges = arrangeManager.getBySectionMonth(from, section);
			for(Arrange a : arranges){
				String name = a.getWorker();
				Map<String, Integer> map = new HashMap<String,Integer>();
				if(workshift.get(name)!=null){
					map = workshift.get(name);
				}
				if(a.getShift()==null)
					continue;
				for(String shift : a.getShift().split(";")){
					if(shift==null || shift.isEmpty())
						continue;
					if(map.get(shift)==null){
						map.put(shift, 1);
					}else{
						map.put(shift, map.get(shift)+1);
					}
				}
				workshift.put(name, map);
			}
			
			
			int month = Integer.parseInt(from.split("-")[1]);
			if(month<12)
				from = from.split("-")[0] +"-"+( (month+1)<10?"0"+(month+1):(month+1) );
			else
				from = (Integer.parseInt(from.split("-")[0])+1) + "-01";
		}
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		int listSize = 0;
		if (workertime != null)
			listSize = workername.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (rownum == 0 ? listSize : rownum);
		if (x != 0) {
			x = rownum - x;
		}
		int totalPage = (listSize + x) / (rownum == 0 ? listSize : rownum);
		dataResponse.setPage(Integer.parseInt(page));
		dataResponse.setTotal(totalPage);
		int start = rownum * (Integer.parseInt(page) - 1);
		int index = 0;
		if(workername!=null){
			while (index < rownum && (start + index) < listSize) {
				Map<String, Object> map = new HashMap<String, Object>();
				String wname = workername.get(start + index);
				Map<String, Double> w = workertime.get(wname);
				//按顺序取出班次顺序
				Map<String, Integer> shift = workshift.get(wname);
				String shifts = "";
				if(shift!=null){
					for(int i=0;i<shiftname.size();i++){
						if(shift.get(shiftname.get(i))==null)
							continue;
						shifts += shiftname.get(i)+"-"+shift.get(shiftname.get(i))+"; ";
					}
				}
				map.put("name", wname);
				if(w!=null){
					map.put("worktime",w.get("worktime"));
					map.put("monthOff", w.get("monthOff"));
					map.put("shift", shifts);
				}
				dataRows.add(map);
				index++;
			}
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		
		
		return dataResponse;
	}
	
	
	
	
	
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
	
	
	
}
