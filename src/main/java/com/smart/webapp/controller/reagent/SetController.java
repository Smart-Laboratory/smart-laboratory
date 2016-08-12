package com.smart.webapp.controller.reagent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.reagent.Reagent;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("/reagent/set*")
public class SetController extends ReagentBaseController{
	
	private final static String pbexcelUrl = "/lab/temporaty";
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }
	
	@RequestMapping(method = RequestMethod.GET, value="/export*")
    public ModelAndView export() throws Exception {
		List<Reagent> reagents = reagentManager.getAll();
		
		writeExcel(reagents);
				
				
				
				
				
				
				
				
				
				
				
        return new ModelAndView();
    }

	private void writeExcel(List<Reagent> reagents) throws FileNotFoundException{
		File dir = new File(pbexcelUrl);
		dir.setWritable(true,false);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(pbexcelUrl+"/reagents.xls");
		OutputStream os = new FileOutputStream(file);
		try
        {
           
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            //创建Excel工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("reagents",0);
            
            Field[] fields = Reagent.class.getDeclaredFields();
            int i=0;
            for(Field field : fields){
            	field.setAccessible(true);
            	String name = field.getName();
            	Label label = new Label(i, 0, name);
            	ws.addCell(label);
            	i++;
            }
            
            for(int j=0; j<reagents.size();j++){
            	Reagent r = reagents.get(j);
            }
            
            
            //**************往工作表中添加数据*****************
            Label label = new Label(0, 0, "工号");
            Label label1 = new Label(2, 0, "姓名");
            Label label2 = new Label(1,0,"科室");
        	ws.addCell(label);
        	ws.addCell(label1);
        	ws.addCell(label2);
        	
        	
                       //写入工作表
            wwb.write();
            wwb.close();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		
		
		
		return;
	}
}
