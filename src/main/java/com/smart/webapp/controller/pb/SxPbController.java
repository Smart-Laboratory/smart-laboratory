package com.smart.webapp.controller.pb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.pb.SxArrange;
import com.smart.service.SxArrangeManager;

@Controller
@RequestMapping("/pb/sxpb*")
public class SxPbController {

	@RequestMapping(value = "/submit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String month = request.getParameter("date");
		String text = request.getParameter("text");
		if(text.isEmpty())
			return true;
		List<SxArrange> sList = new ArrayList<SxArrange>();//需要保存或更新的排班信息
		Map<String, SxArrange> sxMap = new HashMap<String,SxArrange>();
		
		
		List<SxArrange> sxArranges = sxArrangeManager.getByMonth(month);
		System.out.println(sxArranges.size());
		if(sxArranges != null && !sxArranges.isEmpty()){
			for(SxArrange a: sxArranges){
				sxMap.put(a.getWorker()+":"+a.getWeek(), a);
			}
		}
		
		for(String arr : text.split(",")){
			
			String[] s = arr.split(":");
			String nameweek = s[0]+":"+s[2];
			SxArrange sxArrange = null;
			
			if(!sxMap.isEmpty() && sxMap.containsKey(nameweek)){
				sxArrange = sxMap.get(nameweek);
			}else if(s.length>=4){
				sxArrange = new SxArrange();
			}else{
				continue;
			}
			sxArrange.setWorker(s[0]);
			sxArrange.setMonth(s[1]);
			sxArrange.setWeek(s[2]);
			if(s.length==3){
				sxArrange.setSection("");
			}else{
				sxArrange.setSection(s[3]);
			}
			sList.add(sxArrange);
		}
		
		sxArrangeManager.saveAll(sList);
		
		
		return true;
	}
	
	@Autowired
	private SxArrangeManager sxArrangeManager;
}
