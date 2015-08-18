package com.smart.webapp.controller.lis.audit;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.lis.Diagnostic;
import com.smart.model.lis.Sample;
import com.smart.model.rule.Index;
import com.smart.service.lis.SampleManager;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/audit*")
public class GetPatientController {
	
	private Map<String, Index> idMap = new HashMap<String, Index>();
	private Map<String, Integer> slgiMap = new HashMap<String, Integer>();
	private Map<String, String> diagMap = new HashMap<String, String>();
	
    
    @Autowired
    private SampleManager sampleManager = null;
    
    @Autowired
    private RMIService rmiService = null;

	/**
	 * 获取样本中的病人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/patient*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPatientInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		if (id == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();
		
		if (slgiMap.size() == 0)
			initSLGIMap();
		
		if (diagMap.size() == 0)
			initDiagMap();

		Sample info = sampleManager.get(Long.parseLong(id));
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		if (info != null) {
			String code = info.getSampleNo().substring(8, 11);
			map.put("isOverTime", false);
			if(slgiMap.containsKey(code) && info.getReceivetime()!=null) {
				long exceptTime = slgiMap.get(code) * 60 * 1000;
				long df = new Date().getTime() - info.getReceivetime().getTime();
				if (info.getResultStatus()<5 && df>exceptTime) {
					map.put("isOverTime", true);
				}
			}
			map.put("id", info.getId());
			map.put("name", info.getPatientName());
			map.put("age", String.valueOf(info.getAge()));
			if(info.getExaminaim() != null) {
				String ex = info.getExaminaim().trim();
				/*if (ex.length() > 16) {
					ex = ex.substring(0, 16) + "...";
				}*/
				map.put("examinaim", ex);
			} else {
				map.put("examinaim", "");
			}
			map.put("mode", info.getRequestMode());
			map.put("diagnostic", info.getDiagnostic());
			if(diagMap.containsKey(info.getDiagnostic())) {
				map.put("diagnosticKnow", diagMap.get(info.getDiagnostic()));
			} else {
				map.put("diagnosticKnow", "");
			}
			map.put("section", sectionutil.getValue(info.getSection()));

			String note = info.getNotes();
			List<TestResult> testList = syncManager.getTestBySampleNo(info.getSampleNo());
			Set<String> testIds = new HashSet<String>();
			int size = testList.size();
			for (TestResult t : testList) {
				testIds.add(t.getTestId());
				if (t.getEditMark() == 7) {
					size--;
				}
			}
			if (info.getAuditStatus() == Constants.STATUS_UNPASS
					&& !StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					//Set<String> usedTestIds = new HashSet<String>();
					if (rule.getType() == 3 || rule.getType() == 4
							|| rule.getType() == 5 || rule.getType() == 6
							|| rule.getType() == 7) {
	 					String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
						String result = rule.getResultName();
						if (note != null && !note.isEmpty()) {
							note = note + "<br>" + reason + ", <font color='red'>" + result + "</font>";
						} else {
							note = reason + ", <font color='red'>" + result + "</font>";
						}
					}
				}
			}

			map.put("reason", note);
			map.put("mark", info.getAuditMark());
			map.put("sex", info.getSexValue());
			map.put("hasImages", info.getHasimages() == 0 ? false : true);
			if (StringUtils.isEmpty(info.getBlh())) {
				//List<Patient> list = syncManager.getPatientList("'" + info.getPatientId() + "'");
				List<com.zju.api.model.Patient> list = rmiService.getPatientList("'" + info.getPatientId() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", info.getBlh());
			}
			map.put("requester", info.getRequester());
			map.put("type",
					SampleUtil.getInstance().getSampleList(indexManager).get(String.valueOf(info.getSampleType())));
			map.put("dgFlag", info.getCriticalDealFlag());
			map.put("dgInfo", info.getCriticalDeal());
			String dealTimeStr = "";
			if (info.getCriticalDealTime() != null) {
				dealTimeStr = sdf.format(info.getCriticalDealTime());
			}
			map.put("dgTime", dealTimeStr);
			map.put("bed", info.getDepartBed());
			map.put("size", size);
			map.put("passReason", info.getPassReason());
			map.put("patientId", info.getPatientId());
			if(info.getAuditMark() == 4) {
				map.put("isLack", true);
			} else {
				map.put("isLack", false);
			}
			if(info.getAuditMark() == 6 && info.getAuditStatus() == 2) {
				map.put("isDanger", true);
			} else {
				map.put("isDanger", false);
			}
		}
		return map;
	}
	
	synchronized private void initSLGIMap() {
		List<LabGroupInfo> list = rmiService.getLabGroupInfo();
		for (LabGroupInfo s : list) {
			slgiMap.put(s.getSpNo(), s.getExpectAvg());
		}
	}
	
	synchronized private void initDiagMap() {
		List<Diagnostic> list = syncManager.getDiagnostic();
		for (Diagnostic d : list) {
			diagMap.put(d.getDIAGNOSTIC(), d.getKNOWLEDGENAME());
		}
	}
}
