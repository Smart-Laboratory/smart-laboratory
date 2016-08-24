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
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcw on 2016/8/17.
 */
public class WebService {
    private JaxWsProxyFactoryBean jwpfb ;
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-resources.xml");
    private WebClient client = ctx.getBean("webClient", WebClient.class);

    private String url = "http://10.31.96.38:8080/lisservice/services/rest/";
    private HttpURLConnection connection = null;

    public String getBacteriaList(){
        return  client.path("getBacteriaList").accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public SampleAndResultVo getRequestInfo() {

        return new SampleAndResultVo(new Sample(), new Process(), new TestResult());
    }

    public List<String> getJCXM(String patientId, String from, String to) {
        List<String> list = new ArrayList<String>();
        try {
            url += "getOutPatientRequestInfo";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "?requestType=2&patientType=1&patientCode=" + patientId
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
            url += "getPatientInfoList";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "?patientType=1&patientCode=" + patientId);
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
            url += "getOutPatientRequestInfo";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "?patientCode=" + patientId + "&executeStatus=" + requestmode
                    + "&fromDate=" + from + "&toDate=" + to);
            method.releaseConnection();
            httpClient.executeMethod(method);
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    LabOrder labOrder = new LabOrder();
                    labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));
                    labOrder.setBirthday(Constants.SDF.parse(arr.getJSONObject(i).getString("birthday")));
                    labOrder.setBlh(arr.getJSONObject(i).getString("patientFileCode"));
                    labOrder.setCycle(0);
                    labOrder.setLaborderorg(arr.getJSONObject(i).getLong("requestDetailId"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LabOrder> getExecuteInfoByRequestIds(String unExecuteRequestIds) {
        List<LabOrder> list = new ArrayList<LabOrder>();
        try {
            url += "getOutPatientRequestInfo";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "?requestDetailId=" + unExecuteRequestIds);
            method.releaseConnection();
            httpClient.executeMethod(method);
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    LabOrder labOrder = new LabOrder();
                    labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));
                    labOrder.setBirthday(Constants.SDF.parse(arr.getJSONObject(i).getString("birthday")));
                    labOrder.setBlh(arr.getJSONObject(i).getString("patientFileCode"));
                    labOrder.setCycle(0);
                    labOrder.setLaborderorg(arr.getJSONObject(i).getLong("requestDetailId"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void requestUpdate(int requestType, String itemId, int exeType, String exeDeptCode, String exeDeptName, String exeDoctorCode, String exeDoctorName, String exeDate, String expand) {
        try {
            url += "requestUpdate";
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            PostMethod method = new PostMethod(url);
            method.setRequestHeader("Content-Type","application/json;charset=utf-8");
            NameValuePair[] param = { new NameValuePair("requestType",requestType+""),
                    new NameValuePair("exeType",exeType+""),
                    new NameValuePair("exeDeptCode",exeDeptCode),
                    new NameValuePair("exeDeptName",exeDeptName),
                    new NameValuePair("exeDoctorCode",exeDoctorCode),
                    new NameValuePair("exeDoctorName",exeDoctorName),
                    new NameValuePair("exeDate",exeDate),
                    new NameValuePair("expand",expand)} ;
            method.setRequestBody(param);
            method.releaseConnection();
            httpClient.executeMethod(method);
            System.out.println(method.getResponseBodyAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public String getBacteriaList(){
//        ReturnMsg msg = service.getBacteriaList();
//        return (String)msg.getMessage();
//    }
}
