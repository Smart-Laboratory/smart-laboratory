package com.smart.webapp.controller.lis.audit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.Diagnostic;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Ylxh;
import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.CriticalRecordManager;
import com.smart.service.lis.DiagnosticManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.service.lis.YlxhManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.service.RMIService;

public class BaseAuditController {
	
	@Autowired
    protected SampleManager sampleManager = null;
    
	@Autowired
    protected TestResultManager testResultManager = null;
    
	@Autowired
    protected final DictionaryManager dictionaryManager = null;
    
	@Autowired
    protected ItemManager itemManager = null;
    
	@Autowired
    protected ResultManager resultManager = null;
    
	@Autowired
    protected RuleManager ruleManager = null;
    
	@Autowired
    protected RMIService rmiService = null;

	@Autowired
    protected IndexManager indexManager = null;
    
	@Autowired
    protected CriticalRecordManager criticalRecordManager = null;

	@Autowired
    protected DiagnosticManager diagnosticManager = null;
	
	@Autowired
    protected YlxhManager ylxhManager = null;
	
	protected static HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
    protected Map<String, Index> idMap = new HashMap<String, Index>();
    protected Map<String, Integer> slgiMap = new HashMap<String, Integer>();
    protected Map<String, String> diagMap = new HashMap<String, String>();
    protected Map<Long, Ylxh> ylxhMap = new HashMap<Long, Ylxh>();

	protected synchronized void initMap() {
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
	}
	
	protected synchronized void initSLGIMap() {
		List<LabGroupInfo> list = rmiService.getLabGroupInfo();
		for (LabGroupInfo s : list) {
			slgiMap.put(s.getSpNo(), s.getExpectAvg());
		}
	}
	
	protected synchronized void initDiagMap() {
		List<Diagnostic> list = diagnosticManager.getAll();
		for (Diagnostic d : list) {
			diagMap.put(d.getDiagnostic(), d.getKnowledgename());
		}
	}
	
	synchronized private void initYLXHMap() {
		List<Ylxh> list = ylxhManager.getAll();
		for (Ylxh y : list) {
			ylxhMap.put(y.getYlxh(), y);
		}
	}
	
	protected Map<String, Integer> StringToMap(String ts) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(ts!=null){
			for (String s : ts.split(";")) {
				if (!"".equals(s) && s.contains(":")) {
					String[] array = s.split(":");
					map.put(array[0], Integer.parseInt(array[1]));
				}
			}
		}
		return map;
	}
	
	protected boolean sameSample(Sample info, Sample pinfo) {
		if (ylxhMap.size() == 0)
			initYLXHMap();
		if (!info.getSampleNo().equals(pinfo.getSampleNo())) {
			String ylxh = info.getYlxh();
			String ylxh2 = pinfo.getYlxh();
			if (!StringUtils.isEmpty(ylxh) && !StringUtils.isEmpty(ylxh2)) {
				if (ylxh.equals(ylxh2)) {
					return true;
				}
				Set<String> infoSet = new HashSet<String>();
				String[] xhs = ylxh.split("[+]");
				for (String xh : xhs) {
					if(xh.contains("[")){
						String[] linshi_xh = xh.split("\\[");
						xh = linshi_xh[0];
					}
					Ylxh y = ylxhMap.get(Long.parseLong(xh));
					if (y.getProfiletest() != null) {
						for (String s : y.getProfiletest().split(",")) {
							infoSet.add(s);
						}
					}
				}
				int size = infoSet.size();
				String[] xhs2 = ylxh2.split("[+]");
				for (String xh : xhs2) {
					if(xh.contains("[")){
						String[] linshi_xh = xh.split("\\[");
						xh = linshi_xh[0];
					}
					Ylxh y = ylxhMap.get(Long.parseLong(xh));
					if (y != null && y.getProfiletest() != null) {
						for (String s : y.getProfiletest().split(",")) {
							infoSet.add(s);
						}
					} else {
						return false;
					}
				}
				if(infoSet.size() - size == 0) {
					return true;
				} 
			}
		}
		return false;
	}
}
