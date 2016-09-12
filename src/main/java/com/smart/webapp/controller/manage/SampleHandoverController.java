package com.smart.webapp.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ReceivePoint;
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
		initPointMap();
		startTime = new Date();
		ModelAndView view = new ModelAndView();
		view.addObject("name", user.getName());
		view.addObject("pointList", pointList);
        return view;
    }
	
	private void initPointMap(){
		List<ReceivePoint> pointList = receivePointManager.getByType(0);
		for(ReceivePoint rp : pointList) {
			pointMap.put(rp.getCode(), rp.getLab());
		}
	}
	
	@RequestMapping(value = "/ajax/outsample*", method = RequestMethod.GET)
	@ResponseBody
	public String outSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initPointMap();
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
				if(sp.getSENDTIME() == null) {
					obj.put("type", 2);
					//更新本地process
					Process process = processManager.getBySampleId(doct);
					if(process==null){
						process = new Process();
						process.setSampleid(sp.getDOCTADVISENO());
					}
					process.setSender(operator);
					process.setSendtime(new Date());
					processManager.save(process);
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
					obj.put("type", 4);
				}
				rmiService.sampleOut(doct, operator);
					
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
		initPointMap();
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
					Process process = processManager.getBySampleId(doct);
					//这个时候如果本地process为空则不更新，等待同步器完成更新，否则会出现多条记录
					if(process==null){
						process = new Process();
						process.setSampleid(sp.getDOCTADVISENO());
					}
					process.setKsreceiver(operator);
					process.setKsreceivetime(new Date());
					processManager.save(process);
					
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
		
		Map<Long, SyncPatient> sMap = new HashMap<Long,SyncPatient>();
		List<Process> processes  = new ArrayList<Process>();
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
		
		int size = 0;
		if(type.equals("1")){//标本送出
			size = processManager.getReceiveListCount(sender, startTime, null);
			
		}
		DataResponse dataResponse = new DataResponse();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		processes = processManager.getSendList(sender, startTime, null,start,end>size?size:end);
		
		if(processes==null || processes.size()==0)
			return null;
		String sampleids = "";
		for(Process p : processes){
			sampleids += p.getSampleid()+",";
		}
		List<SyncPatient> syncPatients = rmiService.getByDoctadvisenos(sampleids.substring(0,sampleids.length()-1));
		for(SyncPatient s : syncPatients){
			if(s!=null)
				sMap.put(s.getDOCTADVISENO(), s);
		}
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(Process p : processes){
			SyncPatient s = sMap.get(p.getSampleid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctadviseno", p.getSampleid());
			if(s!=null){
				map.put("patientname", s.getPATIENTNAME());
				map.put("patientid", s.getPATIENTID());
				map.put("inspectionName", s.getEXAMINAIM());
				map.put("labdepartment", sectionUtil.getValue(s.getLABDEPARTMENT()));
			}
			map.put("requesttime",p.getRequesttime()==null?"":Constants.SDF.format(p.getRequesttime()));
			map.put("requester", p.getRequester());
			map.put("executetime", p.getExecutetime()==null?"":Constants.SDF.format(p.getExecutetime()));
			map.put("executor", p.getExecutor());
			map.put("sendtime", p.getSendtime()==null?"":Constants.SDF.format(p.getSendtime()));
			map.put("sender", p.getSender());
			map.put("ksreceivetime", p.getKsreceivetime()==null?"":Constants.SDF.format(p.getKsreceivetime()));
			map.put("ksreceiver", p.getKsreceiver());
			
			dataRows.add(map);
		}
		
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
		
	}
	
	
	@RequestMapping(value = "/receiveList*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getReceiveList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row*(page-1);
		int end = row * page;
		
		String receiver = request.getParameter("operator");
		String type = request.getParameter("type");
		
		Map<Long, SyncPatient> sMap = new HashMap<Long,SyncPatient>();
		List<Process> processes  = new ArrayList<Process>();
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
		
		int size = 0;
		if(type.equals("1")){//标本送出
			size = processManager.getReceiveListCount(receiver, startTime, null);
			
		}
		DataResponse dataResponse = new DataResponse();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		
		processes = processManager.getReceiveList(receiver, startTime, null,start,end>size?size:end);
		if(processes==null || processes.size()==0)
			return null;
		String sampleids = "";
		for(Process p : processes){
			sampleids += p.getSampleid()+",";
		}
		List<SyncPatient> syncPatients = rmiService.getByDoctadvisenos(sampleids.substring(0,sampleids.length()-1));
		for(SyncPatient s : syncPatients){
			if(s!=null)
				sMap.put(s.getDOCTADVISENO(), s);
		}
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(Process p : processes){
			SyncPatient s = sMap.get(p.getSampleid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctadviseno", p.getSampleid());
			if(s!=null){
				map.put("patientname", s.getPATIENTNAME());
				map.put("patientid", s.getPATIENTID());
				map.put("inspectionName", s.getEXAMINAIM());
				map.put("labdepartment", sectionUtil.getValue(s.getLABDEPARTMENT()));
			}
			map.put("requesttime",p.getRequesttime()==null?"":Constants.SDF.format(p.getRequesttime()));
			map.put("requester", p.getRequester());
			map.put("executetime", p.getExecutetime()==null?"":Constants.SDF.format(p.getExecutetime()));
			map.put("executor", p.getExecutor());
			map.put("sendtime", p.getSendtime()==null?"":Constants.SDF.format(p.getSendtime()));
			map.put("sender", p.getSender());
			map.put("ksreceivetime", p.getKsreceivetime()==null?"":Constants.SDF.format(p.getKsreceivetime()));
			map.put("ksreceiver", p.getKsreceiver());
			
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
