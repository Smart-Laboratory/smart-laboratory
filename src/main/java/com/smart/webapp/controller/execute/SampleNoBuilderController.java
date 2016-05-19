package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.spi.InitialContextFactoryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.lis.ProcessManager;
import com.smart.model.execute.SampleNoBuilder;

import net.sf.ehcache.search.expression.And;

@Controller
public class SampleNoBuilderController {

	private SimpleDateFormat hhmm = new SimpleDateFormat("hhmm");
	private SimpleDateFormat dd = new SimpleDateFormat("E");
	private SimpleDateFormat ymd = new SimpleDateFormat("YYYYMMDD");
	
	
	
	private String builderSampleNo(Sample sample, String labdepartment, int requestMode, Date executeTime){
		double ll_time = Double.parseDouble(hhmm.format(executeTime).substring(0, 2))+ Double.parseDouble(hhmm.format(executeTime).substring(2, 4))/60;
		String ll_day = dd.format(executeTime);
		System.out.println(ll_time+ll_day);
		
		String sampleNo = "0";
		int stayHospitalModel =Integer.parseInt(sample.getStayHospitalModelValue());
		int count = 1;
		int nextday=0;
		Process process = processManager.getBySampleId(sample.getId());
		while (count > 0) {
			if (requestMode == 1){
				sample.setSectionId("1300300");
				sampleNo="0";
				
				process.setReceivetime(executeTime);
			}
			else{
				if(labdepartment.contains("1300600")){
					if(!ll_day.contains("日")){
						if(ll_time<14 && stayHospitalModel >0 ){
							sampleNo = getautoSampleno(nextday,labdepartment,stayHospitalModel);
						}
					}
				}
				else if(labdepartment.contains("13007")){
					if(!ll_day.contains("日") && (ll_time >5.1 && ll_time<10) && stayHospitalModel >0 ){
						sampleNo = getautoSampleno(nextday,labdepartment,stayHospitalModel);
					}
				}
				else if(labdepartment.contains("13001")){
					if(!ll_day.contains("日") && (ll_time >6.1 && ll_time<17.5) && stayHospitalModel >0){
						sampleNo = getautoSampleno(nextday,labdepartment,stayHospitalModel);
					}
				}
				else if(labdepartment.contains("1300501")){
					if(!ll_day.contains("日")){
						if(ll_time>13)
							nextday = 1;
						if(stayHospitalModel>0)
							sampleNo= getautoSampleno(nextday,labdepartment,stayHospitalModel);
					}
				}
				if(labdepartment.contains("13001")){
					process.setReceivetime(executeTime);
				}
			}
			//判断样本好是否重复
			if(sampleNo.trim().length()==14){
				
			}
//				count = getSampleNoCount(sampleNo);
			else
				count = 0;
		}
		
		return sampleNo;
	}
	
	public String getautoSampleno(int nextDay,String lab,int type){
		String sampleno="";
	/*	Date sampleDate = new Date();//需要生成的样本号日期
		Date today = new Date(); //数据库中记录的日期
		int hm = Integer.parseInt(hhmm.format(sampleDate));
		SampleNoBuilder s = sampleNoBuilderManager.getByLab(lab);
		
		int sampleNo = 0;
		String sampleno="";
		if(nextDay>0){
			Calendar c = Calendar.getInstance();
			c.setTime(sampleDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
			sampleDate = c.getTime();
			today = s.getNextday();
			type = type + 10*nextDay;
		}
		else{
			today = s.getNow();
		}
		
		//判断数据库中日期是否正确
		if(today.getTime() != sampleDate.getTime()){
			initBuilder(lab);//初始化数据库
		}
		
		if(lab.equals("1300600") && hm>=830 && type ==1 )
			type =2;
		
		do{
			//添加处理办法
			type+=1;
			switch (type) {
			case 1:
				sampleNo = s.getSampleNo1();
				break;
			case 2:
				sampleNo = s.getSampleNo2();
				break;

			default:
				break;
			}
		}while(sampleNo>=900);
		
		int count = 1;
		
		do{
			//数据库sampleno++
			updateSampleNo(type);
			//取groupid，sampleNo
			String groupId="";
			
			if(sampleNo > 0){
				if(groupId.trim().length()==3)
					sampleno = ymd.format(sampleDate)+groupId+sampleNo;
				else
					sampleno="0";
			}
			if(!sampleno.substring(sampleno.length()-4).equals("000") && sampleno.trim().length()==14){
				count = getCountBySampleNo(sampleno);//查看数据库中是否有该样本号记录
			}
			else {
				count=0;
			}
		}while(count > 0);*/
		
		return sampleno;
		
	}
	
	
	@Autowired
	private SampleNoBuilderManager sampleNoBuilderManager;
	@Autowired
	private ProcessManager processManager;
}
