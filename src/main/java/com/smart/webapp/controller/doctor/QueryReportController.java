package com.smart.webapp.controller.doctor;

import com.smart.Constants;
import com.smart.model.doctor.LeftVo;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.model.rule.Index;
import com.smart.service.doctor.DoctorQueryManager;
import com.smart.service.reagent.OutManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.model.SyncResult;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title: QueryReportController
 * Description:医生工作站报告查询
 *
 * @Author:zhou
 * @Date:2016/6/16 16:49
 * @Version:
 */

@Controller
@RequestMapping("/doctor/*")
public class QueryReportController  extends BaseAuditController {

    @Autowired
    DoctorQueryManager doctorQueryManager = null;

    @Autowired
    private OutManager outManager;

    /**
     * 返回标本列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reportlist",method = RequestMethod.GET)
    public ModelAndView handRequest(HttpServletRequest request, HttpServletResponse response){
        // 查询
        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String searchText = ConvertUtil.null2String(request.getParameter("searchText"));
        int selectType = ConvertUtil.getIntValue(request.getParameter("selectType"),-1);

        List<Sample> list = new ArrayList<Sample>();
        try{
            if (selectType == 1) {
                list = sampleManager.getHistorySample(searchText,searchText,"");
            } else if (selectType==2 && searchText != null && fromDate != null && toDate != null) {
                list = sampleManager.getSampleByPatientName(fromDate, toDate, searchText);
            } else if (selectType==3) {
                Sample p = sampleManager.get(Long.parseLong(searchText));
                if (p != null) {
                    list.add(p);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       // info.getAuditStatus() == -1  无结果 else 有结果 info.getSampleStatus()>=6 已打印
        ModelAndView view = new ModelAndView();
        view.addObject("sampleList",list);
        view.addObject("searchText",searchText);
        view.addObject("fromDate",fromDate);
        view.addObject("toDate",toDate);
        view.addObject("selectType",selectType);
        return  view;
    }

    @RequestMapping(value = "/getSampleList",method = RequestMethod.POST)
    public ModelAndView getSampleList(HttpServletRequest request,HttpServletResponse response){
        // 查询
        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String searchText = ConvertUtil.null2String(request.getParameter("searchText"));
        int selectType = ConvertUtil.getIntValue(request.getParameter("selectType"),-1);
        ModelAndView view = new ModelAndView("redirect:/doctor/reportlist");
        view.addObject("searchText",searchText);
        view.addObject("fromDate",fromDate);
        view.addObject("toDate",toDate);
        view.addObject("selectType",selectType);
        return  view;
    }

    /**
     * 报表1使用，获取标本及结果信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/getData*",method = RequestMethod.POST)
    @ResponseBody
    public String getData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取样本病人信息
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        Sample info =  sampleManager.get(id);
        JSONObject patientInfo = new JSONObject();
        patientInfo.put("id", ConvertUtil.null2String(info.getPatientId()));
        patientInfo.put("name", info.getPatientname());
        patientInfo.put("age", ConvertUtil.null2String(info.getAge()));
        patientInfo.put("examinaim", ConvertUtil.null2String(info.getInspectionName()));
        patientInfo.put("diagnostic", ConvertUtil.null2String(info.getDiagnostic()));
        patientInfo.put("section", ConvertUtil.null2String(DepartUtil.getInstance(sectionManager).getValue(info.getSectionId())));
        patientInfo.put("sex", ConvertUtil.null2String(info.getSexValue()));
        patientInfo.put("medicalnumber", ConvertUtil.null2String(info.getPatientblh()));
        patientInfo.put("bedno",ConvertUtil.null2String(info.getDepartBed()));
        patientInfo.put("type", SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(info.getSampleType())));
        JSONArray dataRows = null;
        try {
            dataRows = getResult(info.getSampleNo());
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject sampleData = new JSONObject();
        sampleData.put("patientInfo",patientInfo);
        sampleData.put("testResult",dataRows);
        return sampleData.toString();
    }


    /**
     * 报表2使用，获取样本信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/getSampleData*",method = RequestMethod.POST)
    @ResponseBody
    public String getSampleData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取样本病人信息
        String patientBlh = ConvertUtil.null2String(request.getParameter("patientBlh"));
        String nowDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String samplenos = ConvertUtil.null2String(request.getParameter("samplenos"));


        JSONArray jsonResult  = new JSONArray();
        JSONObject patientInfo = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = sdf.parse(nowDate);
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -6); //设置为前3月
        Date dBefore  = calendar.getTime(); //得到前3月的时间
        String fromDate = sdf.format(dBefore); //格式化前3月的时间

        //System.out.println(fromDate);

        try {
            Sample  info = doctorQueryManager.getSampleByPatientBlh(patientBlh,nowDate);
            patientInfo = getSampleInfo(info);
            if(!samplenos.equals("")){
                String[] arrSample = samplenos.split(",");
                String sampleids ="";
                for(int i=0;i<arrSample.length;i++){
                    if(!sampleids.equals("")) sampleids+=",";
                    sampleids +="'" + arrSample[i]+"'";
                }
                long start = System.currentTimeMillis();
                List<Object[]> sampleAndResultVos = doctorQueryManager.getSampleAndResult(patientBlh,fromDate,nowDate);
                
                getResult(samplenos, sampleAndResultVos, jsonResult);
                /*for(int i=0;i<arrSample.length;i++){
                    Sample sampleInfo = sampleManager.getBySampleNo(arrSample[i]);
                    List<SyncResult> microList = null;
                    JSONArray dataRows = new JSONArray();
                    int resultType = getSampleType(sampleInfo.getSampleNo(),sampleInfo.getSectionId()); //4微生物
                    try {
                        if (resultType == 4) {
                            System.out.println("sampleInfo.getSampleNo()=="+sampleInfo.getSampleNo());
                            microList = rmiService.getWSWResult(sampleInfo.getSampleNo());
                            dataRows = getMicroResults(microList);
                        } else {
                            dataRows = getResult(arrSample[i]);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    JSONObject obj = getSampleInfo(sampleInfo);
                    //String inspectionName = sampleInfo.getInspectionName();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type",resultType);      //类型 4：微生物
                    jsonObject.put("sampleinfo",obj);       //标本信息
                    jsonObject.put("datas",dataRows);       //结果信息

                    jsonResult.put(jsonObject);
                }*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(jsonResult);
        JSONObject sampleData = new JSONObject();
        sampleData.put("patientInfo",patientInfo);
        sampleData.put("testResult",jsonResult);
        return sampleData.toString();
    }

    @RequestMapping(value = "item")
    public ModelAndView item(HttpServletRequest request,HttpServletResponse response){
        return new ModelAndView();
    }
    private void getResult(String samplenos, List<Object[]> sampleAndResultVos, JSONArray jsonResult) {
		if(idMap.size() == 0) {
			initMap();
		}
    	//数据初始化，获得每一化验单的检验项目列表
    	Map<String, Set<String>> nowtestMap = new LinkedHashMap<String, Set<String>>();
		Map<String, Set<String>> histestMap = new LinkedHashMap<String, Set<String>>();
		Map<String, List<SampleAndResultVo>> sampleMap = new LinkedHashMap<String, List<SampleAndResultVo>>();
		Set<String> wswSet = new HashSet<String>();
		for(String sampleno : samplenos.split(",")) {
			if(sampleno.indexOf("BAA") >= 0) {
				wswSet.add(sampleno);
			}
		}
		for(Object[] objs : sampleAndResultVos) {
			SampleAndResultVo vo = new SampleAndResultVo((Sample)objs[0],(Process)objs[1],(TestResult)objs[2]);
			if(samplenos.indexOf(vo.getSample().getSampleNo()) >= 0 && vo.getSample().getSampleNo().indexOf("BAA")< 0) {
				if(nowtestMap.containsKey(vo.getSample().getSampleNo())) {
					nowtestMap.get(vo.getSample().getSampleNo()).add(vo.getTestResult().getTestId());
				} else {
					Set<String> testset = new HashSet<String>();
					testset.add(vo.getTestResult().getTestId());
					nowtestMap.put(vo.getSample().getSampleNo(), testset);
				}
			} else {
				if(histestMap.containsKey(vo.getSample().getSampleNo())) {
					histestMap.get(vo.getSample().getSampleNo()).add(vo.getTestResult().getTestId());
				} else {
					Set<String> testset = new HashSet<String>();
					testset.add(vo.getTestResult().getTestId());
					histestMap.put(vo.getSample().getSampleNo(), testset);
				}
			}
			if(sampleMap.containsKey(vo.getSample().getSampleNo())) {
				sampleMap.get(vo.getSample().getSampleNo()).add(vo);
			} else {
				List<SampleAndResultVo> list = new ArrayList<SampleAndResultVo>();
				list.add(vo);
				sampleMap.put(vo.getSample().getSampleNo(), list);
			}
		}
		
		//获取所有检验单的历史记录
		Map<String, Map<String,List<String>>> smaplenoMap = new HashMap<String, Map<String,List<String>>>();
		for(String now : nowtestMap.keySet()) {
			for(String his : histestMap.keySet()) {
				boolean isHis = false;
				boolean isOver = false;
				for(String testid : histestMap.get(his)) {
					if(nowtestMap.get(now).contains(testid)) {
						isHis = true;
						break;
					}
				}
				if(isHis) {
					List<SampleAndResultVo> hisList = sampleMap.get(his);
					if(smaplenoMap.containsKey(now)) {
						Map<String,List<String>> testMap = smaplenoMap.get(now);
						for(SampleAndResultVo vo : hisList) {
							if(nowtestMap.get(now).contains(vo.getTestResult().getTestId())) {
								if(testMap.containsKey(vo.getTestResult().getTestId())) {
									if(testMap.get(vo.getTestResult().getTestId()).size() >= 3) {
										isOver = true;
									} else {
										testMap.get(vo.getTestResult().getTestId()).add(vo.getTestResult().getTestResult());
									}
								}
							}
						}
					} else {
						Map<String,List<String>> testMap = new LinkedHashMap<String,List<String>>();
						for(SampleAndResultVo vo : hisList) {
							if(nowtestMap.get(now).contains(vo.getTestResult().getTestId())) {
								List<String> testlist = new ArrayList<String>();
								testlist.add(vo.getTestResult().getTestResult());
								testMap.put(vo.getTestResult().getTestId(), testlist);
							}
						}
						smaplenoMap.put(now, testMap);
					}
				}
				if(isOver) {
					break;
				}
			}
		}
        //微生物处理
        String wswsamplenos = setToString(wswSet);
        wswsamplenos = "'" + wswsamplenos.replace(",", "','") + "'";
        List<Sample> wswSample = sampleManager.getBysampleNos(wswsamplenos);
        Map<String, Sample> wswSampleMap = new HashMap<String, Sample>();
        for(Sample s : wswSample) {
            wswSampleMap.put(s.getSampleNo(), s);
        }
        for(String wswsampleno : wswSet) {
            Sample sampleInfo = wswSampleMap.get(wswsampleno);
            int resultType = getSampleType(sampleInfo.getSampleNo(),sampleInfo.getSectionId());
            try {
                JSONObject obj = getSampleInfo(sampleInfo);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type",resultType);      //类型 4：微生物
                jsonObject.put("sampleinfo",obj);       //标本信息
                JSONArray dataRows = new JSONArray();
                List<SyncResult> microList = rmiService.getWSWResult(sampleInfo.getSampleNo());
                dataRows = getMicroResults(microList);
                jsonObject.put("datas",dataRows);       //结果信息
                jsonResult.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
		//组装jsonObject
		for(String nowsampleno : nowtestMap.keySet()) {
			List<SampleAndResultVo> list = sampleMap.get(nowsampleno);
			Sample sampleInfo = list.get(0).getSample();
			int resultType = getSampleType(sampleInfo.getSampleNo(),sampleInfo.getSectionId());
			Map<String, Integer> colorMap = StringToMap(sampleInfo.getMarkTests());
			try {
				JSONObject obj = getSampleInfo(sampleInfo);
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("type",resultType);      //类型 4：微生物
	            jsonObject.put("sampleinfo",obj);       //标本信息
	            JSONArray dataRows = new JSONArray();
	            int color = 0;
	            for(SampleAndResultVo vo : list) {
	            	TestResult re = vo.getTestResult();
	            	if(idMap.containsKey(re.getTestId())) {
	            		color = 0;
		                String id = re.getTestId();
		                if (colorMap.containsKey(id)) {
		                    color = colorMap.get(id);
		                }
		            	Map<String,List<String>> testMap = smaplenoMap.get(vo.getSample().getSampleNo());
		            	JSONObject json = new JSONObject();
		            	json.put("id", id);
		                json.put("color", color);
		                json.put("ab", idMap.get(re.getTestId()).getEnglish());
		                json.put("name", idMap.get(re.getTestId()).getName());
		                json.put("result", re.getTestResult());
		                json.put("last","");
		                json.put("last1","");
		                json.put("last2","");
		                if(testMap != null && testMap.size() > 0) {
		                	if(testMap.containsKey(id)) {
		                		switch(testMap.get(id).size()) {
		                		case 1:
		                			json.put("last", testMap.get(id).get(0));
		                			break;
		                		case 2: 
		                			json.put("last", testMap.get(id).get(0));
		                			json.put("last1", testMap.get(id).get(1));
		                			break;
		                		case 3: 
		                			json.put("last", testMap.get(id).get(0));
		                			json.put("last1", testMap.get(id).get(1));
		                			json.put("last2", testMap.get(id).get(2));
		                			break;
		                		}
		                	}
		                }
		                json.put("checktime", Constants.DF5.format(re.getMeasureTime()));
		                json.put("device", deviceMap.get(re.getDeviceId()) == null ? re.getOperator() : deviceMap.get(re.getDeviceId()));
		                String lo = re.getRefLo();
		                String hi = re.getRefHi();
		                if (lo != null && hi != null) {
		                    json.put("scope", lo + "-" + hi);
		                } else {
		                    json.put("scope", "");
		                }
		                json.put("unit", re.getUnit());
		                json.put("knowledgeName", idMap.get(re.getTestId()).getKnowledgename());
		                json.put("editMark", re.getEditMark());
		                dataRows.put(json);
	            	}
	            }
	            jsonObject.put("datas",dataRows);       //结果信息
	            jsonResult.put(jsonObject);	
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		

	}

	/**
     * 返回标本列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/doctorquery*",method = RequestMethod.GET)
    public ModelAndView doctorQuery(HttpServletRequest request, HttpServletResponse response)throws Exception{

        // 查询
        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String searchText = ConvertUtil.null2String(request.getParameter("searchText"));
        int selectType = ConvertUtil.getIntValue(request.getParameter("selectType"),-1);

        List<LeftVo> list = new ArrayList<LeftVo>();
        if(!fromDate.equals("")){
            try{
                list = doctorQueryManager.getReportList(searchText,selectType,fromDate,toDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fromDate.equals("")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.DAY_OF_MONTH, 1);
            fromDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
            cal.roll(Calendar.DAY_OF_MONTH, -1);
            toDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        }
        // info.getAuditStatus() == -1  无结果 else 有结果 info.getSampleStatus()>=6 已打印
        ModelAndView view = new ModelAndView();
        view.addObject("sampleList",list);
        view.addObject("searchText",searchText);
        view.addObject("fromDate",fromDate);
        view.addObject("toDate",toDate);
        view.addObject("selectType",selectType);
        return  view;
    }

    @RequestMapping(value = "/getReportList",method = RequestMethod.POST)
    public ModelAndView getReportList(HttpServletRequest request,HttpServletResponse response){
        // 查询
        String fromDate = ConvertUtil.null2String(request.getParameter("fromDate"));
        String toDate = ConvertUtil.null2String(request.getParameter("toDate"));
        String searchText = ConvertUtil.null2String(request.getParameter("searchText"));
        int selectType = ConvertUtil.getIntValue(request.getParameter("selectType"),-1);
        ModelAndView view = new ModelAndView("redirect:/doctor/doctorquery");
        view.addObject("searchText",searchText);
        view.addObject("fromDate",fromDate);
        view.addObject("toDate",toDate);
        view.addObject("selectType",selectType);
        return  view;
    }


    /**
     * 获取某一样本的检验数据
     *
     * @param sampleNo
     * @return
     * @throws Exception
     */

    private JSONArray getResult(String sampleNo) throws Exception {
        String hisDate = "";
        String sameSample = "";

        if (sampleNo == null) {
            throw new NullPointerException();
        }
        if (idMap.size() == 0)
            initMap();

        if (deviceMap.size() == 0)
            initDeviceMap();

        if(likeLabMap.size() == 0) {
            initLikeLabMap();
        }

        Sample info = sampleManager.getBySampleNo(sampleNo);


        Process process = processManager.getBySampleId(info.getId());
        List<TestResult> testResults = testResultManager.getTestBySampleNo(sampleNo);

        Map<String, String> resultMap1 = new HashMap<String, String>();
        Map<String, String> resultMap2 = new HashMap<String, String>();
        Map<String, String> resultMap3 = new HashMap<String, String>();
        Map<String, String> resultMap4 = new HashMap<String, String>();
        Map<String, String> resultMap5 = new HashMap<String, String>();
        int isLastYear = 5;
        int isLastTwoYear = 5;
        if (info != null) {
            String lab = info.getSectionId();
            if(likeLabMap.containsKey(lab)) {
                lab = likeLabMap.get(lab);
            }
            List<Sample> list = sampleManager.getHistorySample(info.getPatientId(), info.getPatientblh(), lab);
            String hisSampleId = "";
            String hisSampleNo = "";
            for(Sample sample : list) {
                hisSampleId += sample.getId() + ",";
                hisSampleNo += "'" + sample.getSampleNo() + "',";
            }
            List<Process> processList = processManager.getHisProcess(hisSampleId.substring(0, hisSampleId.length()-1));
            List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
            Map<Long, Process> hisProcessMap = new HashMap<Long, Process>();
            Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
            for(Process p : processList) {
                hisProcessMap.put(p.getSampleid(), p);
            }
            for(TestResult tr : testList) {
                if(hisTestMap.containsKey(tr.getSampleNo())) {
                    hisTestMap.get(tr.getSampleNo()).add(tr);
                } else {
                    List<TestResult> tlist = new ArrayList<TestResult>();
                    tlist.add(tr);
                    hisTestMap.put(tr.getSampleNo(), tlist);
                }
            }

            Date receivetime = null;
            receivetime = process.getReceivetime();
            long curInfoReceiveTime = receivetime.getTime();
            int index = 0;
            Map<String, String> rmap = null;
            Set<String> testIdSet = new HashSet<String>();
            for (TestResult t : testResults) {
                testIdSet.add(t.getTestId());
            }
            String day = info.getSampleNo().substring(4, 6) + "/" + info.getSampleNo().substring(6, 8);

            int year = Integer.parseInt(info.getSampleNo().substring(0, 4));
            if(list!=null && list.size()>0){
                for (Sample pinfo : list) {
                    boolean isHis = false;
                    List<TestResult> his = hisTestMap.get(pinfo.getSampleNo());
                    if(his != null) {
                        for (TestResult test: his) {
                            String testid = test.getTestId();
                            Set<String> sameTests = util.getKeySet(testid);
                            sameTests.add(testid);
                            for (String id : sameTests) {
                                if (testIdSet.contains(id)) {
                                    isHis = true;
                                    break;
                                }
                            }
                            if (isHis) {
                                break;
                            }
                        }
                    }
                    Date preceivetime = null;
                    Process hisProcess = hisProcessMap.get(pinfo.getId());
                    if (hisProcess == null || pinfo.getSampleNo() == null || hisProcess.getReceivetime() == null) {
                        continue;
                    }
                    preceivetime = hisProcess.getReceivetime();
                    String pDay = pinfo.getSampleNo().substring(4, 6) + "/" + pinfo.getSampleNo().substring(6, 8);
                    int pyear = Integer.parseInt(pinfo.getSampleNo().substring(0, 4));
                    if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
                        if (index > 4)
                            break;
                        switch (index) {
                            case 0:
                                rmap = resultMap1;
                                break;
                            case 1:
                                rmap = resultMap2;
                                break;
                            case 2:
                                rmap = resultMap3;
                                break;
                            case 3:
                                rmap = resultMap4;
                                break;
                            case 4:
                                rmap = resultMap5;
                                break;
                        }
                        for (TestResult result : his) {
                            rmap.put(result.getTestId(), result.getTestResult());
                        }
                        if (!"".equals(hisDate)) {
                            hisDate += ",";
                        }
                        if(pyear == year) {
                            isLastYear--;
                        }
                        if(pyear >= year-1) {
                            isLastTwoYear--;
                        }
                        hisDate += pDay + ":" + pinfo.getSampleNo();
                        index++;
                    }
                    if (day.equals(pDay) && sameSample(info, pinfo) && pyear == year) {
                        if (!"".equals(sameSample)) {
                            sameSample += ",";
                        }
                        sameSample += pinfo.getSampleNo();
                    }
                }
            }
        }
        int color = 0;
        Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
        List<SyncResult> editTests = rmiService.getEditTests(sampleNo);
        Map<String, String> editMap = new HashMap<String, String>();
        if (editTests.size() > 0) {
            for (SyncResult sr : editTests) {
                editMap.put(sr.getTESTID(), sr.getTESTRESULT());
            }
        }
        JSONArray dataRows = new JSONArray();
        for (TestResult tr : testResults) {
            if (tr.getEditMark() == Constants.DELETE_FLAG)
                continue;

            color = 0;
            String id = tr.getTestId();
            if (colorMap.containsKey(id)) {
                color = colorMap.get(id);
            }
            JSONObject jsonObject = new JSONObject();
            if (idMap.containsKey(id)) {
//			if(true){
                String testId = tr.getTestId();
                Set<String> sameTests = util.getKeySet(testId);
                sameTests.add(testId);
                jsonObject.put("id", id);
                jsonObject.put("color", color);
                jsonObject.put("ab", idMap.get(tr.getTestId()).getEnglish());
                jsonObject.put("name", idMap.get(tr.getTestId()).getName());
                jsonObject.put("result", tr.getTestResult());
                jsonObject.put("last","");
                jsonObject.put("last1","");
                jsonObject.put("last2","");
                jsonObject.put("last3","");
                jsonObject.put("last4","");
                for(String tid : sameTests) {
                    if(jsonObject.get("last").equals("")) {
                        jsonObject.put("last", resultMap1.size() != 0 && resultMap1.containsKey(tid) ? resultMap1.get(tid) : "");
                    }
                    if(jsonObject.get("last1").equals("")) {
                        jsonObject.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(tid) ? resultMap2.get(tid) : "");
                    }
                    if(jsonObject.get("last2").equals("")) {
                        jsonObject.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(tid) ? resultMap3.get(tid) : "");
                    }
                    if(jsonObject.get("last3").equals("")) {
                        jsonObject.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(tid) ? resultMap4.get(tid) : "");
                    }
                    if(jsonObject.get("last4").equals("")) {
                        jsonObject.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(tid) ? resultMap5.get(tid) : "");
                    }
                }
                jsonObject.put("checktime", Constants.DF5.format(tr.getMeasureTime()));
                jsonObject.put("device", deviceMap.get(tr.getDeviceId()) == null ? tr.getOperator() : deviceMap.get(tr.getDeviceId()));
                String lo = tr.getRefLo();
                String hi = tr.getRefHi();
                if (lo != null && hi != null) {
                    jsonObject.put("scope", lo + "-" + hi);
                } else {
                    jsonObject.put("scope", "");
                }
                jsonObject.put("unit", tr.getUnit());
                jsonObject.put("knowledgeName", idMap.get(tr.getTestId()).getKnowledgename());
                jsonObject.put("editMark", tr.getEditMark());
                jsonObject.put("lastEdit", editMap.size() == 0 || !editMap.containsKey(id) ? "" : "上次结果 " + editMap.get(id));
                dataRows.put(jsonObject);
            }

        }
        return dataRows;
    }

    /**
     * 获取检验项目结果
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchTest", method = { RequestMethod.GET })
    @ResponseBody
    public String searchTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (org.apache.cxf.common.util.StringUtils.isEmpty(name)) {
            return null;
        }

        List<Index> desList =  indexManager.getIndexs(name);
        if(desList.size()>10)
            desList = desList.subList(0, 10);
        JSONArray array = new JSONArray();

        if (desList != null) {
            for (Index d : desList) {

                JSONObject o = new JSONObject();
                o.put("id", d.getIndexId());
                o.put("ab", d.getEnglish());
                o.put("name", d.getName());
                array.put(o);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }

    /**
     * 历史曲线图
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/singleChart*", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSingleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String testid = request.getParameter("id");
        String sample = request.getParameter("sample");
        DecimalFormat deFormat = new DecimalFormat("#.##");

        Sample info = sampleManager.getBySampleNo(sample);
        Date measuretime = new Date();
        String patientid = info.getPatientblh();
        Set<String> sameTests = new HashSet<String>();
        sameTests.add(testid);
        String tests = sameTests.toString().replace("[", "'").replace("]", "'");
        tests = tests.replaceAll(", ", "','");
        List<TestResult> list = testResultManager.getSingleHistory(tests, patientid);
        List<Double> loArr = new ArrayList<Double>();
        List<Double> reArr = new ArrayList<Double>();
        List<Double> hiArr = new ArrayList<Double>();
        List<String> timeArr = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        for(TestResult t: testResultManager.getPrintTestBySampleNo(info.getSampleNo())) {
            if (testid.equals(t.getTestId())) {
                measuretime = t.getMeasureTime();
            }
        }
        List<Reagent> rlist  = reagentManager.getByTestId(testid);
        if(rlist.size()>0) {
            String rids = "";
            Map<Long, Reagent> rm = new HashMap<Long, Reagent>();
            for(Reagent r : rlist) {
                rids += r.getId() + ",";
                rm.put(r.getId(), r);
            }
//            List<Out> out = outManager.getLastHMs(rids.substring(0, rids.length()-1), Constants.SDF.format(measuretime));
            List<Out> out = outManager.getLastHMs(rids.substring(0, rids.length()-1), measuretime);
            List<String> html = new ArrayList<String>();
            for(int i=0; i<out.size(); i++) {
                Out o = out.get(i);
                StringBuilder s = new StringBuilder("");
                s.append("<p>");
                s.append((i+1) + ". ");
                s.append(rm.get(o.getRgId()).getName());
                s.append(" 批号:");
                s.append(" " + o.getBatch());
                s.append(" 出库日期:");
                s.append(" " + Constants.SDF.format(o.getOutdate()));
                s.append("</p>");
                html.add(s.toString());
            }
            map.put("hmList", html);
        } else {
            map.put("hmList", "");
        }

        if (idMap.size() == 0)
            initMap();

        if(list.size()>1) {
            if(list.get(0).getUnit() != null && !list.get(0).getUnit().isEmpty() && idMap.containsKey(list.get(0).getTestId())) {
                map.put("name", idMap.get(list.get(0).getTestId()).getName() + " (" + list.get(0).getUnit() + ")");
            } else {
                map.put("name", idMap.get(list.get(0).getTestId()).getName());
            }
            int num = list.size();
            int count = 0;
            Double average;
            Double max = 0.0;
            Double min = 100000.0;
            Double total = 0.0;
            Double sd;
            List<Double> resultList = new ArrayList<Double>();
            for (int i = 0; i < num; i++) {
                if(StringUtils.isNumericSpace(list.get(i).getTestResult().replace(".", ""))) {
                    double d = Double.parseDouble(list.get(i).getTestResult());
                    if(d > max){
                        max = d;
                    }
                    if(d < min){
                        min = d;
                    }
                    total = total + d;
                    count = count +1;
                    resultList.add(d);
                    loArr.add(Double.parseDouble(list.get(i).getRefLo()));
                    reArr.add(Double.parseDouble(list.get(i).getTestResult()));
                    hiArr.add(Double.parseDouble(list.get(i).getRefHi()));
                    timeArr.add(Constants.SDF.format(list.get(i).getMeasureTime()));
                }
            }
            map.put("max", max);
            map.put("min", min);
            map.put("num", count);
            if (resultList.size()%2 == 0) {
                map.put("mid", resultList.get(resultList.size()/2-1));
            } else {
                map.put("mid", resultList.get(resultList.size()/2));
            }

            average = (count == 0 ? 0 : total/count);
            map.put("ave", deFormat.format(average));
            Double variance = 0.0;
            for (Double d : resultList) {
                variance = variance + Math.pow(d-average, 2);
            }
            sd = Math.sqrt(variance/resultList.size());
            map.put("sd", deFormat.format(sd));
            map.put("cov", deFormat.format(sd*100/average));
        }
        Collections.reverse(timeArr);
        Collections.reverse(reArr);
        Collections.reverse(hiArr);
        Collections.reverse(loArr);
        map.put("lo", loArr);
        map.put("re", reArr);
        map.put("hi", hiArr);
        map.put("time", timeArr);
        return map;
    }


    /**
     * 获取微生物检验结果信息
     * @param wswlist
     * @return
     */
    private JSONArray getMicroResults(List<SyncResult> wswlist) throws JSONException {
        StringBuilder html = new StringBuilder("");
        Map<String, List<SyncResult>> wswMap = new HashMap<String, List<SyncResult>>();
        for(SyncResult sr : wswlist) {
            if(sr.getRESULTFLAG().charAt(0) == 'N') {
                if(wswMap.containsKey("N")) {
                    wswMap.get("N").add(sr);
                } else {
                    List<SyncResult> list = new ArrayList<SyncResult>();
                    list.add(sr);
                    wswMap.put("N", list);
                }
            } else if(sr.getRESULTFLAG().charAt(0) == 'O') {
                if(wswMap.containsKey("O")) {
                    wswMap.get("O").add(sr);
                } else {
                    List<SyncResult> list = new ArrayList<SyncResult>();
                    list.add(sr);
                    wswMap.put("O", list);
                }
            } else if(sr.getRESULTFLAG().charAt(0) == 'B') {
                if(wswMap.containsKey("B")) {
                    wswMap.get("B").add(sr);
                } else {
                    List<SyncResult> list = new ArrayList<SyncResult>();
                    list.add(sr);
                    wswMap.put("B", list);
                }
            } else {
                if(wswMap.containsKey("A")) {
                    wswMap.get("A").add(sr);
                } else {
                    List<SyncResult> list = new ArrayList<SyncResult>();
                    list.add(sr);
                    wswMap.put("A", list);
                }
            }
        }

        JSONArray microResults = new JSONArray();   //微生物结果集合
        if(wswMap.containsKey("N")) {
            List<SyncResult> list = wswMap.get("N");
            for(SyncResult sr : list) {
               JSONObject obj = getMicroReuslt(sr);
                microResults.put(obj);
            }
        } else if (wswMap.containsKey("O")) {
            List<SyncResult> list = wswMap.get("O");
            for(SyncResult sr : list) {
                JSONObject obj = getMicroReuslt(sr);
                microResults.put(obj);
            }
        } else if (wswMap.containsKey("B")){
            List<SyncResult> list = wswMap.get("B");
            List<SyncResult> ymlist = wswMap.get("A");
            for(SyncResult sr : list) {
                JSONObject obj = getMicroReuslt(sr);

                char num = sr.getRESULTFLAG().charAt(sr.getRESULTFLAG().length()-1);
                boolean isFirst = true;
                JSONArray ymDatas = new JSONArray();
                String hasDrug = "0";
                if(ymlist != null) {
                    hasDrug = "1";
                    for(SyncResult sr2 : ymlist) {
                        if(sr2.getRESULTFLAG().charAt(sr.getRESULTFLAG().length()-1) == num) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("testid",ConvertUtil.null2String(sr2.getTESTID()));           //抗生素名
                            jsonObject.put("testresult",ConvertUtil.null2String(sr2.getTESTRESULT()));   //结果
                            jsonObject.put("hint",ConvertUtil.null2String(sr2.getHINT()));             //解释
                            jsonObject.put("reflo",ConvertUtil.null2String(sr2.getREFLO()));            //折点
                            jsonObject.put("refhi",ConvertUtil.null2String(sr2.getREFHI()));
                            jsonObject.put("unit",ConvertUtil.null2String(sr2.getUNIT()));             //单位
                            ymDatas.put(jsonObject);
                        }
                    }
                }
                obj.put("hasdrug",hasDrug);     //是否包含药敏数据
                obj.put("ymdatas",ymDatas);
                microResults.put(obj);
            }
        }
        return microResults;
    }

    private JSONObject getMicroReuslt(SyncResult syn) throws  JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "");
        jsonObject.put("color", "");
        jsonObject.put("ab", "");
        jsonObject.put("name", ConvertUtil.null2String(syn.getTESTID()));
        jsonObject.put("result", ConvertUtil.null2String(syn.getTESTRESULT()));
        jsonObject.put("last","");
        jsonObject.put("last1","");
        jsonObject.put("last2","");
        jsonObject.put("last3","");
        jsonObject.put("last4","");
        jsonObject.put("checktime",syn.getMEASURETIME());
        jsonObject.put("device", deviceMap.get(syn.getDEVICEID()));
        jsonObject.put("scope", "");
        jsonObject.put("unit", syn.getUNIT());
        jsonObject.put("knowledgeName", "");
        jsonObject.put("editMark", "");
        jsonObject.put("lastEdit", "");
        return jsonObject;
    }

    /**
     * 结果类型
     * @return
     */
    private int getSampleType(String sampleno,String sectionId){
//          System.out.println("sampleno==>"+sampleno);
        int type = 0;
        if(sampleno.substring(8, 11).equals("BAA")) {
            type = 4;
        } else {
            if(sampleno.substring(8, 11).equals("MYC")) {
                type = 3;
            }
            if("1300801".equals(sectionId)) {
                type = 5;
            }
        }
        return type;
    }

    /**
     * 获取标本信息JSON
     * @param info
     * @return
     * @throws JSONException
     */
    private JSONObject getSampleInfo(Sample info) throws JSONException{
        SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
        JSONObject patientInfo = new JSONObject();
        patientInfo.put("id", ConvertUtil.null2String(info.getPatientId()));
        patientInfo.put("name", info.getPatientname());
        patientInfo.put("sampleNo", info.getSampleNo());
        patientInfo.put("age", ConvertUtil.null2String(info.getAge()));
        patientInfo.put("examinaim", ConvertUtil.null2String(info.getInspectionName()));
        patientInfo.put("diagnostic", ConvertUtil.null2String(info.getDiagnostic()));
//        System.out.println("hossection="+info.getHosSection());
        patientInfo.put("section", ConvertUtil.null2String(sectionUtil.getValue(info.getHosSection())));
        patientInfo.put("sex", ConvertUtil.null2String(info.getSexValue()));
        patientInfo.put("medicalnumber", ConvertUtil.null2String(info.getPatientblh()));
        patientInfo.put("bedno",ConvertUtil.null2String(info.getDepartBed()));
        patientInfo.put("sampleStatus",ConvertUtil.null2String(info.getSampleStatus()));
        patientInfo.put("auditStatus",ConvertUtil.null2String(info.getAuditStatus()));
        patientInfo.put("type", SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(info.getSampleType())));
        patientInfo.put("doctadviseno", info.getId());
        return  patientInfo;
    }
}
