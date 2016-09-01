package com.smart.webapp.controller.pb;

import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.service.RMIService;
import com.smart.model.pb.Shift;
import com.smart.model.pb.SxSchool;
import com.smart.service.ShiftManager;
import com.smart.service.SxSchoolManager;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.lis.Section;
import com.smart.model.pb.DayShift;
import com.smart.model.pb.WInfo;
import com.smart.service.DayShiftManager;
import com.smart.service.WInfoManager;

import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SectionUtil;

@Controller
@RequestMapping("/pb/sz*")
public class PbszController extends PbBaseController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ShiftManager shiftManager;
	
//	@Autowired
//	private AtypeManager atypeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private RMIService rmiService;
	
	@Autowired
	private UserManager userManager;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String section = "";
	
	private Map<Long, String> schoolMap = new HashMap<Long,String>();
	
	public void initSchoolMap(){
		List<SxSchool> schools = sxSchoolManager.getAll();
		for(SxSchool s : schools){
			schoolMap.put(s.getId(), s.getName());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		section = user.getLastLab();
		Map<String, String> departList = new HashMap<String, String>();
		String department = user.getPbsection();
		initLabMap();
		if(department != null){
			for(String depart : department.split(",")){
				departList.put(depart, labMap.get(depart));
				if(section.isEmpty()){
					section=depart;
				}
			}
		}
		Map<String, String> sxshifts = new HashMap<String,String>();
		List<Shift> sx = shiftManager.getSx();
		for(Shift shift : sx){
			sxshifts.put(shift.getAb(), shift.getName());
		}
		
		request.setAttribute("sxshifts", sxshifts);
		request.setAttribute("departList", departList);
		request.setAttribute("section", section);
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/ajax/getWinfo*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getWorkInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int page = Integer.parseInt(request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("rows"));
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");
		String sec = request.getParameter("section");
		String search = request.getParameter("_search");
		if(sec!=null&&!sec.isEmpty()){
			section = sec;
		}
		List<WInfo> list = null;
		if(schoolMap.size()==0)
			initSchoolMap();
		
		if(search.equals("true")){
			String searchField = request.getParameter("searchField");
			String searchString = request.getParameter("searchString");
			
			list = wInfoManager.getBySearch(searchField,searchString);
			
		}else if(sidx != ""){
			list = wInfoManager.getBySection(section, sidx, sord);
		}else{
			list = wInfoManager.getBySection(section);
		}
		
		
		
//		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		if(list!=null){
			while (index < row && (start + index) < listSize) {
				Map<String, Object> map = new HashMap<String, Object>();
				WInfo wi = list.get(start + index);
				map.put("id", wi.getId());
				map.put("workid", wi.getWorkid());
				map.put("name", wi.getName());
				map.put("sex", wi.getSexString());
				map.put("section", labMap.get(wi.getSection()));
				map.put("worktime", wi.getWorktime()==null?"":sdf.format(wi.getWorktime()));
				map.put("type", wi.getTypeString());
				map.put("phone", wi.getPhone());
				map.put("shift", wi.getShift());
				map.put("ord1", wi.getOrd1());
				map.put("ord2", wi.getOrd2());
				map.put("ord3", wi.getOrd3());
				map.put("ord4", wi.getOrd4());
				map.put("ord5", wi.getOrd5());
				map.put("ord6", wi.getOrd6());
				map.put("holiday", wi.getHoliday());
				map.put("defeHoliday", wi.getDefeHolidayNum());
				map.put("defeHolidayhis", wi.getDefeholidayhis());
				map.put("lxsy", wi.getLxsy());
				map.put("isactive", wi.getIsActive()==1?"使用":"不使用");
				map.put("school", wi.getSchool()==null?"无":schoolMap.get(Long.parseLong(wi.getSchool())));
				map.put("usercomment", wi.getUsercomment()==null?"":wi.getUsercomment());
				dataRows.add(map);
				index++;
			}
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/*@RequestMapping(value = "/ajax/getAtype*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getAtype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = Integer.parseInt(request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("rows"));
		List<AttenceType> list = atypeManager.getBySection(section);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			AttenceType at = list.get(start + index);
			map.put("id", at.getId());
			map.put("name", at.getName());
			map.put("ab", at.getAb());
			map.put("type", at.getTypeString());
			map.put("iscancel", at.getIsCancelString());
			map.put("order", at.getOrder());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}*/
	
	@RequestMapping(value = "/ajax/getShift*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getShift(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = Integer.parseInt(request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("rows"));
		List<Shift> list = shiftManager.getShiftBySection(section);
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			Shift sh = list.get(start + index);
			map.put("id", sh.getId());
			map.put("name", sh.getName());
			map.put("ab", sh.getAb());
			map.put("wtime", sh.getWorktime());
			map.put("section", labMap.get(sh.getSection()));
			map.put("order", sh.getOrder());
			map.put("days", sh.getDays());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/ajax/getDShift*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getDayShift(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = Integer.parseInt(request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("rows"));

		List<SxSchool> list = sxSchoolManager.getAll();
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			SxSchool dsh = list.get(start + index);
			map.put("id", dsh.getId());
			map.put("phone", dsh.getPhone());
			map.put("name", dsh.getName());
			map.put("namesx", dsh.getNamesx());
			map.put("address", dsh.getAddress());
			map.put("system", dsh.getSystem());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/wiedit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean wiedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if(oper.equals("del")){
			wInfoManager.remove(Long.parseLong(id));
			return true;
		}
		String name = request.getParameter("name");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String section = request.getParameter("section");
		String workid = request.getParameter("workid");
		String phone = request.getParameter("phone");
		String shift = request.getParameter("shift");
		String ord1 = request.getParameter("ord1");
		String ord2 = request.getParameter("ord2");
		String ord3 = request.getParameter("ord3");
		String ord4 = request.getParameter("ord4");
		String ord5 = request.getParameter("ord5");
		String ord6 = request.getParameter("ord6");
		Date worktime = sdf.parse(request.getParameter("worktime"));
		System.out.println(worktime.getTime());
		int type = Integer.parseInt(request.getParameter("type"));
		String holiday = request.getParameter("holiday");
		String defeHolidayhis = request.getParameter("defeHolidayhis");
		int isActive = Integer.parseInt(request.getParameter("isactive"));
		String school = request.getParameter("school");
		String usercomment = request.getParameter("usercomment");
		if(school.equals(0))
			school = null;
		
		WInfo wi = new WInfo();
		if(oper.equals("add")) {
			wi.setName(name);
			wi.setSex(sex);
			wi.setSection(section);
			wi.setWorktime(worktime);
			wi.setType(type);
			wi.setWorkid(workid);
			wi.setPhone(phone);
			wi.setShift(getShift(section));
			wi.setOrd1(ord1==null?0:Integer.parseInt(ord1));
			wi.setOrd2(ord2==null?0:Integer.parseInt(ord2));
			wi.setOrd3(ord3==null?0:Integer.parseInt(ord3));
			wi.setOrd4(ord4==null?0:Integer.parseInt(ord4));
			wi.setOrd5(ord5==null?0:Integer.parseInt(ord5));
			wi.setOrd6(ord6==null?0:Integer.parseInt(ord6));
			wi.setHoliday(holiday==null?0:Double.parseDouble(holiday));
			wi.setDefeholidayhis(defeHolidayhis==null?0:Double.parseDouble(defeHolidayhis));
			wi.setIsActive(isActive);
			wi.setSchool(school);
			wi.setUsercomment(usercomment);
			wInfoManager.save(wi);
		} else if (oper.equals("edit")) {
			wi = wInfoManager.get(Long.parseLong(id));
			wi.setName(name);
			wi.setSex(sex);
			wi.setSection(section);
			wi.setWorktime(worktime);
			wi.setType(type);
			wi.setWorkid(workid);
			wi.setPhone(phone);
			wi.setShift(shift);
			wi.setOrd1(ord1==null?0:Integer.parseInt(ord1));
			wi.setOrd2(ord2==null?0:Integer.parseInt(ord2));
			wi.setOrd3(ord3==null?0:Integer.parseInt(ord3));
			wi.setOrd4(ord4==null?0:Integer.parseInt(ord4));
			wi.setOrd5(ord5==null?0:Integer.parseInt(ord5));
			wi.setOrd6(ord6==null?0:Integer.parseInt(ord6));
			wi.setHoliday(holiday==null?0:Double.parseDouble(holiday));
			wi.setDefeHoliday(wi.getDefeHoliday());
			wi.setDefeholidayhis(defeHolidayhis==null?0:Double.parseDouble(defeHolidayhis));
			wi.setIsActive(isActive);
			wi.setSchool(school);
			wi.setUsercomment(usercomment);
			wInfoManager.save(wi);
		} else {
			wInfoManager.remove(Long.parseLong(id));
		}
		return true;
	}
	
	private String getShift(String section) {
		List<String> list = shiftManager.getBySection(section);
		String shift = "";
		for(String s : list) {
			shift += s + ",";
		}
		return shift;
	}

	/*@RequestMapping(value = "/kqedit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean kqedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String ab = request.getParameter("ab");
		int type = Integer.parseInt(request.getParameter("type"));
		int iscancel = Integer.parseInt(request.getParameter("iscancel"));
		int order = Integer.parseInt(request.getParameter("order"));
		
		AttenceType at = new AttenceType();
		if(oper.equals("add")) {
			at.setName(name);
			at.setAb(ab);
			at.setType(type);
			at.setIscancel(iscancel);
			at.setOrder(order);
			atypeManager.save(at);
		} else if (oper.equals("edit")) {
			at.setId(Long.parseLong(id));
			at.setName(name);
			at.setAb(ab);
			at.setType(type);
			at.setIscancel(iscancel);
			at.setOrder(order);
			atypeManager.save(at);
		} else {
			atypeManager.remove(Long.parseLong(id));
		}
		return true;
	}*/
	
	@RequestMapping(value = "/bcedit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean bcedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		
		if(oper.equals("del")) {
			shiftManager.remove(Long.parseLong(id));
			return true;
		}
		
		String name = request.getParameter("name");
		String ab = request.getParameter("ab");
		String wtime = request.getParameter("wtime");
		String days = request.getParameter("days");
		String section = request.getParameter("section");
		int order = Integer.parseInt(request.getParameter("order"));
		
		Shift sh = new Shift();
		if(oper.equals("add")) {
			sh.setName(name);
			sh.setAb(ab);
			sh.setWorktime(wtime);
			sh.setSection(section);
			sh.setOrder(order);
			sh.setDays(Double.parseDouble(days));
			shiftManager.save(sh);
		} else if (oper.equals("edit")) {
			sh.setId(Long.parseLong(id));
			sh.setName(name);
			sh.setAb(ab);
			sh.setWorktime(wtime);
			sh.setSection(section);
			sh.setOrder(order);
			sh.setDays(Double.parseDouble(days));
			shiftManager.save(sh);
		} 
		return true;
	}
	
	@RequestMapping(value = "/dbcedit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean dbcedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String namesx = request.getParameter("namesx");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String system = request.getParameter("system");
		
		SxSchool dsh = new SxSchool();
		if(oper.equals("add")) {
			dsh.setName(name);
			dsh.setNamesx(namesx);
			dsh.setPhone((phone==null||phone.isEmpty())?0:Integer.parseInt(phone));
			dsh.setAddress(address);
			dsh.setSystem(system);
			sxSchoolManager.save(dsh);
		} else if (oper.equals("edit")) {
			dsh.setId(Long.parseLong(id));
			dsh.setName(name);
			dsh.setNamesx(namesx);
			dsh.setPhone((phone==null||phone.isEmpty())?0:Integer.parseInt(phone));
			dsh.setAddress(address);
			dsh.setSystem(system);
			sxSchoolManager.save(dsh);
		} else {
			dayShiftManager.remove(Long.parseLong(id));
		}
		return true;
	}
	@RequestMapping(value = "/resetHoliday*", method = RequestMethod.GET)
	public void resetHoliday(HttpServletRequest request, HttpServletResponse response){
		List<WInfo> wList = wInfoManager.getAll();
		for(WInfo wInfo : wList){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(wInfo.getWorktime());
			int year = calendar.get(Calendar.YEAR);
			calendar.setTime(new Date());
			int yearNow = calendar.get(Calendar.YEAR);
			int gl = yearNow - year;
			if(gl == 0){
				wInfo.setHoliday(2.5);
			}
			else if(gl<10){
				wInfo.setHoliday(5);
			} else if(gl<20){
				wInfo.setHoliday(10);
			} else if(gl>20){
				wInfo.setHoliday(15);
			}
			wInfoManager.save(wInfo);
		}
		
	}
	/**
	 * 获取科室和学校列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/getSchool*", method = RequestMethod.GET)
	@ResponseBody
	public String getSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<SxSchool> schools = sxSchoolManager.getAll();
		String sname="0:无;";
		for(SxSchool s : schools){
			sname += s.getId()+":"+s.getName()+";";
		}
		sname = sname.substring(0,sname.length()-1);
		
		String sectionList = "";
		for(String code : labMap.keySet()){
			sectionList += code + ":" + labMap.get(code)+";";
		}
		
		JSONObject o = new JSONObject();
		o.put("schools", sname);
		o.put("sectionListStr", sectionList.substring(0,sectionList.length()-1));
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
	
	@Autowired
	private SxSchoolManager sxSchoolManager;
}
