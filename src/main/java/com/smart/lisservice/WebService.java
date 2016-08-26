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
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(url);
            GetMethod method = new GetMethod(url + "getOutPatientRequestInfo?requestDetailId=" + unExecuteRequestIds);
            method.releaseConnection();
            httpClient.executeMethod(method);
            System.out.println("获取采样信息：" + method.getResponseBodyAsString());
            JSONObject obj = new JSONObject(method.getResponseBodyAsString());
            if ((Integer) obj.get("State") == 1) {
                JSONArray arr = obj.getJSONArray("Message");
                for (int i = 0; i < arr.length(); i++) {
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
            JSONObject obj = new JSONObject(client.path("getInPatientRequestInfo")
                    .replaceQueryParam("ward",ward)
                    .accept(MediaType.APPLICATION_JSON).get(String.class));
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    LabOrder lo = new LabOrder();
                    lo.setHossection(arr.getJSONObject(i).getString("KDKSID"));
                    lo.setBirthday(Constants.SDF.parse(arr.getJSONObject(i).getString("BRCSRQ")));
                    lo.setBlh(arr.getJSONObject(i).getString("BRDABH"));
                    lo.setCycle(0);
                    lo.setDiagnostic(arr.getJSONObject(i).getString("JBZDMC"));
                    lo.setExamitem(arr.getJSONObject(i).getString("JCXMMC"));
                    lo.setPatientid(arr.getJSONObject(i).getString("BRJZHM"));
                    lo.setPatientname(arr.getJSONObject(i).getString("BRDAXM"));
                    lo.setPrice(arr.getJSONObject(i).getString("FYHJJE"));
                    lo.setRequester(arr.getJSONObject(i).getString("KDYSID"));
                    lo.setRequestId(arr.getJSONObject(i).getString("SQJLID"));
                    lo.setRequestmode(arr.getJSONObject(i).getInt("SFJZPB"));
                    lo.setRequesttime(Constants.SDF.parse(arr.getJSONObject(i).getString("SQKDRQ")));
                    lo.setSex(arr.getJSONObject(i).getInt("BRDAXB"));
                    lo.setStayhospitalmode(2);      //门诊 1 住院2 体检3
                    lo.setToponymy(arr.getJSONObject(i).getString("ZLBWMC"));
                    lo.setYlxh(arr.getJSONObject(i).getString("JCXMID"));
                    lo.setZxbz(arr.getJSONObject(i).getInt("SQZTBZ"));
                    list.add(lo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void requestUpdate(int requestType, String itemId, int exeType, String exeDeptCode, String exeDeptName, String exeDoctorCode, String exeDoctorName, String exeDate, String expand) {
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
            System.out.println("回写状态：" + method.getResponseBodyAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
