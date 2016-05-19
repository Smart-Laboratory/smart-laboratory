package com.smart.webapp.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.Patient;
import com.smart.model.lis.Sample;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.PatientManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/sampleQuery*")
public class SearchController {
	
	private SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = "";
		String department = operator.getDepartment();
		Map<String, String> depart = new HashMap<String, String>();
		if (operator.getLastLab() != null) {
			lab = operator.getLastLab();
		}
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, sectionutil.getValue(s));
				if (StringUtils.isEmpty(lab)) {
					lab = s;
				}
			}
		}
		depart.put("1300000", "所有科室");
		Map<String, String> sampleTypes = SampleUtil.getInstance().getSampleList(dictionaryManager);
		
		request.setAttribute("departList", depart);
		request.setAttribute("sampleTypes", sampleTypes);
		request.setAttribute("lastlab", lab);
        return new ModelAndView();
    }
	
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
		String text = request.getParameter("text");
		int type = Integer.parseInt(request.getParameter("type"));
		int mode=0;
		if(request.getParameter("stayhospitalmode")!=null)
			mode = Integer.parseInt(request.getParameter("stayhospitalmode"));
		String fromDate = request.getParameter("from");
		String toDate = request.getParameter("to");
		String sectionId = request.getParameter("section");
		String sampleType = request.getParameter("sampleType");
		DataResponse dataResponse = new DataResponse();
		
		if(text.isEmpty())
			return null;
		
		List<Sample> samples = new ArrayList<Sample>();
		int size = 0;
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = operator.getDepartment();
		
		
		//获取样本信息
		switch (type) {
		case 1:
			if(text.length()<8)
				break;
			String code = operator.getLabCode();
			if(!sectionId.equals("1300000"))
				lab = sectionId;
			samples = sampleManager.getSampleList(text, lab, 0, -3, code, 0, 0);
			break;

		case 2:
			if(StringUtils.isNumeric(text))
				samples.add(sampleManager.get(Long.parseLong(text)));
			break;
		case 3:
			samples = sampleManager.getSampleByPatientName(fromDate, toDate, text);
			break;
		case 4:
			samples = sampleManager.getSampleBySearchType(fromDate, toDate, "patientblh", text);
			break;
		case 5:
			samples = sampleManager.getSampleBySearchType(fromDate, toDate, "patientId", text);
			break;
		default:
			break;
		}
		if(samples==null || samples.size()==0){
			return null;
		}
		if(mode!=0){
			for(int i=0;i<samples.size();i++){
				if(samples.get(i).getStayHospitalMode()!=mode){
					samples.remove(i);
					i--;
				}
			}
		}
		if(type!=1 && !sectionId.equals("1300000")){
			for(int i=0;i<samples.size();i++){
				if(!samples.get(i).getSectionId().equals(sectionId)){
					samples.remove(i);
					i--;
				}
			}
		}
		if(!sampleType.equals("#")){
			for(int i=0;i<samples.size();i++){
				if(!samples.get(i).getSampleType().equals(sampleType)){
					samples.remove(i);
					i--;
				}
			}
		}
		size=samples.size();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		if(size-1>end)
			samples = samples.subList(start, end);
		else if(size>start)
			samples = samples.subList(start, size-1);
		else {
			return null;
		}
		
		String hisBlh = "";
		for(Sample sample : samples) {
			hisBlh += "'" + sample.getPatientblh() + "',";
		}
		Map<String, String> sMap = SampleUtil.getInstance().getSampleList(dictionaryManager);
		List<Patient> patientList = patientManager.getHisPatient(hisBlh.substring(0, hisBlh.length()-1));
		final Map<String, Patient> hisPatientMap = new HashMap<String, Patient>();
		for(Patient p : patientList) {
			hisPatientMap.put(p.getBlh(), p);
		}
		SectionUtil sectionutil = SectionUtil.getInstance(rmiService);
		
		
		for(Sample info :samples) {
			Patient patient = hisPatientMap.get(info.getPatientblh());
			String section = sectionutil.getValue(info.getSectionId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", info.getId());
			map.put("sample", "<a href='../manage/patientList?blh="+patient.getBlh()+"'>"+info.getSampleNo()+"</a>");
			map.put("status", info.getAuditStatusValue());
			map.put("inspection", info.getInspectionName());
			map.put("diagnostic", info.getDiagnostic());
			map.put("name",patient.getPatientName());
			map.put("blh",patient.getBlh());
			map.put("sex",patient.getSexValue());
			map.put("birthday", yyyyMM.format(patient.getBirthday()));
			map.put("stayHospitalMode",info.getStayHospitalModelValue());
			map.put("section",section);
			map.put("patientid",info.getPatientId());
			map.put("sampleType", sMap.get(info.getSampleType()));
			if (info.getSampleStatus()>=5) {
				if (info.getIswriteback() == 1) {
					map.put("lisPass", "<font color='red'>" + "已打印" + "</font>");
				} else {
					map.put("lisPass", "已打印");
				}
			} else {
				map.put("lisPass", "");
			}
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		
		return dataResponse;
	}
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private PatientManager patientManager;
	@Autowired
	private SectionManager sectionManager;
	@Autowired
	private DictionaryManager dictionaryManager;
	@Autowired
	private RMIService rmiService;
}
