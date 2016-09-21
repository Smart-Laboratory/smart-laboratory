package com.smart.lisservice;

import com.alibaba.fastjson.JSON;
import com.smart.Constants;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.*;
import com.smart.model.lis.Process;
import com.smart.model.rule.Index;
import com.smart.service.DictionaryManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.SpringContextUtil;
import com.smart.webapp.util.IndexMapUtil;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.TestIdMapUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by zcw on 2016/8/17.
 */
public class WebService {
    private DictionaryManager dictionaryManager = null;
    private IndexManager indexManager = null;
    public WebService(){
        indexManager = (IndexManager) SpringContextUtil.getBean("indexManager");
        dictionaryManager = (DictionaryManager) SpringContextUtil.getBean("dictionaryManager");
    }
    private JaxWsProxyFactoryBean jwpfb ;
    private static final Log log = LogFactory.getLog(WebService.class);
    private TestIdMapUtil testIdMapUtil = TestIdMapUtil.getInstance(indexManager);
    private String url = "http://10.31.96.38:8080/lisservice/services/rest/";
    private HttpURLConnection connection = null;

    public String getBacteriaList(){
        return  null;//client.path("getBacteriaList").accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public SampleAndResultVo getRequestInfo() {

        return new SampleAndResultVo(new Sample(), new Process(), new TestResult());
    }

    public List<Section> getSectionList() {
        List<Section> list = new ArrayList<Section>();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getDepartMentList");
            method.releaseConnection();
            httpClient.executeMethod(method);
            System.out.println("获取门诊科室信息：" + method.getResponseBodyAsString());
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    Section s = new Section();
                    s.setCode(arr.getJSONObject(i).getString("Id"));
                    s.setName(arr.getJSONObject(i).getString("Name"));
                    list.add(s);
                }
            }
            GetMethod method2 = new GetMethod(url + "getWardList");
            method.releaseConnection();
            httpClient.executeMethod(method2);
            System.out.println("获取住院信息：" + method2.getResponseBodyAsString());
            JSONObject obj2 = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj2.get("State")==1) {
                JSONArray arr = obj2.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    Section s = new Section();
                    s.setCode(arr.getJSONObject(i).getString("Id"));
                    s.setName(arr.getJSONObject(i).getString("Name"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getJCXM(String patientId, String from, String to) {
        List<String> list = new ArrayList<String>();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getOutPatientRequestInfo?requestType=2&patientType=1&patientCode=" + patientId
                    + "&fromDate=" + from + "&toDate" + to);
            method.releaseConnection();
            httpClient.executeMethod(method);
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    list.add(arr.getJSONObject(i).getString("itemName"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Patient getPatient(String patientId) {
        Patient patient = new Patient();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getPatientInfoList?patientType=1&patientCode=" + patientId);
            method.releaseConnection();
            httpClient.executeMethod(method);
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                patient.setAddress(arr.getJSONObject(0).getString("PatientAddress"));
                patient.setBirthday(Constants.SDF.parse(arr.getJSONObject(0).getString("Birthday")));
                patient.setBlh(arr.getJSONObject(0).getString("PatientFileCode"));
                patient.setIdCard(arr.getJSONObject(0).getString("IdCard"));
                patient.setSex( arr.getJSONObject(0).getString("Sex"));
                patient.setInfantFlag("0");
                patient.setPatientId(arr.getJSONObject(0).getString("PatientCode"));
                patient.setPatientName(arr.getJSONObject(0).getString("Name"));
                patient.setPhone(arr.getJSONObject(0).getString("PatientPhone"));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }

    public List<LabOrder> getExecuteInfo(String patientId, String requestmode, String from, String to) {
        List<LabOrder> list = new ArrayList<LabOrder>();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getOutPatientRequestInfo?patientCode=" + patientId + "&executeStatus=" + requestmode
                    + "&fromDate=" + from + "&toDate=" + to);
            method.releaseConnection();
            httpClient.executeMethod(method);
            System.out.println("1获取采样信息：" + method.getResponseBodyAsString());
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            list = jsonTolist(1,obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LabOrder> getExecuteInfoByRequestIds(String unExecuteRequestIds) {
        List<LabOrder> list = new ArrayList<LabOrder>();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getOutPatientRequestInfo?requestDetailId=" + unExecuteRequestIds);
            method.releaseConnection();
            httpClient.executeMethod(method);
            System.out.println("获取采样信息：" + method.getResponseBodyAsString());
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            list = jsonTolist(1,obj);
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取住院病人列表信息
     * @param ward  病区
     * @return
     */
    public List<LabOrder> getInExcuteInfo(String ward,String bedNo,String patientId){
        List<LabOrder> list = new ArrayList<LabOrder>();
        try {
            url += "getInPatientRequestInfo";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "?ward=" + ward +"&bedNo="+bedNo +"&patientId="+patientId);
            method.releaseConnection();
            httpClient.executeMethod(method);
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());

            list = jsonTolist(2,obj);
            System.out.println(obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *  JSON转LabOrder List
     * @param stayhospitalmode   门诊1 住院2 体检3
     * @param obj               JSONObject
     * @return
     * @throws JSONException
     * @throws Exception
     */
    private  List<LabOrder> jsonTolist(int stayhospitalmode,JSONObject obj) throws JSONException,Exception{
        List<LabOrder> list = new ArrayList<LabOrder>();
        if ((Integer) obj.get("State") == 1) {
            JSONArray arr = obj.getJSONArray("Message");
            for (int i = 0; i < arr.length(); i++) {
                LabOrder labOrder = new LabOrder();
                labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));         //申请科室ID
                labOrder.setHossectionName(arr.getJSONObject(i).getString("requestDepartmentName"));         //申请科室名称
                labOrder.setWardId(arr.getJSONObject(i).getString("ward"));                         //病区ID
                labOrder.setWardName(arr.getJSONObject(i).getString("wardName"));                   //病区名称
                labOrder.setBirthday(Constants.SDF.parse(arr.getJSONObject(i).getString("birthday")));
                labOrder.setBlh(arr.getJSONObject(i).getString("patientFileCode"));
                labOrder.setCycle(0);
                labOrder.setLaborderorg(arr.getJSONObject(i).getString("requestDetailId"));
                labOrder.setDiagnostic(arr.getJSONObject(i).getString("diagnose"));
                labOrder.setExamitem(arr.getJSONObject(i).getString("itemName"));
                labOrder.setPatientid(arr.getJSONObject(i).getString("patientId"));
                labOrder.setPatientname(arr.getJSONObject(i).getString("name"));
                labOrder.setPrice(arr.getJSONObject(i).getString("amount"));
                labOrder.setRequester(arr.getJSONObject(i).getString("requestDoctor"));
                labOrder.setRequestId(arr.getJSONObject(i).getString("requestId"));
                labOrder.setRequestmode(arr.getJSONObject(i).getInt("emergency"));
                labOrder.setRequesttime(Constants.SDF.parse(arr.getJSONObject(i).getString("requestDateTime")));
                labOrder.setRequestNum(arr.getJSONObject(i).getInt("quantity"));
                labOrder.setSex(arr.getJSONObject(i).getInt("sex"));
                labOrder.setStayhospitalmode(2); //门诊1 住院2 体检3
                labOrder.setToponymy(arr.getJSONObject(i).getString("testPart"));
                labOrder.setYlxh(arr.getJSONObject(i).getString("itemCode"));
                labOrder.setZxbz(arr.getJSONObject(i).getInt("status"));
                labOrder.setBed(arr.getJSONObject(i).getString("bedno"));
                labOrder.setSampletype( arr.getJSONObject(i).getString("sampleType"));
                labOrder.setAge(arr.getJSONObject(i).getString("age"));
                labOrder.setAgeUnit(arr.getJSONObject(i).getString("ageUnit"));
                labOrder.setRequesterName(arr.getJSONObject(i).getString("requestDoctorName"));
                list.add(labOrder);
            }
        }
        return list;
    }

    /**
     * HIS申请状态变更
     * @param requestType
     * @param itemId
     * @param exeType
     * @param exeDeptCode
     * @param exeDeptName
     * @param exeDoctorCode
     * @param exeDoctorName
     * @param exeDate
     * @param expand
     * @return
     */
    public boolean requestUpdate(int requestType,
                                 String itemId,
                                 int exeType,
                                 String exeDeptCode,
                                 String exeDeptName,
                                 String exeDoctorCode,
                                 String exeDoctorName,
                                 String exeDate,
                                 String expand) {
        boolean success = true;
        try {
            //if(1==1)throw new Exception("错误");
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url+"requestUpdate");
            PostMethod method = new PostMethod(url+"requestUpdate");
            JSONObject object = new JSONObject();
            object.put("requestType",requestType);
            object.put("itemId",itemId);
            object.put("exeType",exeType);
            object.put("exeDeptCode",exeDeptCode);
            object.put("exeDeptName",exeDeptName);
            object.put("exeDoctorCode",exeDoctorCode);
            object.put("exeDoctorName",exeDoctorName);
            object.put("exeDate",exeDate);
            object.put("expand",expand);

            RequestEntity requestEntity = new StringRequestEntity(object.toString(),"application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            method.releaseConnection();

            httpClient.executeMethod(method);
            System.out.println(method.getResponseBodyAsString());

            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==0) {
                success = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    /**
     * 将检测结果保存至HIS系统
     * @param sample        样本信息
     * @param process       流转记录
     * @param resultList    检测结果集合
     * @return
     */
    public boolean saveHisResult(Sample sample,Process process,List<TestResult> resultList){
        boolean flag = false;
        try {
            //if(1==1)throw new Exception("错误");
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url+"saveHisResult");
            PostMethod method = new PostMethod(url+"saveHisResult");
            JSONObject hisSampleInfo = new JSONObject();

            //样本信息
            hisSampleInfo.put("barCode",sample.getBarcode());
            hisSampleInfo.put("sampleNo",sample.getSampleNo());
            hisSampleInfo.put("organizationId",1001);       //机构代码
            hisSampleInfo.put("patientType",sample.getStayHospitalModelValue());
            hisSampleInfo.put("patientId",sample.getPatientId());
            hisSampleInfo.put("patientNo",sample.getPatientblh());
            hisSampleInfo.put("patientName",sample.getPatientname());
            hisSampleInfo.put("sex",sample.getSex());
            hisSampleInfo.put("age",sample.getAge());
            hisSampleInfo.put("ageUnit",sample.getAgeunit());
            hisSampleInfo.put("isBaby",0);
            hisSampleInfo.put("bedNo",sample.getDepartBed());
            hisSampleInfo.put("diagnosisId","");
            hisSampleInfo.put("diagnosis",sample.getDiagnostic());
            hisSampleInfo.put("part",sample.getPart());
            hisSampleInfo.put("cycleId",sample.getCycle());
            hisSampleInfo.put("executeTime",process.getExecutetime());
            hisSampleInfo.put("requesterId","");
            hisSampleInfo.put("requesterName",process.getRequester());
            hisSampleInfo.put("departmentId","");
            hisSampleInfo.put("departmentName",sample.getHosSection());
            hisSampleInfo.put("receiveTime",process.getReceivetime());
            hisSampleInfo.put("testerId","");
            hisSampleInfo.put("testerName",sample.getChkoper2());
            hisSampleInfo.put("testDepartmentId","");
            hisSampleInfo.put("testDepartmentName","");
            hisSampleInfo.put("testTime",process.getExecutetime());
            hisSampleInfo.put("auditerId","");
            hisSampleInfo.put("auditerName",process.getCheckoperator());
            hisSampleInfo.put("auditTime",process.getChecktime());
            hisSampleInfo.put("auditNote",sample.getNote());
            hisSampleInfo.put("sampleTypeId",sample.getSampleType());
            hisSampleInfo.put("sampleTypeName", SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(sample.getSampleType())));
            hisSampleInfo.put("sampleOperateStatus",0);
            hisSampleInfo.put("sampleResultTime",process.getChecktime());
            hisSampleInfo.put("sampleResultStatus",sample.getSampleStatus());
            hisSampleInfo.put("isPrint",sample.getPrintFlag());
            hisSampleInfo.put("isEmergency",sample.getRequestMode());
            hisSampleInfo.put("testId",sample.getYlxh());
            hisSampleInfo.put("testName",sample.getInspectionName());
            hisSampleInfo.put("reportUrl","");
            hisSampleInfo.put("patientCode",sample.getPatientblh());

            //结果信息
            JSONArray hisTestResult= new JSONArray();
            Map<String, Index> indexMap = testIdMapUtil.getIdMap();
            for(TestResult testResult:resultList){
                JSONObject object = new JSONObject();
                object.put("testItemId",testResult.getTestId());              //检测项目ID
                object.put("testItemName_EN", indexMap.get(sample.getYlxh()).getEnglish());         //项目英文名称
                object.put("testItemName_CN",testResult.getTestName());         //项目中文名称
                object.put("sampleTypeId",testResult.getSampleType());            //样本类型ID
                object.put("sampleTypeName",SampleUtil.getInstance(dictionaryManager).getValue(String.valueOf(testResult.getSampleType())));          //样本类型名称
                object.put("testResult",testResult.getTestResult());              //结果
                object.put("resultFlag",testResult.getResultFlag());              //结果标志
                object.put("resultHint",testResult.getHint());              //结果提示
                object.put("unit",testResult.getUnit());
                object.put("referenceLo",testResult.getRefLo());             //下限
                object.put("referenceHi",testResult.getRefHi());             //上限
                object.put("reference",testResult.getReference());               //参考范围
                object.put("order",indexMap.get(sample.getYlxh()).getPrintord());                   //结果顺序
                object.put("method",indexMap.get(sample.getYlxh()).getMethod());                  //检测方法
                hisTestResult.put(object);
            }

            JSONObject hisTestInfo = new JSONObject();
            hisTestInfo.put("sampleInfo",hisSampleInfo);
            hisTestInfo.put("testResultList",hisTestResult);
            RequestEntity requestEntity = new StringRequestEntity(hisTestInfo.toString(),"application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            method.releaseConnection();

            httpClient.executeMethod(method);
            System.out.println(method.getResponseBodyAsString());

            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==0) {
                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }


    /**
     * 记账
     * @param params  记账参数 JSON格式{"patientCode":"6000213","patientId":"123123"....}
     * @return
     */
    public boolean booking(String params){
        boolean success = true;
        try{
            HttpClient httpClient = new HttpClient();
            //httpClient.getHostConfiguration().setHost(url+"requestUpdate");
            PostMethod method = new PostMethod(url+"booking");

            RequestEntity requestEntity = new StringRequestEntity(params,"application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            method.releaseConnection();

            httpClient.executeMethod(method);
            System.out.println(method.getResponseBodyAsString());

            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==0) {
                success = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            success = false;
        }
        return  success;
    }


}
