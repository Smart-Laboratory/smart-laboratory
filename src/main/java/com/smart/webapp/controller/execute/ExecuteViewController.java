package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.model.ExecuteInfo;
import com.zju.api.model.Patient;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.PatientManager;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/execute*")
public class ExecuteViewController {
	private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView ExecuteView(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView();
		String userid=request.getRemoteUser();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		return view;
	}
	
	@RequestMapping(value = "/getPatient*", method = RequestMethod.GET)
	@ResponseBody
	public Patient getPatient(HttpServletRequest request, HttpServletResponse response){
		String patientId = request.getParameter("patientId");
		Patient patient = rmiService.getPatient(patientId);
		return patient;
	}
	
	@RequestMapping(value = "/getTests*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getTests(HttpServletRequest request, HttpServletResponse response){
		String patientId = request.getParameter("patientId");
		String requestmode = request.getParameter("requestmode");
		Date from=null,to=null;
		try {
			from = request.getParameter("from")==null?null:ymd.parse(request.getParameter("from"));
			to = request.getParameter("to")==null?null:ymd.parse(request.getParameter("to"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<ExecuteInfo> eList = rmiService.gExecuteInfo(patientId, requestmode, from, to);
		StringBuilder html = new StringBuilder();
		ExecuteInfo e = new ExecuteInfo();
		for(int i=0;i<eList.size();i++){
			e=eList.get(i);
			if(i%2==1){
				html.append("<div  id='date"+i+"' class='alert alert-info sampleInfo' style='' >");
			}else{
				html.append("<div  id='date"+i+"' class='alert alert-success sampleInfo' style='' >");
			}
			String bmp="BMP/"+ getBmp(e.getHyfl()) +".bmp";
			String mode = e.getHyjg().substring(0, 1);
			if(mode==null)
				mode="0";
			String reportTime = takeReportTime(e.getQbgsj().toLowerCase(), e.getZxksdm(), mode);
			if(reportTime.contains("不能抽血") || reportTime.contains("勿"))
				e.setSl("-900");
			e.setQbgsj(reportTime);
			e.setZxksdm(e.getZxksdm().trim());
			
			html.append("<div class='col-sm-2' style=''>"+
						"<div class='checkbox col-sm-3'><label><input type='checkbox' value=''></label></div>"+
		    			"<div class='checkbox col-sm-3'><img src='"+bmp+"' alt='"+e.getHyfl()+"'/></div></div>");
			html.append("<div class='col-sm-10' style=''>");
			html.append("<div ><span class='datespan'>收费项目:</span><b id='ylmc'>"+e.getYlmc()+"</b>"+
								"<span >发票号:</span><b id='sfsb'>"+e.getSfsb()+"</b>"+
								"<span >单价:</span><b id='dj'>"+e.getDj()+"</b>"+
									"×<b id='sl'>"+e.getSl()+"</b>"+
								"<span >执行科室:</span><b id='ksdm'>"+e.getZxksdm()+"</b>"+
						"</div>"+
						"<div><span >医嘱号:</span><b id='doctadviseno'>"+e.getDoctadviseno()+"</b>"
						+ "<span >报告时间:</span><b id='qbgsj'>"+e.getQbgsj()+"</b>"+
								"<span >申请时间:</span><b id='kdsj'>"+e.getKdsj()+"</b>"+
								"<span >申请科室:</span><b id='sjksdm'>"+e.getSjksdm()+"</b>"+
								"<span >地点:</span><b id='qbgdd'>"+e.getQbgdd()+"</b>"+
							"</div>");
			html.append("</div></div>");
		}
		Map<String, String> map = new HashMap<String,String>();
		map.put("html", html.toString());
		
		return map;
	}
	
	public String getBmp(String str){
		String bmpStr="";
		if (str.indexOf("黑")>0) //and str.indexOf('1.6')>0 
			bmpStr="black1d6";
		else if (str.indexOf("蓝")>0  &&  (str.indexOf("2")>0 || str.indexOf("3")>0)) 
			bmpStr="blue2d7";
		else if ( str.indexOf("蓝")>0 && (str.indexOf("4")>0 || str.indexOf("5")>0) )
			bmpStr="blue5";
		else if (str.indexOf("灰")>0)  //and str.indexOf('2')>0 
			bmpStr="gray2";
		else if (str.indexOf("紫")>0 && str.indexOf("2")>0) 
			bmpStr="purple2";
		else if (str.indexOf("紫")>0 && str.indexOf("5")>0) 
			bmpStr="purple5";
		else if ( str.indexOf("红")>0) 
			bmpStr="red5";
		else if ( str.indexOf("黄")>0) //and str.indexOf('5')>0 
			bmpStr="yellow5";
		else if ( str.indexOf("普通")>0 && str.indexOf("2.7ml")>0) 
			bmpStr="no_1";
		else if ( str.indexOf("普通")>0 && str.indexOf("3ml")>0) 
			bmpStr="no_2";
		else if ( str.indexOf("普通")>0 && str.indexOf("5ml")>0) 
			bmpStr="no_3";
		else if ( str.indexOf("特殊")>0 && str.indexOf("5ml")>0) 
			bmpStr="no_3";
		else if ( str.indexOf("特殊")>0 && str.indexOf("4ml")>0) 
			bmpStr="no_3";
		else if ( str.indexOf("血培养")>0 && str.indexOf("5ml")>0) 
			bmpStr="no_3";
		else if ( str.indexOf("肝素")>0 && str.indexOf("血")>0) 
			bmpStr="no_4";
		else if ( str.indexOf("肝素")>0 && str.indexOf("骨髓")>0) 
			bmpStr="no_4";
	   else
			bmpStr="notube";
	   return bmpStr;
	}
	
	public String takeReportTime(String sj,String depart, String requestmode){
		return null;
	}
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private PatientManager patientManager;
	@Autowired
	private RMIService rmiService;
}
