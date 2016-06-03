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

import org.codehaus.jettison.json.JSONObject;
import org.drools.core.base.evaluators.IsAEvaluatorDefinition.IsAEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.execute.SampleNoBuilder;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.YlxhManager;
import com.zju.api.model.ExecuteInfo;
import com.zju.api.model.Patient;
import com.zju.api.service.RMIService;




@Controller
@RequestMapping("/manage*")
public class ExecuteController {
	
	private SimpleDateFormat ymd1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat hhmm = new SimpleDateFormat("hh:mm");
	
	@RequestMapping(value = "/execute/submit*", method = RequestMethod.GET)
	@ResponseBody
	public String getPatient(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		String patientId = request.getParameter("patientId");
		String mode = request.getParameter("requestmode");
		Date from=new Date(),to=new Date();
		try {
			from = request.getParameter("from")==null?null:ymd1.parse(request.getParameter("from"));
			to = request.getParameter("to")==null?null:ymd1.parse(request.getParameter("to"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ExecuteInfo> eList = rmiService.gExecuteInfo(patientId, mode, from, to);
		
		String selval = request.getParameter("selval");
		String examinaim="",labdepart="",sampleno="",ybh_like="",ylxhdescribe="";
		double fee=0;
		Map<String, String> selMap = new HashMap<String,String>();
		int cj_sl=0,cycle=0,requestmode=0,requestmode_temp;
		boolean samplenoExist = false ;
		Date executetime = new Date(),receivetime=new Date();
		double time=0,day=0;
		int nextday=0;
		
		
		for(String str : selval.split(";")){
			if(str!=null && !str.isEmpty()){
				selMap.put(str.split(";")[0], str.split(";")[1]);
			}
		}
		
		JSONObject o = new JSONObject();
		ExecuteInfo e = new ExecuteInfo();
		ExecuteInfo info = new ExecuteInfo();
		Ylxh ylxh = new Ylxh();
		
		for(int i=0;i<eList.size();i++){
			e = eList.get(i);
			if(selMap.get(e.getYjsb())==null || !selMap.get(e.getYjsb()).equals(e.getYlxh())){
				continue;
			}
			
			if(e.getSl()!=null && Integer.parseInt(e.getSl())<1){
				o.put("error", "警告！选择项目错误！数量为"+e.getSl()+"件！");
			}
			
			fee = Double.parseDouble(e.getDj()) * Double.parseDouble(e.getSl());
			examinaim = e.getYlmc();
			if(e.getJyxmfl().equals("9") && examinaim.contains("/")){
				examinaim = examinaim.substring(0, examinaim.indexOf("/"));
			}
			
			if(e.getYlxh()==null)
				e.setYlxh("");
			labdepart = e.getZxksdm();
			if(labdepart == null)
				labdepart="";
			cj_sl = Integer.parseInt(e.getRequestmode());
			cycle = Integer.parseInt(e.getHyjg().substring(1, 2));
			requestmode = Integer.parseInt(e.getHyjg().substring(0, 1));
			
//			if pos(gs_ksdm,'12001') > 0 then
//			   update yj_yjk2  set zxksdm='1200100' 
//			   where yjsb = :ll_yjsb and (
//			   ylxh=8596 or ylxh=8597 or ylxh=10054 or ylxh=10056 or ylxh=16040 or ylxh=16041 or ylxh=16042 or ylxh=16049);
//			   commit;
//			end if
			if(requestmode != 1){
				requestmode =0;
				cycle = 0;
			}
			
			int stayhospitalmode = 1; //门诊
			executetime = new Date();
			
			Patient patient = rmiService.getPatient(e.getJzkh());
			String diagnostic = e.getLzcd()==null ? "":e.getLzcd();
			String sampletype = (e.getYblx()==null ? "":e.getYblx()).trim();
			
			//合并组合
			if(sampletype != null && !sampletype.isEmpty()){    
				for(int j=i;j<eList.size();j++){
					info = eList.get(j);
					if(selMap.get(info.getYjsb())==null || !selMap.get(info.getYjsb()).equals(info.getYlxh())){
						continue;
					}
					info.setZxksdm(info.getZxksdm().trim());
					requestmode_temp = Integer.parseInt(info.getHyjg().substring(0, 1));
					//判断部门等是否一样
					if(requestmode_temp == requestmode && info.getYblx().trim().equals(sampletype) && info.getZxksdm().equals(e.getZxksdm()) && info.getQbgsj().equals(e.getQbgsj()) 
							&& info.getQbgdd().equals(e.getQbgdd())){
						if(info.getJyxmfl().equals("9") && info.getYlmc().contains("/"))
							info.setYlmc(info.getYlmc().substring(0, info.getYlmc().indexOf("/")));
						
						if(!info.getYlmc().contains(e.getYlmc())){
							e.setYlmc(e.getYlmc()+"+"+info.getYlmc());
							selMap.remove(info.getYjsb());
							if(info.getYlxh()!=null && !info.getYlxh().isEmpty())
								e.setYlxh(e.getYlxh()+"+"+info.getYlxh());
							fee = fee + Double.parseDouble(info.getDj()) * Double.parseDouble(info.getSl());
						}
					}
					
				}
			}
			
			ybh_like = ymd.format(executetime)+"%";
			//生成样本号
			time = Double.parseDouble(hhmm.format(executetime).replace(":", "."));
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(executetime);
			day = calendar.get(Calendar.DAY_OF_WEEK);
			
			do {
				samplenoExist = false;
				if(requestmode ==1){
					e.setZxksdm("1300300");
					labdepart = "1300300";
					sampleno = "0";
					receivetime = executetime;
				}else{
					if(labdepart.equals("1300600")){
						if(day>1 && day<8){
							if(time<14 && Integer.parseInt(e.getQbbdd())>0){
								nextday = 0;
								sampleno = getautoSampleno(nextday,labdepart,Integer.parseInt(e.getQbbdd()));
							}else {
								sampleno = "0";
							}
						}else
							sampleno = "0";
					}else if(labdepart.contains("13007")){
						if(day>1 && (time>5.06 && time<10) && Integer.parseInt(e.getQbbdd())>0){
							nextday = 0;
							sampleno = getautoSampleno(nextday,labdepart,Integer.parseInt(e.getQbbdd()));
						}else
							sampleno = "0";
					}else if(labdepart.contains("13001")){
						if(day>0 && (time>6.06 && time<17.30) && Integer.parseInt(e.getQbbdd())>0){
							nextday = 0;
							sampleno = getautoSampleno(nextday,labdepart,Integer.parseInt(e.getQbbdd()));
						}else
							sampleno = "0";
					}else if(labdepart.contains("1300501")){
						if(day>1){
							if(time<13)
								nextday=0;
							else
								nextday = 1;
							if(Integer.parseInt(e.getQbbdd())>0)
								sampleno = getautoSampleno(nextday,labdepart,Integer.parseInt(e.getQbbdd()));
							else
								sampleno = "0";
						}else
							sampleno = "0";
					}
					
				}
				
				if(sampleno.trim().length()==14)
					samplenoExist = sampleManager.existSampleNo(sampleno);
			} while (samplenoExist);
			//生成样本号结束
			
			//--一个条码抽血多次---
			ylxh = ylxhManager.get(Long.parseLong(e.getYlxh()));
			ylxhdescribe = ylxh.getProfiletest();
			//if li_dccx = 1  and li_pos > 0  and pos(ls_ylxh,'+') = 0  then //有多个标本
			if(ylxhdescribe.contains(",") && ylxhdescribe.contains("+")){
				for(String testid : ylxh.getProfiletest().split(",")){
				
				}
			}else{
				String testid = "",eylxh="";
				Sample sample = new Sample();
				Process process = new Process();
				eylxh = e.getYlxh();
				if(eylxh.contains("+")){
					testid = eylxh.substring(0, eylxh.indexOf("+"));
				}else {
					testid = eylxh;
				}
				sample = sampleManager.getBySfsb(e.getJzkh(), testid, e.getSfsb());
				boolean issame = true;
				if(sample!=null){
					//如果有项目组合，判断组合项目是否一致
					String sylxh = sample.getYlxh();
					if(eylxh.split("+").length != sylxh.split("+").length)
						issame = false;
					for(String s : sylxh.split("+")){
						if(!eylxh.contains(s))
							issame = false;
					}
				}
				
				if(issame){
					sample.setSampleNo(sampleno);
					process = processManager.getBySampleId(sample.getId());
					process.setExecutetime(executetime);
					process.setExecutor(user.getUsername());
					sample.setYlxh(eylxh);
					process.setReceiver(user.getUsername());
				}
				else{
					sample = new Sample();
					sample.setStayHospitalMode(stayhospitalmode);
					sample.setPatientId(e.getJzkh());
					sample.setSectionId(labdepart);
					sample.setDiagnostic(diagnostic);
					sample.setSampleType(sampletype);
					sample.setCycle(cycle);
					sample.setFee(String.valueOf(fee));
					sample.setFeestatus("6");
					sample.setRequestMode(requestmode);
					sample.setInspectionName(examinaim);
					sample.setYlxh(eylxh);
					
					process.setRequesttime(e.getKdsj());
					process.setRequester(e.getSjysgh());
					process.setExecutetime(executetime);
					process.setExecutor(user.getUsername());
					process.setReceivetime(receivetime);
					process.setReceiver(user.getUsername());
					
				}
				sampleManager.save(sample);
				processManager.save(process);
			}
			
		}
		
		return null;
	}
	
	//生成样本号
	public String getautoSampleno(int nextDay,String lab,int type){
		String sampleno="";
		String groupId="";
		Date sampleDate = new Date();//需要生成的样本号日期
		Date today = new Date(); //数据库中记录的日期
		int hm = Integer.parseInt(hhmm.format(sampleDate));
		SampleNoBuilder s = sampleNoBuilderManager.getByLab(lab);
		
		int sampleNo = 0;
		if(nextDay>0){
			Calendar c = Calendar.getInstance();
			c.setTime(sampleDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+nextDay);
			sampleDate = c.getTime();
			today = s.getNextday();
			type = type + 10*nextDay;
			//判断数据库中日期是否正确
			if(ymd.format(today).equals(ymd.format(sampleDate))){
				try {
					s=initBuilder(s,new Date(),sampleDate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//初始化数据库
			}
		}
		else{
			today = s.getNow();
			if(ymd.format(today).equals(ymd.format(sampleDate))){
				try {
					s=initBuilder(s,sampleDate,sampleDate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//初始化数据库
			}
		}
		
		
		
		if(lab.equals("1300600") && hm>=830 && type ==1 )
			type =2;
		//根据type选择样本号 >900则type+1
		boolean isnext = false;
		do{
			//添加处理办法
			switch (type) {
			case 1:
				sampleNo = s.getSampleNo1();
				groupId = s.getGroupId1();
				break;
			case 2:
				sampleNo = s.getSampleNo2();
				groupId = s.getGroupId2();
				break;

			default:
				break;
			}
			if(sampleNo>=900){
				type+=1;
				isnext=true;
			}
		}while(isnext);
		
		boolean exsitSampleno = false;
		
		do{
			//数据库sampleno++
			sampleNoBuilderManager.updateSampleNo(lab, type);
			//取groupid，sampleNo
			
			
			if(sampleNo > 0){
				if(groupId.trim().length()==3)
					sampleno = ymd.format(sampleDate)+groupId+sampleNo;
				else
					sampleno="0";
			}
			if(!sampleno.substring(sampleno.length()-4).equals("000") && sampleno.trim().length()==14){
				//应该查labordr表 以后修改
				exsitSampleno = sampleManager.existSampleNo(sampleno);//查看数据库中是否有该样本号记录
			}
			else {
				exsitSampleno=false;
			}
		}while(exsitSampleno);
		
		return sampleno;
		
	}
	
	public SampleNoBuilder initBuilder(SampleNoBuilder s,Date today,Date nextday) throws Exception{
		s.setSampleNo1(0);
		s.setSampleNo2(0);
		s.setSampleNo3(0);
		s.setSampleNo4(0);
		s.setSampleNo11(0);
		s.setSampleNo12(0);
		s.setSampleNo13(0);
		s.setSampleNo14(0);
		s.setNow(ymd1.parse(ymd1.format(today)));
		s.setNextday(ymd1.parse(ymd1.format(nextday)));
		
		s = sampleNoBuilderManager.save(s);
		return s;
	}
	
	
	@RequestMapping(value = "/printBarcode*", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView printBarcoe(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView();
	}
	
	
	
	
	
	
	@Autowired
	private RMIService rmiService;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private YlxhManager ylxhManager;
	@Autowired
	private SampleNoBuilderManager sampleNoBuilderManager;
	@Autowired
	private ProcessManager processManager;

}
