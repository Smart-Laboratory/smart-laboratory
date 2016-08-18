package com.smart.lisservice;

import com.smart.Constants;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.lis.Patient;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;
import java.text.ParseException;


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

    public Patient getPatient(String patientId) {
        Patient patient = new Patient();
        try {
            JSONObject obj = new JSONObject(client.path("getPatientInfoList")
                    .replaceQueryParam("patientType","1")
                    .replaceQueryParam("patientCode",patientId)
                    .accept(MediaType.APPLICATION_JSON).get(String.class));
            if((Integer)obj.get("State")==1) {
                JSONArray arr = obj.getJSONArray("Message");
                patient.setAddress((String) arr.getJSONObject(0).get("PatientAddress"));
                patient.setBirthday(Constants.SDF.parse((String)arr.getJSONObject(0).get("Birthday")));
                patient.setBlh((String) arr.getJSONObject(0).get("PatientFileCode"));
                patient.setIdCard((String) arr.getJSONObject(0).get("IdCard"));
                patient.setSex((String) arr.getJSONObject(0).get("Sex"));
                patient.setInfantFlag("0");
                patient.setPatientId((String) arr.getJSONObject(0).get("PatientCode"));
                patient.setPatientName((String) arr.getJSONObject(0).get("Name"));
                patient.setPhone((String) arr.getJSONObject(0).get("PatientPhone"));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }
//    public String getBacteriaList(){
//        ReturnMsg msg = service.getBacteriaList();
//        return (String)msg.getMessage();
//    }
}
