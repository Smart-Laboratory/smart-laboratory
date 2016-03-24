package com.smart.webapp.controller.pb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.model.pb.Arrange;
import com.smart.model.pb.WInfo;
import com.smart.service.ArrangeManager;
import com.smart.service.DayShiftManager;
import com.smart.service.WInfoManager;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.net.MalformedURLException;



@Controller
@RequestMapping("/pb/pbcx*")
public class PbcxController {
	
	@Autowired
	private WInfoManager wInfoManager;
	
	@Autowired
	private ArrangeManager arrangeManager;
	
	@Autowired
	private DayShiftManager dayShiftManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RMIService rmiService;
	
	private final static String pbexcelUrl = "/lab/temporaty";
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = null;
		if(request.getRemoteUser() != null)
			user = userManager.getUserByUsername(request.getRemoteUser());
		String yearAndMonth = request.getParameter("date");
		String section = request.getParameter("section");
		String type = request.getParameter("type");
		
		if(section == null || section == "") {
			if(user == null){
				ModelAndView view = new ModelAndView();
				view.addObject("size", 0);
				return view;
			}
			section = user.getLastLab();
		}
		if(type == null) {
			type = "1"; 
		}
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.MONTH, 1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		
		if(yearAndMonth != null){
			calendar.set(Calendar.YEAR, Integer.parseInt(yearAndMonth.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(yearAndMonth.substring(5,7))-1);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH)+1;
		}
		String tomonth = year + "-" + (month<10 ? "0" + month : month);		
		List<WInfo> wiList = wInfoManager.getBySection(section, type);
		if(wiList==null || wiList.size() == 0) {
			return new ModelAndView().addObject("size", 0).addObject("date", tomonth);
		}

		String arrString = "";
		int size;
		if(section.equals("1300000") && !type.equals("7")){
			List<Arrange> yArranges = arrangeManager.getArrangeByType("夜", tomonth);
			List<Arrange> lArranges = arrangeManager.getArrangeByType("良", tomonth);
			List<Arrange> wArranges = arrangeManager.getArrangeByType("外", tomonth);
			List<Arrange> hArranges = arrangeManager.getArrangeByType("海", tomonth);
			List<Arrange> rArranges = arrangeManager.getArrangeByType("入", tomonth);
			List<Arrange> bArranges = arrangeManager.getArrangeByType("生帮", tomonth);
			
			String[][] shifts = new String[8][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
			size = shifts.length;
			int j = 1;
	        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
	            try {
	            	
	                Date date = sdf1.parse(tomonth + "-" + j);
	                if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
	                	shifts[0][j] = "<th style='background:#7CFC00'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
	                }else{
	                	shifts[0][j] = "<th style='background:#7FFFD4'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
	                }
	            } catch (Exception e) {
	            	e.printStackTrace();	
	            }
	        }
	        shifts[0][0] = "<th style='background:#7FFFD4'>" + tomonth + "</th>";
	        
	        List<Arrange> arr = new ArrayList<Arrange>();
	        
	        for(int i = 1;i<8;i++){
	        	Map<String, String> arrMap = new HashMap<String,String>();
	        	String bc = "";
	    		switch (i) {
	    		case 1:
	    			bc = "夜";
	    			arr = yArranges;
	    			break;
	    		case 2:
	    			bc = "良";
	    			arr = lArranges;
	    			break;
	    		case 3:
	    			bc = "外9";
	    			arr = wArranges;
	    			break;
	    		case 4:
	    			bc = "外9下";
	    			arr = wArranges;
	    			break;
	    		case 5:
	    			bc = "入";
	    			arr = rArranges;
	    			break;
	    		case 6:
	    			bc = "海";
	    			arr = hArranges;
	    			break;
	    		case 7:
	    			bc = "生帮";
	    			arr = bArranges;
	    			break;
	    		default:
	    			bc = "";
	    			break;
	    		}
	    		if(arr==null||arr.size()<=0)
	    			continue;
	    		for(Arrange a: arr){
//	    			if(a.getShift().contains("休"))
//	    				continue;
	    			if(bc.equals("夜")){
	    				if(!a.getShift().contains("夜;") && !a.getShift().contains("夜生;") && !a.getShift().contains("夜临;")){
	    					continue;
	    				}
	    			}else if(!a.getShift().contains(bc+";")){
	    				continue;
	    			}
	    			String worker = "";
	    			if(a.getState()<5){
	    				worker = "<font style='color:red'>"+a.getWorker()+"</font>";
	    			}else {
						worker = a.getWorker();
					}
	    			if(arrMap.get(a.getDate().split("-")[2])!=null){
	    				arrMap.put(a.getDate().split("-")[2], worker+";<br>  "+arrMap.get(a.getDate().split("-")[2]));
	    			}
	    			else
	    				arrMap.put(a.getDate().split("-")[2], worker);
	    		}
	    		
	    		shifts[i][0] = "<th style='background:#7FFFD4'>"+bc+"</th>";
	    		for(int k =1; k<j;k++){
	    			String m = k<10? "0"+k : k+"";
	    			if(arrMap.containsKey(m))
	    				shifts[i][k] = "<td>"+arrMap.get(m)+"</td>";
	    			else
	    				shifts[i][k] = "<td></td>";
	    		}
	        	
	    		
	        }
	        for(int k=0; k<8; k++) {
	        	arrString += "<tr>";
	        	for(int l=0; l<j; l++) {
	        		arrString += shifts[k][l];
	        	}
	        	arrString += "</tr>";
	        }
			
		}else{
			String wiNames = "";
			int i=1;
			Map<Integer, String> map = new HashMap<Integer, String>();
			Map<String, Arrange> arrMap = new HashMap<String, Arrange>();
			for(WInfo wi : wiList) {
				map.put(i, wi.getName());
				wiNames = wiNames + "'" + wi.getName() + "',"; 
				i++;
			}
			List<Arrange> arrList = arrangeManager.getArrangerd(wiNames.substring(0, wiNames.length()-1), tomonth,5);
			if(arrList.size() == 0) {
				return new ModelAndView().addObject("size", 0).addObject("month", tomonth).addObject("section", section);
			}
			for(Arrange arr : arrList) {
				arrMap.put(arr.getKey2(), arr);
			}
			String[][] shifts = new String[i][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
			size = shifts.length;
			int j = 1;
	        for(; j <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
	            try {
	            	
	                Date date = sdf1.parse(tomonth + "-" + j);
	                shifts[0][j] = "<th style='background:#7FFFD4'>" + sdf3.format(date) + sdf2.format(date).replace("星期", "") + "</th>";
	            } catch (Exception e) {
	            	e.printStackTrace();	
	            }
	        }
	        shifts[0][0] = "<th style='background:#7FFFD4'>" + tomonth + "</th>";
	        for(int m=1;m<i;m++) {
	        	shifts[m][0] = "<th><a onclick=\"personal('"+ map.get(m) + "')\">" + map.get(m) + "</a></th>";
	        }
	        for(int k=1; k<i; k++) {
	        	for(int l=1; l<j; l++) {
	        		String name = map.get(k);
	        		Date date = sdf1.parse(tomonth + "-" + l);
	        		if (arrMap.get(name + "-" + l) == null) {
	        			shifts[k][l] = ""; //<td style='background:#7CFC00'>休</td>
	        		} else {
	        			shifts[k][l] = arrMap.get(name + "-" + l).getShift().replace(";", ";<br>");
	        		}
	        		if (sdf2.format(date).contains("六") || sdf2.format(date).contains("日")) {
	        			shifts[k][l] = "<td style='background:#7CFC00'>" + shifts[k][l] + "</td>";
	        		} else if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getShift().contains("公休")){
	        			shifts[k][l] = "<td  style='background:#FDFF7F;'>"+arrMap.get(name + "-" + l).getShift().replace("公休;", "").replace(";", ";<br>")+"</td>";
	        		}  
	        		else {
	        			shifts[k][l] = "<td>" + shifts[k][l] + "</td>";
	        		}
	        		if(arrMap.get(name + "-" + l) != null && arrMap.get(name + "-" + l).getState()<5){
	        			shifts[k][l] = shifts[k][l].replace("<td>", "<td style='background:#63B8FF'>");
	        			shifts[k][l] = shifts[k][l].replace("<td style='background:#7CFC00'>", "<td style='background:#63B8FF'>");
	        		}
	            }
	        }
	        
	        
	        for(int k=0; k<i; k++) {
	        	arrString += "<tr>";
	        	for(int l=0; l<j; l++) {
	        		arrString += shifts[k][l];
	        	}
	        	arrString += "</tr>";
	        }
		}
        ModelAndView view = new ModelAndView();
        view.addObject("section", section);
        view.addObject("month", tomonth);
        view.addObject("type", type);
        view.addObject("arrString", arrString);
        view.addObject("size", size);
		return view;
	}
	
	@RequestMapping(value = "/daochu*", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView daochu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String date = request.getParameter("date");
		String section = request.getParameter("section");
		String type = request.getParameter("type");
		
		if(date == "" || date == null )
			return null;
		if(section == "" || section ==null){
			section = "1300000";
		}
		if(type==null)
			type="1";
		
		Calendar calendar = Calendar.getInstance();
		if(date != null && date !=""){
			calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0,4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7))-1);
		}
		
		List<WInfo> wInfos = wInfoManager.getBySection(section,type);
		String[][] data = new String[wInfos.size()][calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
		String[] gh = new String[wInfos.size()];
		String[] ks = new String[wInfos.size()];
		int i=0;
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService);
		for(WInfo wInfo : wInfos){
			ks[i]= sectionUtil.getValue(wInfo.getSection());
			gh[i]=wInfo.getWorkid();
			data[i][0] = wInfo.getName();
			List<Arrange> arranges = arrangeManager.getPersonalArrange(wInfo.getName(), date);
			for(Arrange a : arranges){
				System.out.println(a.getDate()+a.getWorker());
				int day = Integer.parseInt(a.getDate().split("-")[2]);
				data[i][day]=a.getShift(); 
			}
			i++;
		}
		
		writeExcel(data,date,gh,ks);
		
		System.out.println("开始导出");
		
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition","attachment; " + "filename=newpb.xls");
		response.setHeader("Content-Type", "application/vnd.ms-excel");   
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(pbexcelUrl+"/pb.xls");
			FileInputStream fin = new FileInputStream(file);
			bis = new BufferedInputStream(fin);
			bos = new BufferedOutputStream(out);
			byte buff[] = new byte[1024];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			}   
		return new ModelAndView("../pb/pbcx");
	}

	public boolean writeExcel(String[][] data,String date,String[] gh,String[] ks) throws FileNotFoundException{
		// win下
//		 OutputStream os = new FileOutputStream("d:\\test.xls");
		
		//linux下
		File dir = new File(pbexcelUrl);
		dir.setWritable(true,false);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(pbexcelUrl+"/pb.xls");
		OutputStream os = new FileOutputStream(file);
		try
        {
           
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            //创建Excel工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("pb",0);
 
            //**************往工作表中添加数据*****************
            Label label = new Label(0, 0, "工号");
            Label label1 = new Label(2, 0, "姓名");
            Label label2 = new Label(1,0,"科室");
        	ws.addCell(label);
        	ws.addCell(label1);
        	ws.addCell(label2);
        	
        	int length = data[0].length -1;
            for(int i=1;i<=length;i++){
            	String s = date+"-"+i;
            	label = new Label(i+2, 0, s);
            	ws.addCell(label);
            }
            
            for(int j=0;j<gh.length;j++){
            	label = new Label(0, j+1, gh[j]);
            	ws.addCell(label);
            }
            
            for(int k=0;k<ks.length;k++){
            	label = new Label(1,k+1,ks[k]);
            	ws.addCell(label);
            }
                     
           for(int i=0;i<data.length;i++){
              for(int j=0;j<=length;j++){
              label = new Label(j+2,i+1,data[i][j]);
              ws.addCell(label);
              }
           }
                       //写入工作表
            wwb.write();
            wwb.close();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		
		
		
		return true;
	}
}