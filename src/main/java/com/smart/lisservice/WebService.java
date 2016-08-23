package com.smart.lisservice;

import com.smart.Constants;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.zju.api.model.ExecuteInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcw on 2016/8/17.
 */
public class WebService {
    private JaxWsProxyFactoryBean jwpfb ;
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-resources.xml");
    private WebClient client = ctx.getBean("webClient", WebClient.class);
//    private LisInfoService service;
//    public WebService(){
//        jwpfb  = new JaxWsProxyFactoryBean();
//        jwpfb.setServiceClass(LisInfoService.class);
//        jwpfb.setAddress("http://127.0.0.1:8080/lisservice/services/soap");
//        service = (LisInfoService) jwpfb.create();
//    }

    public String getBacteriaList(){
        return  client.path("getBacteriaList").accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public SampleAndResultVo getRequestInfo() {

        return new SampleAndResultVo(new Sample(), new Process(), new TestResult());
    }

    public List<String> getJCXM(String patientId, String from, String to) {
        List<String> list = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(client.path("getPatientRequestInfo")
                    .replaceQueryParam("requestType","2")
                    .replaceQueryParam("patientType","1")
                    .replaceQueryParam("patientCode",patientId)
                    .replaceQueryParam("fromDate",from)
                    .replaceQueryParam("toDate",to)
                    .accept(MediaType.APPLICATION_JSON).get(String.class));
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
            JSONObject obj = new JSONObject(client.path("getPatientInfoList")
                    .replaceQueryParam("patientType","1")
                    .replaceQueryParam("patientCode",patientId)
                    .accept(MediaType.APPLICATION_JSON).get(String.class));
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
            JSONObject obj = new JSONObject(client.path("getPatientRequestInfo")
                    .replaceQueryParam("patientCode",patientId)
                    .replaceQueryParam("executeStatus",requestmode)
                    .replaceQueryParam("fromDate",from)
                    .replaceQueryParam("toDate",to)
                    .accept(MediaType.APPLICATION_JSON).get(String.class));
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                for(int i = 0; i < arr.length(); i++) {
                    LabOrder labOrder = new LabOrder();
                    labOrder.setHossection(arr.getJSONObject(i).getString("requestDepartment"));
                    labOrder.setBirthday(Constants.SDF.parse(arr.getJSONObject(i).getString("birthday")));
                    labOrder.setBlh(arr.getJSONObject(i).getString("patientFileCode"));
                    labOrder.setCycle(0);
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
//    public String getBacteriaList(){
//        ReturnMsg msg = service.getBacteriaList();
//        return (String)msg.getMessage();
//    }
}
