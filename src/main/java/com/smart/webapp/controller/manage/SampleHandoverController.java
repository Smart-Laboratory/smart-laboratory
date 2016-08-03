package com.smart.webapp.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Process;
import com.smart.model.lis.Ward;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.WardManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.SyncPatient;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/sampleHandover*")
public class SampleHandoverController {

	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private WardManager wardManager = null;
	
	@Autowired
	private RMIService rmiService = null;
	
	@Autowired
	private ReceivePointManager receivePointManager = null;
	
	private Map<String, String> pointMap = new HashMap<String, String>();
	
	private Date startTime = new Date();

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<ReceivePoint> pointList = receivePointManager.getByType(0);
		for(ReceivePoint rp : pointList) {
			pointMap.put(rp.getCode(), rp.getLab());
		}
		startTime = new Date();
		ModelAndView view = new ModelAndView();
		view.addObject("name", user.getName());
		view.addObject("pointList", pointList);
        return view;
    }
	
	@RequestMapping(value = "/ajax/outsample*", method = RequestMethod.GET)
	@ResponseBody
	public String outSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject obj = new JSONObject();
		int isupdate = 0;
		try {
			long doct = Long.parseLong(request.getParameter("doct"));
			String operator = request.getParameter("operator");
			String lab = pointMap.get(operator.substring(operator.indexOf("(")+1, operator.indexOf(")")));
			SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
			
			SyncPatient sp = rmiService.getSampleByDoct(doct);
			if(sp == null) {
				obj.put("type", 1);
			} else {
				obj.put("exam", sp.getEXAMINAIM());
				obj.put("name", sp.getPATIENTNAME());
				obj.put("sex", sp.getSEX());
				obj.put("age", sp.getAge());
				obj.put("lab", sectionutil.getLabValue(sp.getLABDEPARTMENT()));
				String section = sectionutil.getValue(sp.getSECTION());
				obj.put("section", section);
				if(sp.getSTAYHOSPITALMODE() == 2) {
					obj.put("bed", sp.getDEPART_BED());
					if(section.contains("(")) {
						String[] array = section.split("\\(");
						section = array[1];
						section = section.replace(")", "");
						section = section.replace("楼", "");
						List<Ward> list = wardManager.getByWard(section);
						String type = "";
						String phone = "";
						if (list.size()>0) {
							for (Ward w : list) {
								type = type + w.getType() + " ";
								phone = phone + w.getPhone() + " ";
							}
						}
						obj.put("wardType", type);
						obj.put("wardPhone", phone);
					} else {
						obj.put("wardType", "");
						obj.put("wardPhone", "");
					}
					
				}
				obj.put("stayhospitalmode", sp.getSTAYHOSPITALMODE());
				obj.put("mode", sp.getREQUESTMODE());
				if(sp.getSENDTIME() == null) {
					obj.put("type", 2);
					isupdate=1;
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
					obj.put("type", 4);
				}
				if(isupdate==1){
					rmiService.sampleOut(doct, operator);
					//更新本地process
					Process process = processManager.getBySampleId(doct);
					if(process==null){
						process = new Process();
						process.setSampleid(doct);
						
					}
					process.setSender(operator);
					process.setSendtime(new Date());
					processManager.save(process);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			obj.put("type", 1);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
	@RequestMapping(value = "/ajax/sample*", method = RequestMethod.GET)
	@ResponseBody
	public String getRelativeTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject obj = new JSONObject();
		try {
			long doct = Long.parseLong(request.getParameter("doct"));
			String operator = request.getParameter("operator");
			String lab = pointMap.get(operator.substring(operator.indexOf("(")+1, operator.indexOf(")")));
			SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
			
			SyncPatient sp = rmiService.getSampleByDoct(doct);
			if(sp == null) {
				obj.put("type", 1);
			} else {
				obj.put("exam", sp.getEXAMINAIM());
				obj.put("name", sp.getPATIENTNAME());
				obj.put("sex", sp.getSEX());
				obj.put("age", sp.getAge());
				obj.put("lab", sectionutil.getLabValue(sp.getLABDEPARTMENT()));
				String section = sectionutil.getValue(sp.getSECTION());
				obj.put("section", section);
				if(sp.getSTAYHOSPITALMODE() == 2) {
					obj.put("bed", sp.getDEPART_BED());
					if(section.contains("(")) {
						String[] array = section.split("\\(");
						section = array[1];
						section = section.replace(")", "");
						section = section.replace("楼", "");
						List<Ward> list = wardManager.getByWard(section);
						String type = "";
						String phone = "";
						if (list.size()>0) {
							for (Ward w : list) {
								type = type + w.getType() + " ";
								phone = phone + w.getPhone() + " ";
							}
						}
						obj.put("wardType", type);
						obj.put("wardPhone", phone);
					} else {
						obj.put("wardType", "");
						obj.put("wardPhone", "");
					}
					
				}
				obj.put("stayhospitalmode", sp.getSTAYHOSPITALMODE());
				obj.put("mode", sp.getREQUESTMODE());
				if(sp.getKSRECEIVETIME() == null) {
					obj.put("type", 2);
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
					obj.put("type", 4);
				}
				rmiService.sampleReceive(doct, operator);
			}
		} catch(Exception e) {
			e.printStackTrace();
			obj.put("type", 1);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
	@RequestMapping(value = "/outList*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getOutList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row*(page-1);
		int end = row * page;
		
		String sender = request.getParameter("operator");
		String type = request.getParameter("type");
		
		List<Sample> samples = new ArrayList<Sample>();
		Map<Long, Sample> sMap = new HashMap<Long,Sample>();
		List<Process> processes  = new ArrayList<Process>();
		
		if(type.equals("1")){//标本送出
			processes = processManager.getOutList(sender, startTime);
			if(processes==null || processes.size()==0)
				return null;
			String sampleids = "";
			for(Process p : processes){
				sampleids += p.getSampleid()+",";
			}
			samples = sampleManager.getByIds(sampleids.substring(0,sampleids.length()-1));
			for(Sample s : samples){
				if(s!=null)
					sMap.put(s.getId(), s);
			}
		}
		DataResponse dataResponse = new DataResponse();
		int size = samples.size();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		samples = samples.subList(start, end>size?size:end);
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(Process p : processes){
			Sample s = sMap.get(p.getSampleid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctadviseno", p.getSampleid());
			if(s!=null){
				map.put("patientname", s.getPatientname());
				map.put("patientid", s.getPatientId());
				map.put("inspectionName", s.getInspectionName());
				map.put("labdepartment", s.getSectionId());
			}
			map.put("requesttime",p.getRequesttime()==null?"":Constants.SDF.format(p.getRequesttime()));
			map.put("requester", p.getRequester());
			map.put("executetime", p.getExecutetime()==null?"":Constants.SDF.format(p.getExecutetime()));
			map.put("executor", p.getExecutor());
			map.put("sendtime", p.getSendtime()==null?"":Constants.SDF.format(p.getSendtime()));
			map.put("sender", p.getSender());
			
			dataRows.add(map);
		}
		
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
		
	}
	
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private ProcessManager processManager;
	@Autowired
	private SectionManager sectionManager;
}
