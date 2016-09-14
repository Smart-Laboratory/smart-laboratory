package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.lis.Hospital;
import com.smart.service.lis.*;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.*;
import org.codehaus.jettison.json.JSONObject;
import org.drools.core.base.evaluators.IsAEvaluatorDefinition.IsAEvaluator;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.Dictionary;
import com.smart.model.execute.ExecuteUnusual;
import com.smart.model.execute.LabOrder;
import com.smart.model.execute.SampleNoBuilder;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.execute.ExecuteUnusualManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.impl.zy.RMIServiceImpl;
import com.zju.api.model.ExecuteInfo;
import com.zju.api.model.Patient;
import com.zju.api.service.RMIService;




@Controller
@RequestMapping("/manage*")
public class ExecuteController {
	
	private SimpleDateFormat ymd1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat ymd2 = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat hhmm = new SimpleDateFormat("hh:mm");
	private static SimpleDateFormat ymdh = new SimpleDateFormat("yyyy年MM月dd日 HH:mm(EEE)" );
	
	private Map<String, String> sampleTypeMap = new HashMap<String,String>();

	@RequestMapping(value = "/execute/ajax/submit*", method = RequestMethod.GET)
	public String getPatient(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
		String selfExecute = request.getParameter("selfexecute");
		String selectValue = request.getParameter("selval");
		String unExecuteRequestIds = "";
		String executeRequestIds = "";

		String examinaim="",labDepart="",sampleNo="",ybh_like="",ylxhDescribe="";
		boolean sampleNoExist = false ;
		Date executeTime = new Date();
		double time=0,day=0;
		int nextDay=0;
        JSONObject o = new JSONObject();

		for(String str : selectValue.split(";")){
			if(str!=null && !str.isEmpty()){
				if(str.split("\\+")[1].equals("0")) {
					if(unExecuteRequestIds.isEmpty()) {
						unExecuteRequestIds = str.split("\\+")[0];
					} else {
						unExecuteRequestIds += "," + str.split("\\+")[0];
					}
				} else {
					if(executeRequestIds.isEmpty()) {
						executeRequestIds = str.split("\\+")[0];
					} else {
						executeRequestIds += "," + str.split("\\+")[0];
					}
				}

			}
		}

		WebService webService = new WebService();
		List<LabOrder> unExecuteList = webService.getExecuteInfoByRequestIds(unExecuteRequestIds);
		List<LabOrder> executeList = new ArrayList<LabOrder>();
		if(!executeRequestIds.isEmpty()) {
			executeList.addAll(labOrderManager.getByRequestIds(executeRequestIds));
		}
		Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance(ylxhManager).getMap();

		double fee=0;
		boolean isFirst = true;

		List<LabOrder> needSaveList = new ArrayList<LabOrder>(); // 记录需要打印的项目
		String itemId = "";
		for(int i=0;i<unExecuteList.size();i++) {
			LabOrder labOrder = unExecuteList.get(i);	//当前采样项目
			if (labOrder.getZxbz() == 0) {
				//设置执行标志
				labOrder.setZxbz(1);
				if (itemId.isEmpty()) {
					itemId = "" + labOrder.getLaborderorg();
				} else {
					itemId += "|" + labOrder.getLaborderorg();
				}
				labOrder.setExecutetime(executeTime);
				labOrder.setExecutor(user.getUsername());
				if (i == 0) {
					Ylxh ylxh = ylxhMap.get(ConvertUtil.null2String(labOrder.getYlxh()));
					labOrder.setExamitem(ylxh.getYlmc());
					labOrder.setQbgdt(ylxh.getQbgdd());
					labOrder.setSampletype(ylxh.getYblx());
					labOrder.setLabdepartment(ylxh.getKsdm());
					labOrder.setQbgsj(ylxh.getQbgsj());
					labOrder.setToponymy(ylxh.getCjbw());
					labOrder.setCount(ylxh.getSgsl());
                    labOrder.setContainer(ylxh.getSglx());
                    labOrder.setVolume(ylxh.getBbl());
					fee = Double.parseDouble(labOrder.getPrice()) * labOrder.getRequestNum();
					labOrder.setPrice("" + fee);
				} else {
					isFirst = false;
				}
				if (labOrder.getRequestNum() < 1) {
					o.put("error", "警告！选择项目错误！数量为" + labOrder.getRequestNum() + "件！");
				}
				//开始合并组合
				String sampleType = ConvertUtil.null2String(labOrder.getSampletype()).trim();
				if (!sampleType.isEmpty()) {
					for (int j = i + 1; j < unExecuteList.size(); j++) {
						LabOrder lo = unExecuteList.get(j);		//后续采样项目
						if (isFirst) {
							Ylxh ylxh2 = ylxhMap.get(ConvertUtil.null2String(lo.getYlxh()));
							lo.setExamitem(ylxh2.getYlmc());
							lo.setQbgdt(ylxh2.getQbgdd());
							lo.setSampletype(ylxh2.getYblx());
							lo.setLabdepartment(ylxh2.getKsdm());
							lo.setQbgsj(ylxh2.getQbgsj());
							lo.setToponymy(ylxh2.getCjbw());
							lo.setCount(ylxh2.getSgsl());
                            lo.setContainer(ylxh2.getSglx());
                            lo.setVolume(ylxh2.getBbl());
							fee = Double.parseDouble(lo.getPrice()) * lo.getRequestNum();
							lo.setPrice("" + fee);
						}

						//判断合并条件
						if (lo.getZxbz() == 0 && labOrder.getRequestId().equals(lo.getRequestId()) && lo.getRequestmode() == labOrder.getRequestmode() && lo.getSampletype().equals(labOrder.getSampletype()) && lo.getLabdepartment().equals(labOrder.getLabdepartment()) && lo.getQbgsj().equals(labOrder.getQbgsj())
								&& lo.getQbgdt().equals(labOrder.getQbgdt())) {

							if (labOrder.getExamitem().indexOf(lo.getExamitem()) < 0 && lo.getExamitem().indexOf(labOrder.getExamitem()) < 0) {
								//合并组合
								labOrder.setExamitem(labOrder.getExamitem() + "+" + lo.getExamitem());
								labOrder.setYlxh(labOrder.getYlxh() + "+" + lo.getYlxh());
								labOrder.setPrice("" + (Double.parseDouble(labOrder.getPrice()) + Double.parseDouble(lo.getPrice())));
								labOrder.setLaborderorg(labOrder.getLaborderorg() + "," + lo.getLaborderorg());
								lo.setZxbz(1);
								itemId += "|" + lo.getLaborderorg();
							}
						}
					}

				} else {
					o.put("error", "警告！检验项目" + labOrder.getExamitem() + "样本类型为空！");
				}

				//获得取报告单时间

			}
			//合并后的采样项目添加到记录表
			needSaveList.add(labOrder);
		}

		Set<Long> labOrders = new HashSet<Long>();
		AutoSampleNoUtil autoUtil = AutoSampleNoUtil.getInstance(sampleNoBuilderManager);
		Map<String, List<SampleNoBuilder>> autoMap = autoUtil.getMap();
		List<Sample> needSaveSample = new ArrayList<Sample>();
		List<Process> needSaveProcess = new ArrayList<Process>();
		List<LabOrder> needSaveLabOrder = new ArrayList<LabOrder>();
		for(int i = 0; i < needSaveList.size(); i++) {
			//生成样本号
			LabOrder labOrder = needSaveList.get(i);
			boolean needNo = true;			//需要编号
			System.out.println("执行科室：" + labOrder.getLabdepartment());
			for(SampleNoBuilder sampleNoBuilder : autoMap.get(labOrder.getLabdepartment())) {
				if(needNo && sampleNoBuilder.getNowNo() < sampleNoBuilder.getEndNo()) {
					int nowNo = sampleNoBuilder.getNowNo() + 1;
					labOrder.setSampleno(Constants.DF3.format(executeTime) + sampleNoBuilder.getSegment() + String.format("%03d", nowNo));
					sampleNoBuilder.setNowNo(nowNo);
					autoUtil.updateSampleNoBuilder(sampleNoBuilderManager, sampleNoBuilder);
					needNo = false;
				}
			}
			//生成条码号
			Sample sample = new Sample();
			sample.setBirthday(labOrder.getBirthday());
			sample.setPatientId(labOrder.getPatientid());
			sample.setYlxh(labOrder.getYlxh());
			sample.setCount("" + labOrder.getCount());
			sample.setCycle(labOrder.getCycle());
			sample.setDiagnostic(labOrder.getDiagnostic());
			sample.setFee(labOrder.getPrice());
			sample.setFeestatus("" + labOrder.getFeestatus());
			sample.setHosSection(labOrder.getHossection());
			sample.setInspectionName(labOrder.getExamitem());
			sample.setPart(labOrder.getToponymy());
			sample.setPatientblh(labOrder.getBlh());
			sample.setPatientname(labOrder.getPatientname());
			sample.setRequestMode(labOrder.getRequestmode());
			sample.setSampleNo(labOrder.getSampleno());
			sample.setSex("" + labOrder.getSex());
			sample.setSampleStatus(2);
			sample.setSampleType(labOrder.getSampletype());
			sample.setSectionId(labOrder.getLabdepartment());
			sample.setStayHospitalMode(labOrder.getStayhospitalmode());
			sample.setId(sampleManager.getSampleId());
			sample.setBarcode(HospitalUtil.getInstance(hospitalManager).getHospital(user.getHospitalId()).getIdCard() + String.format("%08d", sample.getId()));

            Process process = new Process();
			process.setSampleid(sample.getId());
			process.setRequesttime(labOrder.getRequesttime());
			process.setRequester(labOrder.getRequester());
			process.setExecutetime(labOrder.getExecutetime());
			process.setExecutor(labOrder.getExecutor());

			labOrder.setLaborder(sample.getId());
            labOrder.setBarcode(sample.getBarcode());

            labOrders.add(sample.getId());
			needSaveSample.add(sample);
			needSaveProcess.add(process);
			needSaveLabOrder.add(labOrder);
		}
		//回写HIS，申请状态变更
		System.out.println("申请序号： " + itemId);
		System.out.println("生成的序号： " + labOrders.toString());
        boolean saveSuccess = true;
        try {
            System.out.println("开始保存数据");
			needSaveSample = sampleManager.saveAll(needSaveSample);
			needSaveProcess = processManager.saveAll(needSaveProcess);
			needSaveLabOrder = labOrderManager.saveAll(needSaveLabOrder);
        } catch (Exception e) {
            e.printStackTrace();
            saveSuccess = false;
        }
        if(true) {
           boolean updateStatusSuccess = webService.requestUpdate(11, itemId, 1, "21", "检验科", user.getUsername(), user.getName(), Constants.DF9.format(executeTime), "");
            if(!updateStatusSuccess){
                sampleManager.removeAll(needSaveSample);
                processManager.removeAll(needSaveProcess);
                labOrderManager.removeAll(needSaveLabOrder);
            }
        }
        JSONArray array = new JSONArray();
        for(LabOrder labOrder : needSaveLabOrder) {
            JSONObject object = new JSONObject();
            object.put("barcode", labOrder.getBarcode());
            object.put("patientName", labOrder.getPatientname());
            object.put("sex", labOrder.getSex());
            object.put("age", labOrder.getAge());
            object.put("ageUnit", labOrder.getAgeUnit());
            object.put("labDepartment", SectionUtil.getInstance(rmiService, sectionManager).getLabValue(labOrder.getLabdepartment()));
            object.put("patientCode",labOrder.getBlh());
            object.put("executeTime",labOrder.getExecutetime());
            object.put("requestMode",labOrder.getRequestmode());
            object.put("sampleNo",labOrder.getSampleno());
            object.put("container", labOrder.getContainer());
            object.put("volume", labOrder.getVolume());
            object.put("sampleType", SampleUtil.getInstance(dictionaryManager).getValue(labOrder.getSampletype()));
            object.put("sex",labOrder.getSex() == 1 ? "男" : (labOrder.getSex() == 2 ? "女" : "未知"));
            object.put("testName", labOrder.getExamitem());
            object.put("hosSectionName", SectionUtil.getInstance(rmiService, sectionManager).getValue(labOrder.getHossection()));
            object.put("ageUnit", labOrder.getAgeUnit());
            object.put("requestTime", Constants.SDF.format(labOrder.getRequesttime()));
            object.put("executeTime", Constants.SDF.format(labOrder.getExecutetime()));
            object.put("reportTime", new GetReportTimeUtil().getReportTime(labOrder.getExecutetime(), labOrder.getQbgsj()));
            object.put("requester", labOrder.getRequesterName());
            object.put("reportPlace", labOrder.getQbgdt());
            array.add(object);
        }
        o.put("labOrders", array);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}

	@RequestMapping(value = "/printBarcode*", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView printBarcoe(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tests = request.getParameter("tests");
		if(tests.endsWith(","))
			tests = tests.substring(0, tests.length()-1);
		System.out.println(tests);
		
		if(sampleTypeMap==null || sampleTypeMap.size()==0){
			initSampleTypeMap();
		}
		if(tests == null || tests.isEmpty())
			return new ModelAndView();
		List<LabOrder> list = labOrderManager.getByIds(tests);
		StringBuilder html = new StringBuilder();
		if(list==null || list.size()==0)
			return null;
		
		SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
		for(LabOrder l : list){
			html.append("<div style='background:#999;width:450px;height:350px;padding:10px 10px;margin:15px 10px;float:left;'>");
			html.append("<div id='top' style='text-align:center;'>"+
							"<p><span >浙一医院 门诊 检验回执单</span></p>"+
							"<p><span >检验部门:<b name='section'>"+sectionUtil.getLabValue(l.getLabdepartment().toString())+"</b></span></p>"+
						"</div>");
			html.append("<div id='patient'>");
			html.append("<div class='col-sm-12' style='width:100%;float:left;'><div class='col-sm-4' style='width:33.3%;float:left;'>"+
							"<span class='col-sm-6'>病历号:</span>"+
							"<b class='col-sm-6 info' id='blh' >"+l.getBlh()+"</b></div>"+
						"<div class='col-sm-2' style='width:16.6%;float:left;'><label>医嘱号:</label></div>"+
							"<div class='col-sm-6' style='width:190px;height:60px;margin-top:0px;float:left;'>"+
						//数据传递时 <% 不识别
							"<img src='/barcode?&msg="+l.getLaborder()+"     &hrsize=0mm' style='align:left;width:180px;height:50px;'/>"+
							"<div style='font-size:10px;margin-left:20px;'><span id='sampleid' name='sampleid'>"+l.getLaborder()+"</span></div></div>"+
						"</div>");
			html.append("<div class='col-sm-12' style='width:100%;float:left;'>"
						+ "<div class='col-sm-4' style='width:33.3%;float:left;'>"
							+ "<span class='col-sm-6'>姓名:</span>"
							+ "<b class='col-sm-6 info' id='name' name='name'>"+l.getPatientname()+"</b></div>"
						+ "<div class='col-sm-4' style='width:33.3%;float:left;'>"
							+ "<span class='col-sm-6'>性别:</span>"
							+ "<b class='col-sm-6 info' id='sex' >"+l.getSex()+"</b></div>"
						+ "<div class='col-sm-4' style='width:33.3%;float:left;'>"
							+ "<span class='col-sm-4'>年龄:</span>"
							+ "<b class='col-sm-5 info' id='age' >"+l.getAge()+"</b>"
							+ "<span class='col-sm-3'>岁</span></div>"
					+ "</div>");
			html.append("</div>");//patient
			html.append("<div id='sample'>");
			html.append("<div class='col-sm-12'><span class='col-sm-2'>检验项目:</span>"
							+"<b class='col-sm-10 info' id='examine' name='examine'>"+l.getExamitem()+"</b>"
						+"</div>");
			html.append("<div class='col-sm-12'>"
							+"<div class='col-sm-6' style='width:50%;float:left;padding:0px 0px;'>"
								+"<span class='col-sm-4'>样本类型:</span>"
								+"<b class='col-sm-8 info' id='sampletype' name='sampletype'>"+sampleTypeMap.get(l.getSampletype())+"</b>"
							+"</div>"
							+"<div class='col-sm-6' style='width:50%;float:left;'>"
								+"<span class='col-sm-4'>收费:</span>"
								+"<b class='col-sm-6 info' id='sf' >"+l.getPrice()+"</b>"
								+"<span class='col-sm-2'>元</span>"
							+"</div>"
						+"</div>");
			html.append("<div class='col-sm-12'>"
							+"<span class='col-sm-2'>抽血时间:</span>"
							+"<b class='col-sm-10 info' id='executetime' name='executetime'>"+ymdh.format(l.getExecutetime())+"</b>"
						+"</div>");
			html.append("<div class='col-sm-12'>"
							+"<span class='col-sm-2'>报告时间:</span>"
							+"<b class='col-sm-10 info' id='qbgsj' >"+l.getQbgsj()+"</b>"
						+"</div>");
			html.append("<div class='col-sm-12'>"
							+"<span class='col-sm-2'>报告地点:</span>"
							+"<b class='col-sm-10 info' id='qbgdd'>"+l.getQbgdt()+"</b>"
						+"</div>");
			html.append("</div>");//sample
			html.append("<div id='hints'>"
							+"<div class='col-sm-12' >"
								+"<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint1' >*法定节假日(如春节等)仪器故障报告时间顺延*</p>"
								+"<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint2' >*抽血时请带就诊卡，凭此单或就诊卡去检验报告*</p>"
								+"<p style='font-size:10px; text-align:center;margin-bottom:2px;'  id='hint3' >再挂号窗口留下核对密码，或者ucmed.cn//zszy.html下载掌上浙一软件或关注微信账号查询检查报告</p>"
							+"</div>"
						+"</div>");
			html.append("</div>");//回执单结束
			
			html.append("<div  style='background:#999;width:450px;height:160px;padding:10px 5px 5px;margin:10px 10px;float:left;''>");
			html.append("<div class='col-sm-6' style='width:50%;float:left;'>");
			html.append("<div class='col-sm-12' style='width:99%;float:left;'>"
							+"<span class='col-sm-4'  id='sName' style='font-size:15px;padding-top:5px;width:33.3%;float:left;'><b name='name'>"+l.getPatientname()+"</b></span>"
							+"<span class='col-sm-8 sfont' id='sExamitem' name='examine' style='width:66.6%;float:left;'>"+l.getExamitem()+"</span>"
						+"</div>"
						+"<div class='col-sm-12' style='width:99%;float:left;'>"
							+"<span class='col-sm-5 sfont' id='sDate' name='sexecutetime' style='width:40%;float:left;'>"+ymd2.format(l.getExecutetime())+"</span>"
							+"<span class='col-sm-2 sfont' id='sDate' name='sampletype' style='width:20%;float:left;'>"+sampleTypeMap.get(l.getSampletype())+"</span>"
						+"</div>"
						+"<div class='col-sm-12' style='width:99%;float:left;'>"
							+"<span class='sfont' name='hosSection'>"+sectionUtil.getLabValue(l.getLabdepartment().toString())+"</span>"
						+"</div>"
						+"<div class='col-sm-12' style='width:99%;float:left;'>"
							+"<span class='sfont' name='hosSection'>"+sectionUtil.getValue(l.getHossection())+"</span>"
						+"</div>"
						+"<div class='col-sm-12' style='width:190px;height:50px;margin-top:0px;float:left;'>"
							+"<img src='/barcode?&msg="+l.getLaborder()+"     &hrsize=0mm' style='align:left;width:180px;height:50px;'/>"
						+"</div>"
						+"<div class='col-sm-12' style='width:99%;float:left;'>"
							+"<span class='col-sm-4 sfont'  name='sampleid'>"+l.getLaborder()+"</span>"
							+"<span class='col-sm-8 sfont' name='sampleno' style='text-align:right;'>"+(l.getSampleno().equals("0")?"":l.getSampleno())+"</span>"
						+"</div>");
			html.append("</div></div>");
			
			
		}
		JSONObject o = new JSONObject();
		o.put("html", html.toString());
		
		return new ModelAndView().addObject("html", o);
	}
	
	
	
	
	
	public void initSampleTypeMap(){
		List<Dictionary> sampletypelist = dictionaryManager.getSampleType();
		
		for(Dictionary d : sampletypelist){
			sampleTypeMap.put(d.getSign(), d.getValue());
		}
	}
	
	@RequestMapping(value = "/getUnusualExecute*", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteUnusual getunusualExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String laborders = request.getParameter("laborder");
		if(laborders==null || laborders.isEmpty())
			return null;
		laborders = laborders.split(",")[0];
		ExecuteUnusual e = new ExecuteUnusual();
		if(executeUnusualManager.exists(Long.parseLong(laborders))){
			e = executeUnusualManager.get(Long.parseLong(laborders));
		}
		
		return e;
		
	}
	
	@RequestMapping(value = "/ajax/unusual*", method = RequestMethod.GET)
	public String unusualExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String laborders = request.getParameter("laborder");
		String jzkh = request.getParameter("jzkh");
		String part = request.getParameter("part");
		String mode = request.getParameter("mode");
		String reaction = request.getParameter("reaction");
		String time = request.getParameter("time");
		String note = request.getParameter("note");
		
		JSONObject object = new JSONObject();
		object.put("data", "false");
		if(laborders == null || jzkh == null ){
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(object.toString());
			return null;
		}
		for(String laborder : laborders.split(",")){
			ExecuteUnusual e = new ExecuteUnusual();
			if(laborder==null || laborder.isEmpty())
				continue;
			if(executeUnusualManager.exists(Long.parseLong(laborder))){
				e = executeUnusualManager.get(Long.parseLong(laborder));
			}else{
				e.setLaborder(Long.parseLong(laborder));
			}
			e.setPatientId(jzkh);
			e.setPart(part);
			e.setExecuteMode(mode);
			e.setReaction(reaction);
			e.setTime(Long.parseLong(time));
			e.setNote(note);
			
			executeUnusualManager.save(e);
		}
		object.put("data", "true");
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(object.toString());
		return null;
		
	}
	
	@Autowired
	private RMIService rmiService;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private YlxhManager ylxhManager;
	@Autowired
	private SampleNoBuilderManager sampleNoBuilderManager;
	@Autowired
	private ProcessManager processManager;
	@Autowired
	private LabOrderManager labOrderManager;
	@Autowired
	private DictionaryManager dictionaryManager;
	@Autowired
	private ExecuteUnusualManager executeUnusualManager;
	@Autowired
	private SectionManager sectionManager;
	@Autowired
	private HospitalManager hospitalManager;
	
}
