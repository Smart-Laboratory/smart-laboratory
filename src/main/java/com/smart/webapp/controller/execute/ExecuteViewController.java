package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import com.smart.lisservice.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.model.ExecuteInfo;
import com.smart.model.lis.Patient;
import com.lowagie.text.Section;
import com.smart.Constants;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.InvalidSample;
import com.smart.model.lis.Sample;
import com.smart.model.reagent.Out;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.lis.InvalidSampleManager;
import com.smart.service.lis.PatientManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

import net.coobird.thumbnailator.geometry.Size;

@Controller
@RequestMapping("/manage/execute*")
public class ExecuteViewController {
	private SimpleDateFormat ymd1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ymdh = new SimpleDateFormat("yyyy年MM月dd日 HH:mm(EEE)" );
	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat hhmm = new SimpleDateFormat("hh:mm");

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView ExecuteView(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view=new ModelAndView();
		String userid=request.getRemoteUser();
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		return view;
	}
	
	@RequestMapping(value = "/getPatient*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>  getPatient(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		//查询病人信息
		String patientId = request.getParameter("patientId").trim();
		Patient patient = patientManager.getByPatientId(patientId);
		if(patient == null) {
			patient = new WebService().getPatient(patientId);
			if(patientManager.getByBlh(patient.getBlh()) != null) {
				patient = patientManager.getByBlh(patient.getBlh());
				patient.setPatientId(patient.getPatientId() + "," + patientId);
			}
			patientManager.save(patient);
		}
		//Patient patient = rmiService.getPatient(patientId);
		map.put("patient", patient);
		//查询不合格标本记录
		InvalidSample invalidSample = invalidSampleManager.getByPatientId(patientId);
		if(invalidSample!=null){
			String[] reasonList = Constants.INVALIDSAMPLE_REASON;
			map.put("invalidsample", reasonList[invalidSample.getRejectSampleReason()]);
		}
		String host = request.getRemoteHost();
		map.put("host", host);
		//查询抽血历史
		List<LabOrder> labOrders = labOrderManager.getByPatientId(patientId);
		if(labOrders == null || labOrders.size()==0)
			map.put("size", 0);
		else{
			map.put("size", labOrders.size());
			map.put("labOrder", labOrders.get(0));
		}
		//查询历史检验结果
		List<Sample> samples = sampleManager.getByPatientId(patientId, null);
		if(samples!=null && samples.size()==0){
			map.put("samples", null);
		}else{
			SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
			for(Sample s : samples){
				String depart = sectionUtil.getValue(s.getSectionId());
				if(depart!=null && !depart.isEmpty()){
					s.setSectionId(depart);
				}
			}
			map.put("samples", samples);
		}
		return map;
	}
	
	@RequestMapping(value = "/getTests*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getTests(HttpServletRequest request, HttpServletResponse response){
		String patientId = request.getParameter("patientId");
		String requestmode = request.getParameter("requestmode");
		Date from=null,to=null;
		try {
			from = request.getParameter("from")==null?null:ymd1.parse(request.getParameter("from"));
			to = request.getParameter("to")==null?null:ymd1.parse(request.getParameter("to"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ExecuteInfo> eList = rmiService.gExecuteInfo(patientId, requestmode, from, to);
		StringBuilder html = new StringBuilder();
		ExecuteInfo e = new ExecuteInfo();
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
		//记录最新的发票号
		String recentInvoiceNum="";
		//待查项目
		String examtodo="";
		List<String> exams = rmiService.getExamtode(patientId,from,to);
		for(String exam : exams){
			if(exam.contains("B超") && requestmode.equals("0")){
				if(!examtodo.contains(exam)){
					if(examtodo.isEmpty())
						examtodo = exam;
					else
						examtodo += ";" + exam;
				}
			}
		}
		
		for(int i=0;i<eList.size();i++){
			e=eList.get(i);
			if(i==0)
				recentInvoiceNum = e.getSfsb();
			
			
//			System.out.println(e.getDoctadviseno()+e.getYlmc()+e.getYlxh()+e.getYjsb()+e.getSfsb());
			if(i%2==1){
				html.append("<div  id='date"+i+"' class='alert alert-info sampleInfo' style='' >");
			}else{
				html.append("<div  id='date"+i+"' class='alert alert-success sampleInfo' style='' >");
			}
			String bmp = "";
			if(!getBmp(e.getHyfl()).equals("notube") && !getBmp(e.getHyfl()).isEmpty()){
				bmp ="../images/bmp/"+ getBmp(e.getHyfl()) +".bmp";
			}
			
			String mode = e.getHyjg().substring(0, 1);
			if(mode==null)
				mode="0";
			String reportTimeAndDd = takeReportTime(e.getQbgsj().toLowerCase(), e.getZxksdm(), mode,e.getQbgdd());
			String reportTime = reportTimeAndDd.split("-")[0];
			if(reportTimeAndDd.split("-")[1]!=null)
				e.setQbgdd(reportTimeAndDd.split("-")[1]);
			if(reportTime.contains("不能抽血") || reportTime.contains("勿"))
				e.setSl("-900");
			e.setQbgsj(reportTime);
			e.setZxksdm(e.getZxksdm().trim());
			
			if(e.getSfsb().equals(recentInvoiceNum)){
				html.append("<div class='col-sm-1' style=''>"+
						"<div class='col-sm-6'><label><input type='checkbox' checked value='"+e.getYjsb()+e.getYlxh()+"+"+e.getQbgsj()+"-"+e.getQbgdd()+"'></label></div>");
			}else{
				html.append("<div class='col-sm-1' style=''>"+
						"<div class='col-sm-6'><label><input type='checkbox' value='"+e.getYjsb()+e.getYlxh()+"+"+e.getQbgsj()+"-"+e.getQbgdd()+"'></label></div>");
			}
			if(!bmp.isEmpty()){
				html.append("<div class='col-sm-4'><img src='"+bmp+"' alt='"+e.getHyfl()+"' width='30px' height='50px' /></div>");
			}
			if(e.getHyjg().substring(0, 1).equals("1")){
				html.append("<div class='col-sm-2'><span class='glyphicon glyphicon-star btn-lg' style='color:red;padding-left:0px;' aria-hidden='true'></span></div>");
			}
			html.append("</div>");
			html.append("<div class='col-sm-11' style=''>");
			html.append("<div ><span class='datespan'>收费项目:</span><b id='ylmc'>"+e.getYlmc()+"</b>"+
								"<span >发票号:</span><b id='sfsb'>"+e.getSfsb()+"</b>"+
								"<span >单价:</span><b id='dj'>"+e.getDj()+"</b>"+
									"×<b id='sl'>"+e.getSl()+"</b>"+
								"<span >执行科室:</span><b id='ksdm'>"+sectionUtil.getValue(e.getZxksdm())+"</b>"+
						"</div>"+
						"<div><span >医嘱号:</span><b id='doctadviseno'>"+e.getDoctadviseno()+"</b>"
						+ "<span >报告时间:</span><b id='qbgsj'>"+e.getQbgsj()+"</b>"+
								"<span >申请时间:</span><b id='kdsj'>"+ymdh.format(e.getKdsj())+"</b>"+
								"<span >申请科室:</span><b id='sjksdm'>"+sectionUtil.getValue(e.getSjksdm())+"</b>"+
								"<span >地点:</span><b id='qbgdd'>"+e.getQbgdd()+"</b>"+
							"</div>");
			html.append("</div></div>");
		}
		Map<String, String> map = new HashMap<String,String>();
		map.put("html", html.toString());
		map.put("examtodo", examtodo);
		
		return map;
	}
	
	public String getBmp(String str){
		if(str==null || str.isEmpty())
			return "";
		String bmpStr=str;
		if (str.indexOf("黑")>=0) //and str.indexOf('1.6')>0 
			bmpStr="black1d6";
		else if (str.indexOf("蓝")>=0  &&  (str.indexOf("2")>=0 || str.indexOf("3")>=0)) 
			bmpStr="blue2d7";
		else if ( str.indexOf("蓝")>=0 && (str.indexOf("4")>=0 || str.indexOf("5")>=0) )
			bmpStr="blue5";
		else if (str.indexOf("灰")>=0)  //and str.indexOf('2')>0 
			bmpStr="gray2";
		else if (str.indexOf("紫")>=0 && str.indexOf("2")>=0) 
			bmpStr="purple2";
		else if (str.indexOf("紫")>=0 && str.indexOf("5")>=0) 
			bmpStr="purple5";
		else if ( str.indexOf("红")>=0) 
			bmpStr="red5";
		else if ( str.indexOf("黄")>=0) //and str.indexOf('5')>0 
			bmpStr="yellow5";
		else if ( str.indexOf("普通")>=0 && str.indexOf("2.7ml")>=0) 
			bmpStr="no_1";
		else if ( str.indexOf("普通")>=0 && str.indexOf("3ml")>=0) 
			bmpStr="no_2";
		else if ( str.indexOf("普通")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3";
		else if ( str.indexOf("特殊")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3";
		else if ( str.indexOf("特殊")>=0 && str.indexOf("4ml")>=0) 
			bmpStr="no_3";
		else if ( str.indexOf("血培养")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3";
		else if ( str.indexOf("肝素")>=0 && str.indexOf("血")>=0) 
			bmpStr="no_4";
		else if ( str.indexOf("肝素")>=0 && str.indexOf("骨髓")>=0) 
			bmpStr="no_4";
	   else
			bmpStr="notube";
	   return bmpStr;
	}
	
	public String takeReportTime(String sj,String labdepartment, String ll_requestmode,String qbgdd){
		
//		System.out.println(sj+labdepartment+qbgdd+ll_requestmode);
		
		Date qbgsj = new Date();
		String qdsj="";
		String ls_day="";//取单时间 d01
		int ll_day = 0; //到取单需要几天
		int i =0;
		int ll_time = 0; //半小时，一小时
		
		Date now = new Date();
		double ld_time = Double.parseDouble(hhmm.format(now).replace(":", "."));
//		System.out.println(ld_time);

		Calendar c = new GregorianCalendar();
		c.setTime(now);
		
		int ll_week = c.get(Calendar.DAY_OF_WEEK)-1;
		if(ll_week==0)
			ll_week=7;
		
		if(sj.contains("$"+ll_week)){
			int ll_pos = sj.indexOf("$"+ll_week);
//			System.out.println("$"+ll_week);
			int ll_pos1 = sj.indexOf("(",ll_pos)+1;
			int ll_pos2 = sj.indexOf(")",ll_pos);
			String ls_time = sj.substring(ll_pos1, ll_pos2);  //抽血时间
			ls_time = ls_time.replace(":", ".");
			double ld_timeo = Double.parseDouble(ls_time);    //转换
//			System.out.println(ls_time);
//			System.out.println(ld_timeo);

			if (ld_time > ld_timeo){ //if ld_timeo = 0 then messagebox('1','2')
				ll_pos1=sj.indexOf("{", ll_pos)+1; 
				if(sj.substring(ll_pos1, ll_pos1+6).contains("勿")){
					qdsj = null;
					sj="该检查现在不能抽血,抽血时间、地点请联系！";
					return sj;
				}
				else if(sj.substring(ll_pos1, ll_pos1+6).contains("请")){
					qdsj="";
					ls_time="";
					ll_pos2=sj.indexOf("}", ll_pos1);
					qdsj=sj.substring(ll_pos1,ll_pos2);
				}
				else{
					ll_pos2 = sj.indexOf("|", ll_pos1);
					ls_day = sj.substring(ll_pos1, ll_pos2);
//					System.out.println(ls_day);
					if(ls_day.contains("<")){   //<15:30[d01/11:30]>d01第三种取报告时间
						ls_time = ls_day.substring(ls_day.indexOf("<")+1,ls_day.indexOf("["));
//						System.out.println(ls_time);
						ls_time = ls_time.replace(":", ".");
						ld_timeo = Double.parseDouble(ls_time);    //转换
						if(ld_time > ld_timeo){
							ll_pos1 = sj.indexOf(">", ll_pos1);
							ls_day = ls_day.substring(ls_day.indexOf(">")+1);
							ll_pos1=sj.indexOf("|", ll_pos1)+1;
							ll_pos2=sj.indexOf("}",ll_pos);
							ls_time = sj.substring(ll_pos1, ll_pos2);
//							System.out.print(">超过当天取单时间:"+ls_day+"|"+ls_time);  //取单时间
						}
						else{
							ls_day = ls_day.substring(ls_day.indexOf("<")+1, ls_day.indexOf(">"));
							ls_time = ls_day.substring(ls_day.indexOf("/")+1, ls_day.indexOf("]"));
							ls_day = ls_day.substring(ls_day.indexOf("[")+1, ls_day.indexOf("/"));
//							System.out.print("<未超过当天取单时间:"+ls_day+"/"+ls_time);  //取单时间
						}
					}
					else{
						ll_pos1=sj.indexOf("|",ll_pos1);
						ll_pos2=sj.indexOf("}",ll_pos1);
						ls_time = sj.substring(ll_pos1+1, ll_pos2);//取单时间
//						System.out.println(ls_time);
					}
				}
				
				if(ls_day.toLowerCase().startsWith("w")){
					ll_day = (Integer.parseInt(ls_day.substring(1,2))+i)*7 + Integer.parseInt(ls_day.substring(2,3)) - ll_week;
				}else{
					ll_day = Integer.parseInt(ls_day.substring(1,3)) + i;
				}
//				System.out.println(ymd.format(c.getTime()));
//				System.out.println(ll_day);
				c.add(Calendar.DAY_OF_MONTH, ll_day);
//				System.out.println(ymdh.format(c.getTime()));
				qbgsj = c.getTime();
				//判断是否在法定节假日
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ls_time.split(":")[0]));
				c.set(Calendar.MINUTE, Integer.parseInt(ls_time.split(":")[1]));
				qdsj = ymdh.format(c.getTime());
				qbgsj = c.getTime();
//				System.out.println(qdsj);
			}
			else{
				ll_pos1 = sj.indexOf("[",ll_pos)+1;
				if(sj.substring(ll_pos1, ll_pos1+6).contains("勿")){
					qdsj = null;
					sj="该检查现在不能抽血,抽血时间、地点请联系！";
					return sj;
				}
				else if(sj.substring(ll_pos1, ll_pos1+6).contains("请")){
					qdsj="";
					ll_pos2=sj.indexOf("]", ll_pos1);
					qdsj=sj.substring(ll_pos1,ll_pos2);
				}else{
					ll_pos2=sj.indexOf("|",ll_pos);
					ls_day = sj.substring(ll_pos1, ll_pos2);
				}
//				System.out.println("ll_day="+ll_day);
				if(ls_day.toLowerCase().startsWith("w")){
					ll_day = (Integer.parseInt(ls_day.substring(1,2))+i)*7 + Integer.parseInt(ls_day.substring(2,3)) - ll_week;
				}else{
					ll_day = Integer.parseInt(ls_day.substring(1,3)) + i;
				}
				ll_pos1 = sj.indexOf("|",ll_pos)+1;
				ll_pos2 = sj.indexOf("]",ll_pos);
				ls_time = sj.substring(ll_pos1, ll_pos2);
				
//				System.out.println(ymd.format(c.getTime()));
				
				c.add(Calendar.DAY_OF_MONTH, ll_day);
//				System.out.println(ymdh.format(c.getTime()));
				qbgsj = c.getTime();
				//判断是否在法定节假日
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ls_time.split(":")[0]));
				c.set(Calendar.MINUTE, Integer.parseInt(ls_time.split(":")[1]));
				qdsj = ymdh.format(c.getTime());
				qbgsj = c.getTime();
//				System.out.println(qdsj);
			}
		}
		else if(sj.contains("小时")){
			if(sj.contains("半小时")){
				ll_time = 30; //表示半小时
				c.add(Calendar.MINUTE, 30);
			}
			else if(sj.contains("一小时"))
				ll_time = 1;
			else if(sj.contains("两小时"))
				ll_time = 2;
			else if(sj.contains("三小时"))
				ll_time = 3;
			else if(sj.contains("四小时"))
				ll_time = 4;
			else if(sj.contains("五小时"))
				ll_time = 5;
			else {
				ll_time = 0;
			}
			
			if(ll_time>0){
				if(ld_time >= 6.00  && ld_time < 8.00){
					c.set(Calendar.HOUR_OF_DAY, 8);
					if(ll_time != 30){
						c.set(Calendar.MINUTE, 0);
						c.add(Calendar.HOUR_OF_DAY, ll_time);
					}else
						c.set(Calendar.MINUTE, 30);
				}else{
					if(ll_time != 30){
						c.add(Calendar.HOUR_OF_DAY, ll_time);
					}
				}
				qdsj = ymdh.format(c.getTime());
				qbgsj = c.getTime();
//				System.out.println(sj+qdsj);
			}
			
		}
		else if(sj.trim().length()>3){
			
		}
		else{
			qdsj = "";
			sj = "该检查现在不能抽血,抽血时间、地点请联系！";
//			return sj;
		}
//---------------------------------------------------------------------------------			
		
		if(ll_requestmode != null)
			ll_requestmode = "0";
		if(qdsj!=null){
			double hour = 0;
			c.setTime(qbgsj);
			ll_day=c.get(Calendar.DAY_OF_WEEK);
//			System.out.println("取单日星期几"+ll_day);
			if(Long.parseLong(ymd.format(qbgsj))<Long.parseLong(ymd.format(now))){
				qdsj="请与科室联系";
			}
			else{
				if(ll_requestmode.equals("1")){
					long l=qbgsj.getTime()-now.getTime();
					hour=l/(60*60*1000);
					if(hour>2)
						qdsj="标本送达两小时后";
				}
				else{
					hour = Double.parseDouble(hhmm.format(qbgsj).replace(":", "."));
					if(ll_day>1 && labdepartment.contains("1300600") && hour<12){
						
					}else if(ll_day==1 && labdepartment.contains("1300600")){
						c.add(Calendar.DAY_OF_MONTH, 1);
						qbgsj=c.getTime();
						qdsj = ymdh.format(qbgsj);
//						System.out.println(qdsj);
					}
					ll_day -=1;
					if(ll_day==0)
						ll_day=7;
					if(ll_day==7 && qbgdd.contains("化验单")){
						c.add(Calendar.DAY_OF_MONTH, 1);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.HOUR_OF_DAY, 8);
						qbgsj = c.getTime();
						qdsj=ymdh.format(qbgsj)+"（星期一）";
					}else if( ll_day ==6 && qbgdd.contains("化验单")){
						if(hour<7.30){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期六）";
						}else if(hour>11.30 && hour<14){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 14);
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期六）";
						}else if(hour>17.30){
							c.add(Calendar.DAY_OF_MONTH, 2);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期一）";
						}else{
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期六）";
						}
					}else if(qbgdd.contains("化验单")){
						if(hour<7.30){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期"+ll_day+"）";
						}else if(hour>17.30){
							c.add(Calendar.DAY_OF_MONTH, 1);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期"+c.get(Calendar.DAY_OF_WEEK)+"）";
						}else{
							qbgsj = c.getTime();
							qdsj=ymdh.format(qbgsj)+"（星期"+ll_day+"）";
						}
					}else{
						qbgsj = c.getTime();
						qdsj=ymdh.format(qbgsj)+"（星期"+ll_day+"）";
					}
						
				}
					
				
				
			}
			if((ll_day==1 || Double.parseDouble(hhmm.format(qbgsj).replace(":", "."))>17.30) && ll_requestmode.equals("1")){
				if(!labdepartment.contains("1300500"))
					qbgdd = "1号楼1楼急诊化验室";
			}
		}
		
		
		if(qdsj==null || qdsj.isEmpty()){
			qdsj = "请与检验部门联系";
		}
		
//		System.out.println("结束："+qdsj);
		
		
		return qdsj+"-"+qbgdd;
	}
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private RMIService rmiService;
	@Autowired
	private InvalidSampleManager invalidSampleManager;
	@Autowired
	private LabOrderManager labOrderManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private SectionManager sectionManager;
	@Autowired
	private PatientManager patientManager;
}
