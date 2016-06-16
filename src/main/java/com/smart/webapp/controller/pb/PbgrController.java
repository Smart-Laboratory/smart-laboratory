package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.smart.model.pb.Arrange;
import com.smart.model.pb.WInfo;
import com.smart.model.pb.WorkCount;
import com.smart.service.ArrangeManager;
import com.smart.service.WInfoManager;
import com.smart.service.WorkCountManager;
import com.zju.api.service.RMIService;

import com.smart.model.pb.Shift;
import com.smart.service.ShiftManager;

@Controller
@RequestMapping("/pb/grcx*")
public class PbgrController extends PbBaseController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private ShiftManager shiftManager;
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = request.getParameter("name");
		return new ModelAndView().addObject("name", name);
	}

	@RequestMapping(value = "/ajax/getData*", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getPersonalPB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String month = request.getParameter("month");
		String name = request.getParameter("name");
		if(name == null || name.isEmpty() || month==null ||month.isEmpty())
			return null;
		
		List<Arrange> arrList = arrangeManager.getPersonalArrange(name, month);
		List<Shift> shList = shiftManager.getAll();
		Map<String, Shift> map = new HashMap<String, Shift>();
		for(Shift sh : shList) {
			map.put(sh.getAb(), sh);
		}
		
		List<Object> data = new ArrayList<Object>();
		
		class Temp {
			public String title;
			public String start;
			public String color;
			public String textColor;
		}
		
		for(Arrange arr : arrList) {
			if(arr.getType() == 0) {
				String abs = arr.getShift();
				for(String ab : abs.split(";")){
					String title = "";
					if(!ab.isEmpty()){
//						System.out.println(ab);
						Temp t = new Temp();
						title = map.get(ab).getName();
						title += " - "+map.get(ab).getWorktime();
						t.title = title;
						t.start = arr.getDate();
						t.color = "#87CEFA";
						t.textColor = "black";
						data.add(t);
					}
				}
			} else {
				
			}
		}
		
		return data;
	}
	
	@RequestMapping(value = "/ajax/getWInfo*", method = RequestMethod.GET)
	@ResponseBody
	public String getWInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String moment = request.getParameter("moment");
		Date date = null;
		try {
			date = ym.parse(moment);
		} catch (Exception e) {
			// TODO: handle exception
		}
		moment = ym.format(date);
		if(name.isEmpty())
			return null;
		WInfo wInfo = wInfoManager.getByName(name);
		JSONObject o = new JSONObject();
		o.put("name",wInfo.getName() );
		String section = wInfo.getSection();
		initLabMap();
//		for(String s : section.split(",")){
//			section = section.replace(s, sectionUtil.getKey(s));
//		}
		
		o.put("section", section);
		o.put("sworktime",ymd.format( wInfo.getWorktime() ));
		o.put("nx",wInfo.getHoliday() );
		o.put("lnjx",wInfo.getDefeholidayhis() );
		o.put("jx",wInfo.getDefeHolidayNum() );
		String yjx = "";
		if(wInfo!=null && wInfo.getDefeHoliday()!=null && !wInfo.getDefeHoliday().isEmpty()){
			for(String s : wInfo.getDefeHoliday().split(";")){
				if(s.contains(moment)){
					yjx = s.replace(moment+":", "");
				}
			}
		}
		
		o.put("yjx", yjx);
		
		WorkCount wCount = workCountManager.getPersonByMonth(name, moment, wInfo.getSection().split(",")[0]);
		if(wCount!=null)
			o.put("worktime", wCount.getWorkTime());
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(o.toString());
		return null;
	}
	
	@Autowired
	private RMIService rmiService;
	@Autowired
	private WorkCountManager workCountManager;
	
}

