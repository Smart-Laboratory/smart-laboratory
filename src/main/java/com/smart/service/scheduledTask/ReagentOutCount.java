package com.smart.service.scheduledTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.Constants;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.service.reagent.OutManager;
import com.smart.service.reagent.ReagentManager;

@Service("reagentOutCount")
public class ReagentOutCount {
	
	@Autowired
	private OutManager outManager = null;
	
	@Autowired
	private ReagentManager reagentManager = null;

    public void run() {
    	System.out.println("开始出库统计。。。"); 
    	Date today = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(today);
    	calendar.add(Calendar.DAY_OF_MONTH, -1);
    	Date yesterday = calendar.getTime();
    	
    	//获取需要计算工作量的出库信息
    	List<Out> needCountList = outManager.getNeedCountList(Constants.DF2.format(yesterday) + " 06:00:00", Constants.DF2.format(today) + " 06:00:00");
    	Set<Long> rgIdSet = new HashSet<Long>();
    	for(Out o : needCountList) {
    		rgIdSet.add(o.getRgId());
    	}
    	String rgids = rgIdSet.toString();
    	List<Reagent> reagentList = reagentManager.getByIds(rgids.substring(1, rgids.length()-1));
    	Map<Long, Reagent> rMap = new HashMap<Long, Reagent>();
    	for(Reagent r : reagentList) {
    		rMap.put(r.getId(), r);
    	}
    	for(Out o : needCountList) {
    		outManager.updateTestnum(o.getLab(), rMap.get(o.getRgId()).getTestname(), o.getRgId(), o.getOutdate());
    	}
    	System.out.println("出库统计完成。。。。"); 
    }
}
