package com.smart.lisservice;

import com.smart.Constants;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcw on 2016/8/17.
 */
public class WebService {
    private JaxWsProxyFactoryBean jwpfb ;
//    private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-resources.xml");
//    private WebClient client = ctx.getBean("webClient", WebClient.class);

    private String url = "http://10.31.96.38:8080/lisservice/services/rest/";
    private HttpURLConnection connection = null;

    public String getBacteriaList(){
        return  null;//client.path("getBacteriaList").accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public SampleAndResultVo getRequestInfo() {

        return new SampleAndResultVo(new Sample(), new Process(), new TestResult());
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
<<<<<<< HEAD
            list = jsonTolist(1,obj);
=======
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    LabOrder labOrder = new LabOrder();
                    labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));
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
                    labOrder.setStayhospitalmode(1);
                    labOrder.setToponymy(arr.getJSONObject(i).getString("testPart"));
                    labOrder.setYlxh(arr.getJSONObject(i).getString("itemCode"));
                    labOrder.setZxbz(arr.getJSONObject(i).getInt("status"));
                    list.add(labOrder);
                }
            }
>>>>>>> 7aad339e3510b3a434b696e91976bb379dfbf9ae
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
<<<<<<< HEAD
            list = jsonTolist(1,obj);
=======
            if ((Integer) obj.get("State") == 1) {
                JSONArray arr = obj.getJSONArray("Message");
                for (int i = 0; i < arr.length(); i++) {
                    LabOrder labOrder = new LabOrder();
                    labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));
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
                    labOrder.setStayhospitalmode(1);
                    labOrder.setToponymy(arr.getJSONObject(i).getString("testPart"));
                    labOrder.setYlxh(arr.getJSONObject(i).getString("itemCode"));
                    labOrder.setZxbz(arr.getJSONObject(i).getInt("status"));
                    list.add(labOrder);
                }
            }
>>>>>>> 7aad339e3510b3a434b696e91976bb379dfbf9ae
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
    public List<LabOrder> getInExcuteInfo(String ward){
        List<LabOrder> list = new ArrayList<LabOrder>();
        try {
            url += "getInPatientRequestInfo";
//            HttpClient httpClient = new HttpClient();
//            httpClient.getHostConfiguration().setHost(url);
//            GetMethod method = new GetMethod(url + "?ward=" + ward);
//            method.releaseConnection();
//            httpClient.executeMethod(method);
            //JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            String json= "{\"Message\":[{\"age\":\"\",\"amount\":8,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22686\",\"itemName\":\"Rh血型E抗原鉴定\",\"itemPrintName\":\"Rh血型E抗原鉴定\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01130594\",\"quantity\":1,\"requestDateTime\":\"2016-03-21 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26082,\"requestDoctor\":\"ADMIN\",\"requestId\":1130594,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":20,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22729\",\"itemName\":\"抗精子抗体测定(IgA)\",\"itemPrintName\":\"抗精子抗体测定(IgA)\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01130594\",\"quantity\":1,\"requestDateTime\":\"2016-03-21 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26083,\"requestDoctor\":\"ADMIN\",\"requestId\":1130594,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":8,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22686\",\"itemName\":\"Rh血型E抗原鉴定\",\"itemPrintName\":\"Rh血型E抗原鉴定\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01131013\",\"quantity\":1,\"requestDateTime\":\"2016-03-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26498,\"requestDoctor\":\"ADMIN\",\"requestId\":1131013,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":20,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22731\",\"itemName\":\"抗精子抗体测定(IgM)\",\"itemPrintName\":\"抗精子抗体测定(IgM)\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01131013\",\"quantity\":1,\"requestDateTime\":\"2016-03-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26499,\"requestDoctor\":\"ADMIN\",\"requestId\":1131013,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":20,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22729\",\"itemName\":\"抗精子抗体测定(IgA)\",\"itemPrintName\":\"抗精子抗体测定(IgA)\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01131013\",\"quantity\":1,\"requestDateTime\":\"2016-03-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26500,\"requestDoctor\":\"ADMIN\",\"requestId\":1131013,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":30,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"5181\",\"itemName\":\"抗核小体抗体测定\",\"itemPrintName\":\"抗核小体抗体测定\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01131203\",\"quantity\":1,\"requestDateTime\":\"2016-03-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26611,\"requestDoctor\":\"ADMIN\",\"requestId\":1131203,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":15,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"8356\",\"itemName\":\"抗核糖核蛋白抗体测定\",\"itemPrintName\":\"抗核糖核蛋白抗体测定\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01131203\",\"quantity\":1,\"requestDateTime\":\"2016-03-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26612,\"requestDoctor\":\"ADMIN\",\"requestId\":1131203,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":8,\"bedno\":\"04\",\"birthday\":\"1961-03-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"成人呼吸窘迫综合征\",\"emergency\":0,\"itemCode\":\"22686\",\"itemName\":\"Rh血型E抗原鉴定\",\"itemPrintName\":\"Rh血型E抗原鉴定\",\"name\":\"电子病历测试\",\"patientCode\":\"00000496\",\"patientFileCode\":\"00000496\",\"patientId\":\"335085\",\"patientRequestCode\":\"01163553\",\"quantity\":1,\"requestDateTime\":\"2016-04-22 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":54122,\"requestDoctor\":\"275\",\"requestId\":1163553,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":200,\"bedno\":\"9\",\"birthday\":\"2014-02-28 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"骨折连接不正\",\"emergency\":0,\"itemCode\":\"14859\",\"itemName\":\"新生儿溶血症筛查\",\"itemPrintName\":\"新生儿溶血症筛查\",\"name\":\"儿童测试\",\"patientCode\":\"60000289\",\"patientFileCode\":\"60000289\",\"patientId\":\"336123\",\"patientRequestCode\":\"01237942\",\"quantity\":1,\"requestDateTime\":\"2016-06-28 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":114897,\"requestDoctor\":\"275\",\"requestId\":1237942,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":6,\"bedno\":\"06\",\"birthday\":\"1961-01-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"损伤 双手创伤性切断\",\"emergency\":0,\"itemCode\":\"22707\",\"itemName\":\"葡萄糖测定(餐后半小时)\",\"itemPrintName\":\"葡萄糖测定(餐后半小时)\",\"name\":\"ICU测试\",\"patientCode\":\"00000021\",\"patientFileCode\":\"00000021\",\"patientId\":\"333664\",\"patientRequestCode\":\"01122138\",\"quantity\":1,\"requestDateTime\":\"2016-03-10 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":18899,\"requestDoctor\":\"ADMIN\",\"requestId\":1122138,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":180,\"bedno\":\"06\",\"birthday\":\"1961-01-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"化生性癌\",\"emergency\":0,\"itemCode\":\"15315\",\"itemName\":\"TORCH筛查\",\"itemPrintName\":\"TORCH筛查\",\"name\":\"ICU测试\",\"patientCode\":\"00000021\",\"patientFileCode\":\"00000021\",\"patientId\":\"333664\",\"patientRequestCode\":\"01132300\",\"quantity\":1,\"requestDateTime\":\"2016-03-23 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":27540,\"requestDoctor\":\"ADMIN\",\"requestId\":1132300,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":220,\"bedno\":\"06\",\"birthday\":\"1961-01-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"化生性癌\",\"emergency\":0,\"itemCode\":\"22861\",\"itemName\":\"torch10项定性检测\",\"itemPrintName\":\"torch10项定性检测\",\"name\":\"ICU测试\",\"patientCode\":\"00000021\",\"patientFileCode\":\"00000021\",\"patientId\":\"333664\",\"patientRequestCode\":\"01132300\",\"quantity\":1,\"requestDateTime\":\"2016-03-23 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":27541,\"requestDoctor\":\"ADMIN\",\"requestId\":1132300,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":100,\"bedno\":\"06\",\"birthday\":\"1961-01-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"化生性癌\",\"emergency\":0,\"itemCode\":\"7054\",\"itemName\":\"AFP（定量）+CEA+CA199\",\"itemPrintName\":\"AFP（定量）+CEA+CA199\",\"name\":\"ICU测试\",\"patientCode\":\"00000021\",\"patientFileCode\":\"00000021\",\"patientId\":\"333664\",\"patientRequestCode\":\"01131205\",\"quantity\":1,\"requestDateTime\":\"2016-03-23 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26614,\"requestDoctor\":\"ADMIN\",\"requestId\":1131205,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"},{\"age\":\"\",\"amount\":8,\"bedno\":\"06\",\"birthday\":\"1961-01-01 00:00:00.0\",\"department\":\"1024\",\"diagnose\":\"化生性癌\",\"emergency\":0,\"itemCode\":\"7588\",\"itemName\":\"ABO血型鉴定\",\"itemPrintName\":\"ABO血型鉴定\",\"name\":\"ICU测试\",\"patientCode\":\"00000021\",\"patientFileCode\":\"00000021\",\"patientId\":\"333664\",\"patientRequestCode\":\"01131205\",\"quantity\":1,\"requestDateTime\":\"2016-03-23 00:00:00.000\",\"requestDepartment\":\"1024\",\"requestDetailId\":26615,\"requestDoctor\":\"ADMIN\",\"requestId\":1131205,\"requestItemType\":\"检验\",\"requestType\":\"1\",\"sampleType\":\"\",\"sex\":\"1\",\"status\":3,\"testDept\":\"21\",\"testPart\":\"\",\"ward\":\"1025\",\"wardName\":\"测试病区\"}],\"State\":1}";
            JSONObject obj = new JSONObject(json);
            list = jsonTolist(2,obj);
            System.out.println(obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

<<<<<<< HEAD
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
                labOrder.setHossection(arr.getJSONObject(i).getString("ward"));         //病区
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
                labOrder.setHossectionName(arr.getJSONObject(i).getString("wardName"));
                labOrder.setSampletype(arr.getJSONObject(i).getString("sampleType"));
                list.add(labOrder);
            }
        }
        return list;
    }
    public void requestUpdate(int requestType, String itemId, int exeType, String exeDeptCode, String exeDeptName, String exeDoctorCode, String exeDoctorName, String exeDate, String expand) {
=======
    public boolean requestUpdate(int requestType, String itemId, int exeType, String exeDeptCode, String exeDeptName, String exeDoctorCode, String exeDoctorName, String exeDate, String expand) {
        boolean success = true;
>>>>>>> 7aad339e3510b3a434b696e91976bb379dfbf9ae
        try {
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
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            System.out.println(obj.toString());
            if((Integer)obj.get("State")==0) {
                success = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


}
