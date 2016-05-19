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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.Dictionary;
import com.smart.model.lis.Sample;
import com.smart.model.lis.CriticalRecord;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.TestResult;
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.webapp.util.PatientUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;

@Controller
@RequestMapping("/audit*")
public class GetPatientController extends BaseAuditController {
	private static final Log log = LogFactory.getLog(GetPatientController.class);
    
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
		Patient patient = patientManager.getByBlh(info.getPatientblh());
		Process process = processManager.getBySampleId(info.getId());
		CriticalRecord cr = criticalRecordManager.getBySampleId(info.getId());
		List<TestResult> list = testResultManager.getTestBySampleNo(info.getSampleNo());
		
		if(patient == null){
			return null;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		
		if (info != null) {
			map.put("id", info.getId());
			map.put("name", patient.getPatientName());
			map.put("age", String.valueOf(patient.getAge()));
			if(info.getInspectionName() != null) {
				map.put("examinaim", info.getInspectionName());
			} else {
				map.put("examinaim", "");
			}
			map.put("mode", info.getRequestMode());
			map.put("stayhospitalmode", info.getStayHospitalMode()==1 ? "门诊":"住院");
			map.put("diagnostic", info.getDiagnostic());
			if(diagMap.containsKey(info.getDiagnostic())) {
				map.put("diagnosticKnow", diagMap.get(info.getDiagnostic()));
			} else {
				map.put("diagnosticKnow", "");
			}
			map.put("section", sectionutil.getValue(info.getHosSection()));

			String note = info.getNotes();
			Set<String> testIds = new HashSet<String>();
			int size = list.size();
			for (TestResult t : list) {
				testIds.add(t.getTestId());
				if (t.getEditMark() == 7) {
					size--;
				}
			}
			if (info.getAuditStatus() == Constants.STATUS_UNPASS
					&& !StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
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
			map.put("description", info.getDescription());
			map.put("mark", info.getAuditMark());
			map.put("sex", patient.getSexValue());
			map.put("hasImages", info.getHasimages() == 0 ? false : true);
			map.put("blh", info.getPatientblh());
			
			String code = info.getSampleNo().substring(8, 11);
			map.put("requester", "");
			map.put("isOverTime", false);
			map.put("requester", process.getRequester());
			if(slgiMap.containsKey(code)) {
				long exceptTime = slgiMap.get(code) * 60 * 1000;
				long df = new Date().getTime() - process.getReceivetime().getTime();
				if (info.getAuditStatus()<5 && df>exceptTime) {
					map.put("isOverTime", true);
				}
			}
			map.put("type",
					SampleUtil.getInstance().getSampleList(dictionaryManager).get(String.valueOf(info.getSampleType())));
			if(cr != null) {
				map.put("dgFlag", cr.getCriticalDealFlag());
				map.put("dgInfo", cr.getCriticalDeal());
				String dealTimeStr = "";
				if (cr.getCriticalDealTime() != null) {
					dealTimeStr = Constants.SDF.format(cr.getCriticalDealTime());
				}
				map.put("dgTime", dealTimeStr);
			}
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
	
	private String getItemStr(String id) {
		String result = "";
		Long ID = Long.parseLong(id.substring(1));
		if (id.startsWith("P")) {
			Dictionary lib = PatientUtil.getInstance().getInfo(ID, dictionaryManager);
			result = lib.getValue();
		} else {
			Item item = itemManager.get(ID);
			String testName = item.getIndex().getName();
			String value = item.getValue();
			if (value.contains("||")) {
				return testName + value.replace("||", "或");
			} else if (value.contains("&&")) {
				return testName + value.replace("&&", "且");
			}
			result = testName + value;
		}
		return result;
	}

	private StringBuilder getItem(JSONObject root, StringBuilder sb) {
		try {
			if ("and".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 并 ");
					}
				}
			} else if ("or".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 或 ");
					}
				}
				sb.append(")");
			} else if ("not".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("非(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
				}
				sb.append(")");
			} else {
				sb.append(getItemStr(root.get("id").toString()));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return sb;

	}
}
