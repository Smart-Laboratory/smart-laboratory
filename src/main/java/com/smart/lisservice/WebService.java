package com.smart.lisservice;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;


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

    public String getBacteriaList2(){
        return  client.path("getBacteriaList").accept(MediaType.APPLICATION_JSON).get(String.class);
    }
//    public String getBacteriaList(){
//        ReturnMsg msg = service.getBacteriaList();
//        return (String)msg.getMessage();
//    }
}
