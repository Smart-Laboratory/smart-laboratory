package com.smart.check;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.Ylxh;
import com.smart.webapp.util.IndexMapUtil;

public class LackCheck implements Check {

	private Map<Long, Ylxh> ylxhMap = null;
	private Map<String, String> idMap = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance(); //检验项映射
	
	public LackCheck(Map<Long, Ylxh> ylxhMap, Map<String, String> idMap) {
		this.ylxhMap = ylxhMap;
		this.idMap = idMap;
	}
	
	public boolean doCheck(Sample info, List<TestResult> list) {

		boolean result = true;
		Set<String> lackSet = new HashSet<String>();
		Set<String> overSet = new HashSet<String>();
		String ylxh = info.getYlxh();	// 医疗序号
		
		if (!StringUtils.isEmpty(ylxh)) {
			List<String> xhList = new ArrayList<String>();	//必做的检验项目列表
			List<String> xhList2 = new ArrayList<String>();	//可选的检验项目列表
			String[] xhs = ylxh.split("[+]");
			for (String xh : xhs) {
				if(xh.contains("[")){
					String[] linshi_xh = xh.split("\\[");
					xh = linshi_xh[0];
				}
				Ylxh y = ylxhMap.get(Long.parseLong(xh));
				if (y!= null && y.getProfiletest() != null) {
					for (String s : y.getProfiletest().split(",")) {
						xhList.add(s);
					}
				}
				
				if (y!= null && y.getProfiletest2() != null) {
					for (String s : y.getProfiletest2().split(",")) {
						xhList2.add(s);
					}
				}
				//List<String> list = ylxhMap.get(Long.parseLong(xh));
				//xhList.addAll(list);
			}

			// 提取testId的set集
			Set<String> testIdSet = new HashSet<String>();
			if (list != null) {
				for (TestResult r : list)
					if(idMap.containsKey(r.getTestId())) {
						testIdSet.add(r.getTestId());
					}
			} else {
				lackSet.addAll(xhList);
			}
			for (String xh : xhList) {
				if(!testIdSet.contains(xh)
						&& !testIdSet.contains(util.getValue(xh))) {
					if(idMap.containsKey(xh)) {
						lackSet.add(idMap.get(xh));
					} else {
						lackSet.add(xh);
					}
				}
			}
			
			for (String testid : testIdSet) {
				if(!xhList.contains(testid) && !xhList2.contains(testid)) {
					if(idMap.containsKey(testid)) {
						overSet.add(idMap.get(testid));
					} else {
						overSet.add(testid);
					}
				}
			}
		}
		
		if(lackSet.size()>0 || overSet.size()>0) {
			result = false;
			String lack = "";
			String over = "";
			if (lackSet.size()>0) {
				lack = "少做:" + CheckUtil.toString(lackSet) + "<br/>";
			}
			if (overSet.size()>0) {
				over = "多做:" + CheckUtil.toString(overSet) + "<br/>";
			}
			info.setAuditMark(LACK_MARK);
			info.setAuditStatus(UNPASS);
			if(info.getNotes()!=null 
					&& !info.getNotes().equals("")){
				info.setNotes(info.getNotes() + "<br/>" + lack + over);
			} else {
				info.setNotes(lack + over);
			}
		}
		
		return result;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {
		return false;
	}

}