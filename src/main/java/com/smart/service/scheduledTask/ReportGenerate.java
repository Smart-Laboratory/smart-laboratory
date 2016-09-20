package com.smart.service.scheduledTask;

import com.smart.Constants;
import com.smart.model.lis.*;
import com.smart.model.lis.Process;
import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.*;
import com.smart.service.reagent.OutManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.Config;
import com.smart.util.ConvertUtil;
import com.smart.util.GenericPdfUtil;
import com.smart.util.SpringContextUtil;
import com.smart.webapp.util.HisIndexMapUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.webapp.util.UserUtil;
import com.zju.api.model.SyncResult;
import com.zju.api.service.RMIService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import java.io.File;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by zcw on 2016/9/20.
 * @author zhou
 * @version 1.0
 *
 * 报告单生成PDF
 */
public class ReportGenerate {

    private SectionManager sectionManager = null;
    protected DictionaryManager dictionaryManager = null;
    protected RMIService rmiService = null;
    private UserManager userManager = null;
    private IndexManager indexManager = null;
    private LikeLabManager likeLabManager = null;

    private static HisIndexMapUtil util = HisIndexMapUtil.getInstance(); //检验项映射
    private Map<String, Index> idMap = new HashMap<String, Index>();
    private Map<String, String> likeLabMap = new HashMap<String, String>();


    public  ReportGenerate()  {
        indexManager = (IndexManager) SpringContextUtil.getBean("indexManager");
        sectionManager = (SectionManager) SpringContextUtil.getBean("sectionManager");
        dictionaryManager = (DictionaryManager) SpringContextUtil.getBean("dictionaryManager");
        rmiService = (RMIService) SpringContextUtil.getBean("rmiService");
        userManager = (UserManager) SpringContextUtil.getBean("userManager");
        likeLabManager = (LikeLabManager) SpringContextUtil.getBean("likeLabManager");
    }
    /**
     * 获取报告单Html
     * @param sample            //样本
     * @param process           //流转
     * @param testResultList    //结果
     * @param hasLast           //是否上次结果
     * @return
     * @throws Exception
     */
    public String getReportHtml(Sample sample,Process process,List<TestResult> testResultList,boolean hasLast) throws Exception {
        VelocityContext velocityContext = new VelocityContext();

        int type = 1;
        String webPath= Config.getString("web.path","");
        String sampleNo = sample.getSampleNo();
        SectionUtil sectionutil = SectionUtil.getInstance(rmiService, sectionManager);
        //Patient patient = patientManager.getByPatientId(sample.getPatientId());
        //List<TestResult> list = testResultManager.getPrintTestBySampleNo(sample.getSampleNo());
        List<SyncResult> wswlist = null;

        velocityContext.put("type", type);
        velocityContext.put("blh", sample.getPatientblh());
        velocityContext.put("patientName", sample.getPatientname());
        velocityContext.put("sex", sample.getSexValue());
        velocityContext.put("age", sample.getAge());
        velocityContext.put("ageUnit", sample.getAgeunit());
        velocityContext.put("sampleType", SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(sample.getSampleType())));
        velocityContext.put("diagnostic", sample.getDiagnostic());
        velocityContext.put("note", sample.getNote());
        velocityContext.put("barCode", sample.getBarcode());
        velocityContext.put("sampleNo", sample.getSampleNo());
        velocityContext.put("sampleId", sample.getId());
        //velocityContext.put("phone",patient.getPhone());

        if(sample.getStayHospitalMode() == 2) {
            velocityContext.put("bed", sample.getDepartBed());
        }
        velocityContext.put("staymode", sample.getStayHospitalMode());
        velocityContext.put("pId", sample.getPatientId());
        velocityContext.put("section", sectionutil.getValue(sample.getHosSection()));

        if(idMap.size() == 0) {
            initMap();
        }
        if(likeLabMap.size() == 0) {
            initLikeLabMap();
        }
        velocityContext.put("requester", process.getRequester());
        velocityContext.put("tester", sample.getChkoper2());
        //更改为电子签名图片地址
        //info.put("auditor", process.getCheckoperator());
        String dzqm_imghtm = "";
        //由于process.getCheckoperator() 有工号有姓名，需要区分
        String username = UserUtil.getInstance(userManager).getKey(process.getCheckoperator());
        //实现获取电子签名
        String dzqm_filepath = webPath+"\\images\\bmp";
        File dzqm_dir = new File(dzqm_filepath);

        //System.out.println(" request.getContextPath()==>"+ requestUrl  );
        if (dzqm_dir.exists()) {
            for (File dzqm_f : dzqm_dir.listFiles()) {
                //去掉后缀
                int dot = dzqm_f.getName().lastIndexOf('.');
                if (dzqm_f.getName().substring(0, dot).equals(username)&&(dzqm_f.getName().toUpperCase().endsWith(".BMP") )) {
                    dzqm_imghtm += webPath+"/images/bmp/" + dzqm_f.getName() + ";";
                }
            }
        }
        velocityContext.put("requestUrl", webPath);
        velocityContext.put("auditro", dzqm_imghtm);
        velocityContext.put("checker",process.getCheckoperator());
        velocityContext.put("receivetime", process.getReceivetime() == null ? "" : Constants.SDF.format(process.getReceivetime()));
        velocityContext.put("checktime", Constants.SDF.format(process.getChecktime()));
        velocityContext.put("executetime", process.getExecutetime() == null ? "" : Constants.SDF.format(process.getExecutetime()));
        velocityContext.put("examinaim", sample.getInspectionName());
        velocityContext.put("date", sampleNo.substring(0, 4) + "年" + sampleNo.substring(4, 6) + "月" + sampleNo.substring(6, 8) + "日");
        Map<String, TestResult> resultMap1 = new HashMap<String, TestResult>();
        String hisTitle1 = "";
        String lab = sample.getSectionId();
//        if(likeLabMap.containsKey(lab)) {
//            lab = likeLabMap.get(lab);
//        }
//        if(hasLast && type<4) {
//            List<Sample> history = sampleManager.getHistorySample(sample.getPatientId(), sample.getPatientblh(), lab);
//            String hisSampleId = "";
//            String hisSampleNo = "";
//            for(Sample sample1 : history) {
//                hisSampleId += sample1.getId() + ",";
//                hisSampleNo += "'" + sample1.getSampleNo() + "',";
//            }
//            List<Process> processList = processManager.getHisProcess(hisSampleId.substring(0, hisSampleId.length()-1));
//            List<TestResult> testList = testResultManager.getHisTestResult(hisSampleNo.substring(0, hisSampleNo.length()-1));
//            Map<Long, Process> hisProcessMap = new HashMap<Long, Process>();
//            Map<String, List<TestResult>> hisTestMap = new HashMap<String, List<TestResult>>();
//            for(Process p : processList) {
//                hisProcessMap.put(p.getSampleid(), p);
//            }
//            for(TestResult tr : testList) {
//                if(hisTestMap.containsKey(tr.getSampleNo())) {
//                    hisTestMap.get(tr.getSampleNo()).add(tr);
//                } else {
//                    List<TestResult> tlist = new ArrayList<TestResult>();
//                    tlist.add(tr);
//                    hisTestMap.put(tr.getSampleNo(), tlist);
//                }
//            }
//            Date receivetime = null;
//            receivetime = process.getReceivetime();
//            long curInfoReceiveTime = receivetime.getTime();
//            int index = 0;
//            Map<String, TestResult> rmap = null;
//            Set<String> testIdSet = new HashSet<String>();
//            for (TestResult t : testResultList) {
//                testIdSet.add(t.getTestId());
//            }
//            if(history != null && history.size()>0){
//                for (Sample pinfo : history) {
//                    String psampleno = pinfo.getSampleNo();
//                    if(psampleno.equals(sampleNo)) {
//                        continue;
//                    }
//                    boolean isHis = false;
//                    List<TestResult> his = hisTestMap.get(psampleno);
//                    if(his != null) {
//                        for (TestResult test: his) {
//                            String testid = test.getTestId();
//                            Set<String> sameTests = util.getKeySet(testid);
//                            sameTests.add(testid);
//                            for (String id : sameTests) {
//                                if (testIdSet.contains(id)) {
//                                    isHis = true;
//                                    break;
//                                }
//                            }
//                            if (isHis) {
//                                break;
//                            }
//                        }
//                    }
//                    Date preceivetime = null;
//                    preceivetime = hisProcessMap.get(pinfo.getId()).getReceivetime();
//                    if (preceivetime == null || pinfo.getSampleNo() == null) {
//                        continue;
//                    }
//                    if (preceivetime.getTime() < curInfoReceiveTime && isHis) {
//                        if (index > 4)
//                            break;
//                        switch (index) {
//                            case 0:
//                                rmap = resultMap1;
//                                hisTitle1 = psampleno.substring(2,4) + "/" + psampleno.substring(4,6) + "/" + psampleno.substring(6,8);
//                                break;
//                        }
//                        for (TestResult tr : hisTestMap.get(psampleno)) {
//                            rmap.put(tr.getTestId(), tr);
//                        }
//                        index++;
//                    }
//                }
//            }
//        }

        List<TestResultVo> testResultVos = new ArrayList<TestResultVo>();
        for(TestResult result:testResultList){
            String testId = result.getTestId();
            Set<String> sameTests = util.getKeySet(testId);
            sameTests.add(testId);
            TestResultVo testResultVo = new TestResultVo();
            testResultVo.setTestName(idMap.get(result.getTestId()).getName());
            testResultVo.setTestResult(result.getTestResult());
            if (Integer.parseInt(idMap.get(result.getTestId()).getPrintord()) <=2015) {
                if(result.getResultFlag().charAt(0) == 'C') {
                    testResultVo.setResultFlag("↓");
                } else if(result.getResultFlag().charAt(0) == 'B') {
                    testResultVo.setResultFlag("↑");
                }
            }
            //上次检验结果
//            if(hasLast) {
//                for(String tid : sameTests) {
//                    if(resultMap1.size() != 0 && resultMap1.containsKey(tid)) {
//                        testResultVo.setHisTestResult1(resultMap1.get(tid).getTestResult());
//                        if (Integer.parseInt(idMap.get(tid).getPrintord()) <=2015) {
//                            if(resultMap1.get(tid).getResultFlag().charAt(0) == 'C') {
//                                testResultVo.setHisResultFlag1("↓");
//                            } else if(resultMap1.get(tid).getResultFlag().charAt(0) == 'B') {
//                                testResultVo.setHisResultFlag1("↑");
//                            }
//                        }
//                    }
//                }
//            }
            testResultVo.setUnit(result.getUnit());
            testResultVo.setDescription(idMap.get(result.getTestId()).getDescription());
            testResultVos.add(testResultVo);
        }
        velocityContext.put("resultSize",testResultVos.size());
        velocityContext.put("results",testResultVos);
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(Velocity.RESOURCE_LOADER, "class");
        engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        engine.init();
        String tmplate="/template/testReport.vm";
        if(testResultVos.size()>20){
            tmplate ="/template/testReport32.vm";
        }
        Template template = engine.getTemplate(tmplate, "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);

        return writer.toString();
    }


    /**
     * 创建报告单PDF
     * @param sample            //样本
     * @param process           //流转
     * @param testResultList    //结果
     * @param hasLast           //是否上次结果
     * @return
     * @throws Exception
     */
    public void createReportPdf(Sample sample,Process process,List<TestResult> testResultList,boolean hasLast) throws Exception {
        String html = getReportHtml(sample,process,testResultList,hasLast);
        GenericPdfUtil.html2Pdf(sample.getSampleNo()+".pdf",html);
    }

    private synchronized void initMap() {
        List<Index> list = indexManager.getAll();
        for (Index t : list) {
            idMap.put(t.getIndexId(), t);
        }
    }

    protected synchronized void initLikeLabMap() {
        List<LikeLab> list = likeLabManager.getAll();
        for (LikeLab ll : list) {
            likeLabMap.put(ll.getLab(), ll.getLikeLab());
        }
    }
}
