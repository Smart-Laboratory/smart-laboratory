package com.smart.webapp.controller.execute;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.lisservice.WebService;
import com.smart.model.lis.Ylxh;
import com.smart.service.lis.*;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.YlxhUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.api.model.ExecuteInfo;
import com.smart.model.lis.Patient;
import com.smart.Constants;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.InvalidSample;
import com.smart.model.lis.Sample;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/execute*")
public class ExecuteViewController {

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
        String from=request.getParameter("from");
        String to=request.getParameter("to");

        Patient patient = patientManager.getByPatientId(patientId);
		WebService webService = new WebService();
		if(patient == null) {
			patient = webService.getPatient(patientId);
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
		List<LabOrder> labOrders = labOrderManager.getByPatientId(patientId, from, to);
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
        //待查项目
        String examtodo="";
        List<String> exams = webService.getJCXM(patientId, from, to);
        for(String exam : exams){
            if(!examtodo.contains(exam)){
                if(examtodo.isEmpty())
                    examtodo = exam;
                else
                    examtodo += ";" + exam;
            }
        }
        map.put("examtodo", examtodo);
		return map;
	}
	
	@RequestMapping(value = "/ajax/getTests*", method = RequestMethod.GET)
	@ResponseBody
	public String getTests(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String patientId = request.getParameter("patientId");
		String requestmode = request.getParameter("requestmode");
        int isEmergency = Integer.parseInt(request.getParameter("isEmergency"));
		String from=request.getParameter("from");
        String to=request.getParameter("to");

		List<LabOrder> loList = new ArrayList<LabOrder>();
		WebService webService = new WebService();
		if(requestmode.equals("0")) {
			loList.addAll(webService.getExecuteInfo(patientId, requestmode, from, to));
		} else if(requestmode.equals("100")){
			loList.addAll(webService.getExecuteInfo(patientId, "0", from, to));
			loList.addAll(labOrderManager.getByPatientId(patientId, from, to));
		} else {
			loList.addAll(labOrderManager.getByPatientId(patientId, from, to));
		}
		Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();
		StringBuilder html = new StringBuilder();
		LabOrder labOrder = new LabOrder();
		Ylxh ylxh = new Ylxh();
		//记录最新的发票号
		String recentInvoiceNum="";

		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < loList.size(); i++) {
			labOrder = loList.get(i);
			ylxh = ylxhMap.get(ConvertUtil.null2String(labOrder.getYlxh()));
			if(ylxh == null) {

			}
			labOrder.setSampletype(ylxh.getYblx());
			labOrder.setQbgdt(ylxh.getQbgdd());
			labOrder.setQbgsj(ylxh.getQbgsj());
			labOrder.setLabdepartment(ylxh.getKsdm());
			System.out.println("执行标志：" + labOrder.getZxbz() + " 急诊标志：" + labOrder.getRequestmode());
            switch (isEmergency) {
                case 0 :
                    jsonArray.add(getJsonObject(labOrder, ylxh));
                    break;
                case 1 :
                    if(labOrder.getRequestmode() == 0) {
                        jsonArray.add(getJsonObject(labOrder, ylxh));
                    }
                    break;
                case 2 :
                    if(labOrder.getRequestmode() != 0) {
                        jsonArray.add(getJsonObject(labOrder, ylxh));
                    }
                    break;
            }
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsonArray.toString());
		return null;
	}

    private JSONObject getJsonObject(LabOrder labOrder, Ylxh ylxh) {
        SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("zxbz", labOrder.getZxbz());
        if(!getBmp(ylxh.getSglx() + " " + ylxh.getBbl()).equals("notube") && !getBmp(ylxh.getSglx() + " " + ylxh.getBbl()).isEmpty()){
            jsonObject.put("bmp", "../images/bmp/"+ getBmp(ylxh.getSglx() + " " + ylxh.getBbl()));
        } else {
            jsonObject.put("bmp", "");
        }
        jsonObject.put("requestId", labOrder.getRequestId());
        jsonObject.put("labOrderOrg", labOrder.getLaborderorg());
        jsonObject.put("qbgsj", ylxh.getQbgsj());
        jsonObject.put("qbgdd", ylxh.getQbgdd());
        jsonObject.put("requestMode", labOrder.getRequestmode());
        jsonObject.put("ylmc", ylxh.getYlmc());
        jsonObject.put("sglx", ylxh.getSglx());
        jsonObject.put("bbl", ConvertUtil.null2String(ylxh.getBbl()));
        jsonObject.put("price", labOrder.getPrice());
        jsonObject.put("amount", labOrder.getRequestNum());
        jsonObject.put("labDepart", sectionUtil.getLabValue(ylxh.getKsdm()));
        jsonObject.put("hosSection", sectionUtil.getValue(labOrder.getHossection()));
        jsonObject.put("requestTime", Constants.DF8.format(labOrder.getRequesttime()));
        return jsonObject;
    }

    public String getBmp(String str){
		if(str==null || str.isEmpty())
			return "";
		String bmpStr=str;
		if (str.indexOf("黑")>=0) //and str.indexOf('1.6')>0 
			bmpStr="black1d6.bmp";
		else if (str.indexOf("蓝")>=0  &&  (str.indexOf("2")>=0 || str.indexOf("3")>=0)) 
			bmpStr="blue2d7.bmp";
		else if ( str.indexOf("蓝")>=0 && (str.indexOf("4")>=0 || str.indexOf("5")>=0) )
			bmpStr="blue5.bmp";
		else if (str.indexOf("灰")>=0)  //and str.indexOf('2')>0 
			bmpStr="gray2.bmp";
		else if (str.indexOf("紫")>=0)
			bmpStr="purple.png";
		else if ( str.indexOf("红")>=0)
			bmpStr="red5.bmp";
		else if ( str.indexOf("黄")>=0) //and str.indexOf('5')>0 
			bmpStr="yellow.png";
		else if ( str.indexOf("普通")>=0 && str.indexOf("2.7ml")>=0) 
			bmpStr="no_1.bmp";
		else if ( str.indexOf("普通")>=0 && str.indexOf("3ml")>=0) 
			bmpStr="no_2.bmp";
		else if ( str.indexOf("普通")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3.bmp";
		else if ( str.indexOf("特殊")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3.bmp";
		else if ( str.indexOf("特殊")>=0 && str.indexOf("4ml")>=0) 
			bmpStr="no_3.bmp";
		else if ( str.indexOf("血培养")>=0 && str.indexOf("5ml")>=0) 
			bmpStr="no_3.bmp";
		else if ( str.indexOf("肝素")>=0 && str.indexOf("血")>=0) 
			bmpStr="no_4.bmp";
		else if ( str.indexOf("肝素")>=0 && str.indexOf("骨髓")>=0) 
			bmpStr="no_4.bmp";
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
		double ld_time = Double.parseDouble(Constants.DF5.format(now).replace(":", "."));
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
				qdsj = Constants.DF8.format(c.getTime());
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
				qdsj = Constants.DF8.format(c.getTime());
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
				qdsj = Constants.DF8.format(c.getTime());
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
			if(Long.parseLong(Constants.DF3.format(qbgsj))<Long.parseLong(Constants.DF3.format(now))){
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
					hour = Double.parseDouble(Constants.DF5.format(qbgsj).replace(":", "."));
					if(ll_day>1 && labdepartment.contains("1300600") && hour<12){
						
					}else if(ll_day==1 && labdepartment.contains("1300600")){
						c.add(Calendar.DAY_OF_MONTH, 1);
						qbgsj=c.getTime();
						qdsj = Constants.DF8.format(qbgsj);
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
						qdsj=Constants.DF8.format(qbgsj)+"（星期一）";
					}else if( ll_day ==6 && qbgdd.contains("化验单")){
						if(hour<7.30){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期六）";
						}else if(hour>11.30 && hour<14){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 14);
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期六）";
						}else if(hour>17.30){
							c.add(Calendar.DAY_OF_MONTH, 2);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期一）";
						}else{
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期六）";
						}
					}else if(qbgdd.contains("化验单")){
						if(hour<7.30){
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期"+ll_day+"）";
						}else if(hour>17.30){
							c.add(Calendar.DAY_OF_MONTH, 1);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.HOUR_OF_DAY, 8);
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期"+c.get(Calendar.DAY_OF_WEEK)+"）";
						}else{
							qbgsj = c.getTime();
							qdsj=Constants.DF8.format(qbgsj)+"（星期"+ll_day+"）";
						}
					}else{
						qbgsj = c.getTime();
						qdsj=Constants.DF8.format(qbgsj)+"（星期"+ll_day+"）";
					}
						
				}
					
				
				
			}
			if((ll_day==1 || Double.parseDouble(Constants.DF5.format(qbgsj).replace(":", "."))>17.30) && ll_requestmode.equals("1")){
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
	@Autowired
	private YlxhManager ylxhManager;
}
