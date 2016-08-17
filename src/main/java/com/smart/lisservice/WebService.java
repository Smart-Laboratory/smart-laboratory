package com.smart.lisservice;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by zcw on 2016/8/17.
 */
public class WebService {
    private JaxWsProxyFactoryBean jwpfb ;
//    private LisInfoService service;
//    public WebService(){
//        jwpfb  = new JaxWsProxyFactoryBean();
//        jwpfb.setServiceClass(LisInfoService.class);
//        jwpfb.setAddress("http://127.0.0.1:8080/lisservice/services/soap");
//        service = (LisInfoService) jwpfb.create();
//    }

    public String getBacteriaList2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-client.xml");
        WebClient client = ctx.getBean("webClient", WebClient.class);
        return  client.path("lisservice/services/getBacteriaList").accept("application/xml").get(String.class);
    }
//    public String getBacteriaList(){
//        ReturnMsg msg = service.getBacteriaList();
//        return (String)msg.getMessage();
//    }

}
