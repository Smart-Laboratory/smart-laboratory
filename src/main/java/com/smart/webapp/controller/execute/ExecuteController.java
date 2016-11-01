package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.reagent.In;
import com.smart.service.lis.*;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.*;
import org.codehaus.jettison.json.JSONObject;
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
import com.smart.service.execute.ExecuteUnusualManager;
import com.smart.service.execute.LabOrderManager;
import com.smart.service.execute.SampleNoBuilderManager;

@Controller
@RequestMapping("/manage*")
public class ExecuteController {
	
	private SimpleDateFormat ymd2 = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat ymdh = new SimpleDateFormat("yyyy年MM月dd日 HH:mm(EEE)" );
	
	private Map<String, String> sampleTypeMap = new HashMap<String,String>();

	@RequestMapping(value = "/execute/ajax/submit*", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{

		long start = System.currentTimeMillis();
		User user = UserUtil.getInstance().getUser(request.getRemoteUser());
		String selfExecute = request.getParameter("selfexecute");
		String selectValue = request.getParameter("selval");
		String unExecuteRequestIds = "";
		for(String str : selectValue.split(";")){
			if(str!=null && !str.isEmpty()){
				if(unExecuteRequestIds.isEmpty()) {
					unExecuteRequestIds = str.split("[+]")[0];
				} else {
					unExecuteRequestIds += "," + str.split("[+]")[0];
				}
			}
		}

		Date executeTime = new Date();
        JSONObject o = new JSONObject();

		WebService webService = new WebService();
		List<LabOrder> unExecuteList = webService.getExecuteInfoByRequestIds(unExecuteRequestIds);
		Map<String, Ylxh> ylxhMap = YlxhUtil.getInstance().getMap();

		double fee=0;
		boolean isFirst = true;

		List<LabOrder> needSaveList = new ArrayList<LabOrder>(); // 记录需要打印的项目
		String itemId = "";
		for(int i=0;i<unExecuteList.size();i++) {
			LabOrder labOrder = unExecuteList.get(i);	//当前采样项目
			Ylxh ylxh = ylxhMap.get(ConvertUtil.null2String(labOrder.getYlxh()));
			String key = labOrder.getRequestId() + "_" +
					ConvertUtil.null2String(ylxh.getYblx()) + "_" +
					ConvertUtil.null2String(ylxh.getKsdm()) + "_" +
					ConvertUtil.null2String(ylxh.getQbgsj())+"_"+
					ConvertUtil.null2String(ylxh.getSglx())+"_"+
					ConvertUtil.null2String(ylxh.getSgsl())+"_"+
					ConvertUtil.null2String(ylxh.getOutSegment())+"_"+
					ConvertUtil.null2String(ylxh.getCjbw());
			if(ylxh.getSfhb() == 0) {
				key += "_"+ ConvertUtil.null2String(ylxh.getYlxh());
			}
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
						Ylxh ylxh2 = ylxhMap.get(ConvertUtil.null2String(lo.getYlxh()));	//后续检验目的信息
						String key2 = labOrder.getRequestId() + "_" +
								ConvertUtil.null2String(ylxh2.getYblx()) + "_" +
								ConvertUtil.null2String(ylxh2.getKsdm()) + "_" +
								ConvertUtil.null2String(ylxh2.getQbgsj())+"_"+
								ConvertUtil.null2String(ylxh2.getSglx())+"_"+
								ConvertUtil.null2String(ylxh2.getSgsl())+"_"+
								ConvertUtil.null2String(ylxh2.getOutSegment())+"_"+
								ConvertUtil.null2String(ylxh2.getCjbw());
						if(ylxh.getSfhb() == 0) {
							key2 += "_"+ ConvertUtil.null2String(ylxh.getYlxh());
						}
						if (isFirst) {
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
						if (key.equals(key2)) {
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

				//合并后的采样项目添加到记录表
				needSaveList.add(labOrder);
			}

		}
		System.out.println(System.currentTimeMillis() - start);
		Set<Long> labOrders = new HashSet<Long>();
//		AutoSampleNoUtil autoUtil = AutoSampleNoUtil.getInstance(sampleNoBuilderManager);
//		Map<String, List<SampleNoBuilder>> autoMap = autoUtil.getMap();
		List<Sample> needSaveSample = new ArrayList<Sample>();
		List<Process> needSaveProcess = new ArrayList<Process>();
		List<LabOrder> needSaveLabOrder = new ArrayList<LabOrder>();
		for(int i = 0; i < needSaveList.size(); i++) {
			LabOrder labOrder = needSaveList.get(i);
			Ylxh ylxh = null;
			//生成样本号
			if(labOrder.getYlxh().indexOf("+") > 0) {
				ylxh = ylxhMap.get(ConvertUtil.null2String(labOrder.getYlxh().split("[+]")[0])); //获得检验段
			} else {
				ylxh = ylxhMap.get(ConvertUtil.null2String(labOrder.getYlxh())); //获得检验段
			}
			String isAutoNo = ConvertUtil.null2String(ylxh.getIsAutoNo());
			//自动生成样本号
			if(isAutoNo.equals("1")){
				String newSampleNo = ConvertUtil.null2String(sampleManager.generateSampleNo(ylxh.getOutSegment(),0));
				labOrder.setSampleno(newSampleNo);
			} else {
				labOrder.setSampleno("0");
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
			sample.setSampleStatus(Constants.SAMPLE_STATUS_EXECUTED);
			sample.setSampleType(labOrder.getSampletype());
			sample.setSectionId(labOrder.getLabdepartment());
			sample.setStayHospitalMode(labOrder.getStayhospitalmode());
			sample.setId(sampleManager.getSampleId());
			sample.setBarcode(HospitalUtil.getInstance(hospitalManager).getHospital(user.getHospitalId()).getIdCard() + String.format("%08d", sample.getId()));
			sample.setAge(labOrder.getAge());
			sample.setAgeunit(labOrder.getAgeUnit());

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
		System.out.println(System.currentTimeMillis() - start);
        if(saveSuccess) {
        	String updateStatusSuccess = webService.requestUpdate(11, itemId, 1, "21", "检验科", user.getHisId(), user.getName(), ConvertUtil.getFormatDateGMT(executeTime,"yyyy-MM-dd'T'HH:mm:ss'Z'" ), "");
            if(!updateStatusSuccess.isEmpty()){
                sampleManager.removeAll(needSaveSample);
                processManager.removeAll(needSaveProcess);
                labOrderManager.removeAll(needSaveLabOrder);
            }
			o.put("labOrders", printJson(needSaveLabOrder));
        }
		System.out.println(System.currentTimeMillis() - start);
		return o.toString();
	}

	/**
	 * 已采集标本重新打印条码
	 *
	 * @return
	 */
	@RequestMapping(value = "/execute/ajax/reprint*", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String reprint(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String selectValue = request.getParameter("selval");
		String executeRequestIds = "";
		for(String str : selectValue.split(";")){
			if(str!=null && !str.isEmpty()){
				if(executeRequestIds.isEmpty()) {
					executeRequestIds = "'" + str.split("[+]")[0] + "'";
				} else {
					executeRequestIds += ",'" + str.split("[+]")[0] + "'";
				}
			}
		}
		List<LabOrder> executeList = new ArrayList<LabOrder>();
		if(!executeRequestIds.isEmpty()) {
			executeList = labOrderManager.getByRequestIds(executeRequestIds);
		}
		JSONObject o = new JSONObject();
		o.put("labOrders", printJson(executeList));
		return o.toString();
	}

	/**
	 * 已采集标本退回
	 *
	 * @return
	 */
	@RequestMapping(value = "/execute/ajax/back*", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String back(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = UserUtil.getInstance().getUser(request.getRemoteUser());
		String selectValue = request.getParameter("selval");
		String requestIds = "";
		for(String str : selectValue.split(";")){
			if(str!=null && !str.isEmpty()){
				if(requestIds.isEmpty()) {
					requestIds = "'" + str.split("[+]")[0] + "'";
				} else {
					requestIds += ",'" + str.split("[+]")[0] + "'";
				}
			}
		}
		List<LabOrder> list = labOrderManager.getByRequestIds(requestIds);
		String sampleIds = "";
		Map<Long, LabOrder> map = new HashMap<Long, LabOrder>();
		for(LabOrder labOrder : list) {
			if(sampleIds.isEmpty()) {
				sampleIds = "" + labOrder.getLaborder();
			} else {
				sampleIds += "," + labOrder.getLaborder();
			}
			map.put(labOrder.getLaborder(), labOrder);
		}
		List<Sample> sampleList = sampleManager.getByIds(sampleIds);
		List<Sample> needRemoveSample = new ArrayList<Sample>();
		List<LabOrder> needRemoveLabOrder = new ArrayList<LabOrder>();
		JSONObject o = new JSONObject();
		o.put("success", 1);
		o.put("message", "标本退回成功！");
		String itemId = "";
		String needRemoveSampleIds = "";
		for(Sample sample : sampleList) {
			if(sample.getSampleStatus() < Constants.SAMPLE_STATUS_RECEIVED) {
				needRemoveSampleIds += sample.getId() + ",";
				needRemoveSample.add(sample);
				needRemoveLabOrder.add(map.get(sample.getId()));
				if(itemId.isEmpty()) {
					itemId = map.get(sample.getId()).getLaborderorg().replace(",","|");
				} else {
					itemId += "|" + map.get(sample.getId()).getLaborderorg().replace(",","|");
				}
			} else {
				o.put("success", 0);
				o.put("message", "标本部分退回不成功，有标本已检验！");
			}
		}
		String updateStatusSuccess = new WebService().requestUpdate(11, itemId, 2, "21", "检验科", user.getHisId(), user.getName(), ConvertUtil.getFormatDateGMT(new Date(),"yyyy-MM-dd'T'HH:mm:ss'Z'" ), "");
		if(updateStatusSuccess.isEmpty()){
			sampleManager.removeAll(needRemoveSample);
			processManager.removeBySampleIds(needRemoveSampleIds.substring(0, needRemoveSampleIds.length()-1));
			labOrderManager.removeAll(needRemoveLabOrder);
		} else {
			o.put("success", 0);
			o.put("message", updateStatusSuccess);
		}
		return o.toString();
	}

	private JSONArray printJson(List<LabOrder> labOrderList) throws Exception {
		JSONArray array = new JSONArray();
		for(LabOrder labOrder : labOrderList) {
			JSONObject object = new JSONObject();
			object.put("barcode", labOrder.getBarcode());
			object.put("patientName", labOrder.getPatientname());
			object.put("sex", labOrder.getSex());
			object.put("age", labOrder.getAge());
			object.put("ageUnit", labOrder.getAgeUnit());
			object.put("labDepartment", SectionUtil.getInstance(sectionManager).getLabValue(labOrder.getLabdepartment()));
			object.put("patientCode", labOrder.getPatientid());
			object.put("executeTime", labOrder.getExecutetime());
			object.put("requestMode", labOrder.getRequestmode());
			object.put("sampleNo", labOrder.getSampleno());
			object.put("container", ConvertUtil.null2String(labOrder.getContainer()));
			object.put("volume", ConvertUtil.null2String(labOrder.getVolume()));
			object.put("sampleType", SampleUtil.getInstance(dictionaryManager).getValue(labOrder.getSampletype()));
			object.put("sex", labOrder.getSex() == 1 ? "男" : (labOrder.getSex() == 2 ? "女" : "未知"));
			object.put("testName", labOrder.getExamitem());
			object.put("hosSectionName", SectionUtil.getInstance(sectionManager).getValue(labOrder.getHossection()));
			object.put("ageUnit", labOrder.getAgeUnit());
			object.put("requestTime", Constants.SDF.format(labOrder.getRequesttime()));
			object.put("executeTime", Constants.SDF.format(labOrder.getExecutetime()));
			object.put("reportTime", new GetReportTimeUtil().getReportTime(labOrder.getExecutetime(), labOrder.getQbgsj()));
			object.put("requester", ConvertUtil.null2String(labOrder.getRequesterName()));
			object.put("reportPlace", labOrder.getQbgdt());
			array.add(object);
		}
		return array;
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
		
		SectionUtil sectionUtil = SectionUtil.getInstance(sectionManager);
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
	private SampleManager sampleManager;
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
