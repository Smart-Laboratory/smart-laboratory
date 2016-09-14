package com.smart.webapp.controller.lis.audit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smart.service.lis.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.ContactInfor;
import com.smart.model.lis.Device;
import com.smart.model.lis.Diagnosis;
import com.smart.model.lis.LikeLab;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Ylxh;
import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.reagent.ReagentManager;
import com.smart.service.request.SFXMManager;
import com.smart.service.rule.BagManager;
import com.smart.service.rule.DesBagManager;
import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.rule.RuleManager;
import com.smart.webapp.util.HisIndexMapUtil;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.service.RMIService;

public class BaseAuditController {
	@Autowired
	protected ProfileTestManager proFileTestManager = null;
	
	@Autowired
    protected AuditTraceManager auditTraceManager = null;
	
	@Autowired
    protected SampleManager sampleManager = null;
    
	@Autowired
    protected TestResultManager testResultManager = null;
    
	@Autowired
    protected DictionaryManager dictionaryManager = null;
    
	@Autowired
    protected ItemManager itemManager = null;
    
	@Autowired
    protected ResultManager resultManager = null;
    
	@Autowired
    protected RuleManager ruleManager = null;
    
    @Autowired
    protected BagManager bagManager = null;
    
	@Autowired
    protected RMIService rmiService = null;

	@Autowired
    protected IndexManager indexManager = null;
    
	@Autowired
    protected CriticalRecordManager criticalRecordManager = null;

	@Autowired
    protected DiagnosisManager diagnosisManager = null;
	
	@Autowired
    protected YlxhManager ylxhManager = null;
	
	@Autowired
    protected UserManager userManager = null;
	
	@Autowired
    protected TaskManager taskManager = null;
	
	@Autowired
	protected CollectSampleManager collectSampleManager = null;
	
	@Autowired
    protected ProcessManager processManager = null;
	
	@Autowired
    protected LikeLabManager likeLabManager = null;
	
	@Autowired
	protected ContactManager contactManager = null;
	
	@Autowired
	protected ReagentManager reagentManager = null;

	@Autowired
	protected DeviceManager deviceManager = null;
	
	@Autowired
	protected SectionManager sectionManager = null;
	
	@Autowired
	protected SectionCodeManager sectionCodeManager = null;
	
	@Autowired
	protected SFXMManager sfxmManager = null;
	
	@Autowired
	protected DesBagManager desBagManager = null;

	@Autowired
	protected PatientManager patientManager = null;

	protected static HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
    protected Map<String, Index> idMap = new HashMap<String, Index>();
    protected Map<String, Integer> slgiMap = new HashMap<String, Integer>();
    protected Map<String, String> diagMap = new HashMap<String, String>();
    protected Map<Long, Ylxh> ylxhMap = new HashMap<Long, Ylxh>();
    protected Map<String, String> likeLabMap = new HashMap<String, String>();
    protected Map<String, String> deviceMap = new HashMap<String, String>();
    protected Map<String, ContactInfor> contactMap = new HashMap<String, ContactInfor>();

	protected synchronized void initMap() {
		List<Index> list = indexManager.getAll();
		for (Index t : list) {
			idMap.put(t.getIndexId(), t);
		}
	}
	
	protected synchronized void initDeviceMap() {
		List<Device> list = deviceManager.getAll();
		for (Device d : list) {
			deviceMap.put(d.getId(), d.getName());
		}
	}
	
	protected synchronized void initSLGIMap() {
		List<LabGroupInfo> list = rmiService.getLabGroupInfo();
		for (LabGroupInfo s : list) {
			slgiMap.put(s.getSpNo(), s.getExpectAvg());
		}
	}
	
	protected synchronized void initDiagMap() {
		List<Diagnosis> list = diagnosisManager.getAll();
		for (Diagnosis d : list) {
			diagMap.put(d.getDiagnosisName(), d.getKnowledgeName());
		}
	}
	
	protected synchronized void initYLXHMap() {
		List<Ylxh> list = ylxhManager.getAll();
		for (Ylxh y : list) {
			ylxhMap.put(y.getYlxh(), y);
		}
	}
	
	protected synchronized void initLikeLabMap() {
		List<LikeLab> list = likeLabManager.getAll();
		for (LikeLab ll : list) {
			likeLabMap.put(ll.getLab(), ll.getLikeLab());
		}
	}
	
	protected synchronized void initContactInforMap() {
		List<ContactInfor> list = contactManager.getAll();
		for(ContactInfor ci : list) {
			contactMap.put(ci.getWORKID(), ci);
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
	
	protected String setToString(Set<String> set) {

		String result = "";
		if (set.size() != 0) {
			for (String s : set) {
				if (!result.equals("")) {
					result += ",";
				}
				result += s;
			}
		}
		return result;
	}
	
	protected boolean sameSample(Sample info, Sample pinfo) {
		if (ylxhMap.size() == 0)
			initYLXHMap();
		if (!info.getSampleNo().equals(pinfo.getSampleNo())) {
			String ylxh = info.getYlxh();
			String ylxh2 = pinfo.getYlxh();
			if(ylxh == null || ylxh2 == null ||ylxh.isEmpty() || ylxh2.isEmpty()) {
				return false;
			}
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
					if (y!=null && y.getProfiletest() != null) {
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
