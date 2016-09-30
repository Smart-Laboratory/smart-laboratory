package com.smart.webapp.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Section;
import com.smart.model.lis.Process;
import com.smart.model.lis.Ward;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.WardManager;
import com.smart.util.ConvertUtil;
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
		List<String> sendList = rmiService.getSendListNo(Constants.DF2.format(new Date()), Constants.DF2.format(new Date()), user.getLastLab());
		view.addObject("sendList",sendList);
		//获取科室map
		List<Section> sList = sectionManager.getAll();
		view.addObject("labs",sList);
		return view;
	}

	private void initPointMap(){
		List<ReceivePoint> pointList = receivePointManager.getByType(0);
		for(ReceivePoint rp : pointList) {
			pointMap.put(rp.getCode(), rp.getLab());
		}
	}

	@RequestMapping(value = "/getSendListNo*", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSendListNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String from = ConvertUtil.null2String(request.getParameter("from"));
		String to = ConvertUtil.null2String(request.getParameter("to"));
		String section = ConvertUtil.null2String(request.getParameter("section"));

		List<String> sendList = rmiService.getSendListNo(from, to, section);

		return sendList;
	}


	@RequestMapping(value = "/ajax/outsample*", method = RequestMethod.GET)
	@ResponseBody
	public String outSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initPointMap();
		JSONObject obj = new JSONObject();
		try {
			long doct = Long.parseLong(request.getParameter("doct"));
			String operator = request.getParameter("operator");
			String sendList = request.getParameter("sendList");
			String queryCheck = request.getParameter("queryCheck");
			String lab = pointMap.get(operator.substring(operator.indexOf("(")+1, operator.indexOf(")")));
			SectionUtil sectionutil = SectionUtil.getInstance(sectionManager);

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
					/*Process process = processManager.getBySampleId(doct);
					if(process==null){
						process = new Process();
						process.setSampleid(sp.getDOCTADVISENO());
					}
					process.setSender(operator);
					process.setSendtime(new Date());
					process.setSendList(sendList);
					processManager.save(process);*/
				} else {
					obj.put("type", 3);
				}
//				System.out.println(lab);
//				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
//					obj.put("type", 4);
//				}
				if(!queryCheck.equals("true"))
					rmiService.sampleOut(doct, operator,sendList);

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
			SectionUtil sectionutil = SectionUtil.getInstance(sectionManager);

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
//				if(sp.getLABDEPARTMENT() == null || !lab.contains(sp.getLABDEPARTMENT())) {
//					obj.put("type", 4);
//				}
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
		Long docid = ConvertUtil.getLongValue(request.getParameter("doct"));
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");

		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row*(page-1);
		int end = row * page;

		String sendListNo = request.getParameter("sendList");
		String from = ConvertUtil.null2String(request.getParameter("from"));
		String to = ConvertUtil.null2String(request.getParameter("to"));
		String section = ConvertUtil.null2String(request.getParameter("receiveLab"));
		int allCheck = ConvertUtil.getIntValue(request.getParameter("allCheck"));
		int queryCheck = ConvertUtil.getIntValue(request.getParameter("queryCheck"));

		if(allCheck!=1 && (sendListNo==null || sendListNo.isEmpty()) )
			return null;

		SectionUtil sectionUtil = SectionUtil.getInstance(sectionManager);
		List<SyncPatient> outList = new ArrayList<SyncPatient>();
		int size = 0;
		if(queryCheck == 1){
			size=1;page=1;start=0;end=1;
			SyncPatient sp = rmiService.getSampleByDoct(docid);
			if(sp==null)
				return null;
			outList.add(sp);

		}else{
			//List<SyncPatient> getSendList(String from, String to, String section, String sendListNo, int stayhospitalmode,int start,int end);
			if(allCheck == 1){
				outList = rmiService.getSendList(from, to, section, null, 1, start, end);
			}else{
				outList = rmiService.getSendList(from, to, section, sendListNo, 1, start, end);
			}
		}
		size=outList.size();

		DataResponse dataResponse = new DataResponse();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);


		if(outList==null || outList.size()==0)
			return null;

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(SyncPatient p : outList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctadviseno", p.getDOCTADVISENO());
			map.put("patientname", p.getPATIENTNAME());
			map.put("patientid", p.getPATIENTID());
			map.put("inspectionName", p.getEXAMINAIM());
			map.put("labdepartment", sectionUtil.getValue(p.getLABDEPARTMENT()));
//			map.put("requesttime",p.getRequesttime()==null?"":Constants.SDF.format(p.getRequesttime()));
//			map.put("requester", p.getREQUESTER());
			map.put("computername", p.getCOMPUTERNAME());
			map.put("executetime", p.getEXECUTETIME()==null?"":Constants.SDF.format(p.getEXECUTETIME()));
			map.put("executor", p.getEXECUTOR());
			map.put("sendtime", p.getSENDTIME()==null?"":Constants.SDF.format(p.getSENDTIME()));
			map.put("sender", p.getSENDER());
			map.put("ksreceivetime", p.getKSRECEIVETIME()==null?"":Constants.SDF.format(p.getKSRECEIVETIME()));
			map.put("ksreceiver", p.getKSRECEIVER());

			dataRows.add(map);
		}

		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;

	}
	/**
	 * 未送出列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/waitOutList*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getWaitOutList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");

		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row*(page-1);
		int end = row * page;

		String from = ConvertUtil.null2String(request.getParameter("from"));
		String to = ConvertUtil.null2String(request.getParameter("to"));
		String section = ConvertUtil.null2String(request.getParameter("receiveLab"));

		if(from==null || to==null )
			return null;

		List<SyncPatient> sPatients  = new ArrayList<SyncPatient>();
		SectionUtil sectionUtil = SectionUtil.getInstance(sectionManager);

		int size = 0;
		//int getSendListCount(int isOut, int stayhospitalmode, String from, String to, String section);
		size = rmiService.getSendListCount(0, 1, from, to, section);
		//List<SyncPatient> getWaitSendList(String from, String to, String section, int stayhospitalmode,int start,int end);
		sPatients = rmiService.getWaitSendList(from, to, section, 1, start, end);

		DataResponse dataResponse = new DataResponse();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);


		if(sPatients==null || sPatients.size()==0)
			return null;

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		for(SyncPatient p : sPatients){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctadviseno", p.getDOCTADVISENO());
			map.put("patientname", p.getPATIENTNAME());
			map.put("patientid", p.getPATIENTID());
			map.put("inspectionName", p.getEXAMINAIM());
			map.put("labdepartment", sectionUtil.getValue(p.getLABDEPARTMENT()));
			map.put("computername", p.getCOMPUTERNAME());
			map.put("executetime", p.getEXECUTETIME()==null?"":Constants.SDF.format(p.getEXECUTETIME()));
			map.put("executor", p.getEXECUTOR());
			map.put("sendtime", p.getSENDTIME()==null?"":Constants.SDF.format(p.getSENDTIME()));
			map.put("sender", p.getSENDER());

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
		SectionUtil sectionUtil = SectionUtil.getInstance(sectionManager);

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
	/**
	 * 送出清单打印
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendListPrint*", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getSendListPrintModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sendListNo = ConvertUtil.null2String(request.getParameter("sendListNo"));
		String from = ConvertUtil.null2String(request.getParameter("from"));
		String to = ConvertUtil.null2String(request.getParameter("to"));
		String section = ConvertUtil.null2String(request.getParameter("receiveLab"));
		int allCheck = ConvertUtil.getIntValue(request.getParameter("allCheck"));

		ModelAndView view = new ModelAndView("print/sendList");
		view.addObject("sendListNo",sendListNo);
		view.addObject("section",SectionUtil.getInstance(sectionManager).getValue(section));
		if(allCheck==1){
			view.addObject("from",from);
			view.addObject("to",to);
			view.addObject("allCheck",allCheck);
		}

		return view;
	}

	@RequestMapping(value = "/ajax/sendListPrintData*", method = RequestMethod.GET)
	@ResponseBody
	public String getSendListPrintData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sendListNo = request.getParameter("sendListNo");
		String from = ConvertUtil.null2String(request.getParameter("from"));
		String to = ConvertUtil.null2String(request.getParameter("to"));
		String section = ConvertUtil.null2String(request.getParameter("receiveLab"));
		int allCheck = ConvertUtil.getIntValue(request.getParameter("allCheck"));

		if(allCheck!=1 && (sendListNo==null || sendListNo.isEmpty()) )
			return null;

		List<SyncPatient> outList = new ArrayList<SyncPatient>();
		//List<SyncPatient> getSendList(String from, String to, String section, String sendListNo, int stayhospitalmode,int start,int end);
		if(allCheck == 1){
			outList = rmiService.getSendList(from, to, section, null, 1, 0, 0);
		}else{
			outList = rmiService.getSendList(from, to, section, sendListNo, 1, 0, 0);
		}

		SectionUtil instance = SectionUtil.getInstance(sectionManager);
		JSONArray jArray = new JSONArray();
		for(int i=0;i<outList.size();i++){
			SyncPatient sPatient = outList.get(i);
			JSONObject o = new JSONObject();
			o.put("xh", i+1);
			o.put("doctadviseno", sPatient.getDOCTADVISENO());
			o.put("xm", sPatient.getPATIENTNAME());
			o.put("jzkh", sPatient.getPATIENTID());
			o.put("examinaim", sPatient.getEXAMINAIM());
			o.put("labdepartment", instance.getValue(sPatient.getLABDEPARTMENT()));
			o.put("sendtime", Constants.SDF.format(sPatient.getSENDTIME()));
			o.put("sender", sPatient.getSENDER());
			o.put("computername", sPatient.getCOMPUTERNAME());

			jArray.put(o);
		}

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jArray.toString());

		return null;
	}

	@Autowired
	private ProcessManager processManager;
	@Autowired
	private SectionManager sectionManager;
}
