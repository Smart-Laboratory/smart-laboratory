package com.smart.webapp.controller.execute;

import com.smart.lisservice.WebService;
import com.smart.model.DictionaryType;
import com.smart.model.execute.LabOrder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zcw on 2016/8/22.
 * 住院标本采集控制器
 */
@Controller
@RequestMapping(value = "/nursestation/inexecute*")
public class InExecuteViewController {
     @RequestMapping(method = RequestMethod.GET)
     public ModelAndView handleRequest (HttpServletRequest request, HttpServletResponse response) throws Exception{
          return new ModelAndView();
     }

     private Map<String ,List> resultMap;
     /**
      * 获取字典列表
      * @param request
      * @param response
      * @return
      * @throws Exception
      */
     @RequestMapping(value ="/getList*", method ={RequestMethod.POST, RequestMethod.GET},produces = "application/json; charset=utf-8")
     @ResponseBody
     public String getList(@RequestParam(value = "ward") String ward, HttpServletRequest request, HttpServletResponse response) throws Exception{
          List<LabOrder> labOrders = new WebService().getInExcuteInfo(ward);
          JSONArray nodes = new JSONArray();
          JSONObject root = new JSONObject();
          if(labOrders != null && labOrders.size()>0){
               root.put("id",labOrders.get(0).getHossection());
               root.put("pId","0");
               root.put("name",labOrders.get(0).getHossectionName());
               root.put("open","true");
          }
          nodes.put(root);
          //按病人床位号+patientId分组
          //Map<String ,List> resultMap = new HashMap<String ,List>();
          for(Iterator it = labOrders.iterator();it.hasNext();){
               LabOrder  labOrder = (LabOrder)it.next();
               String key = labOrder.getBed()+"_"+labOrder.getPatientid();
               if(resultMap.containsKey(key)){ //如果已经存在这个数组，就放在这里
                    List laborderList = resultMap.get(key);
                    laborderList.add(labOrder);
               }else{
                    List laborderList = new ArrayList();  //重新声明一个数组list
                    laborderList.add(labOrder);
                    resultMap.put(key, laborderList);
               }
          }
          for (String key : resultMap.keySet()) {
               List<LabOrder> labOrders1 = resultMap.get(key);
               JSONObject node = new JSONObject();
               node.put("id",labOrders1.get(0).getPatientid());
               node.put("pId",ward);
               node.put("name",labOrders1.get(0).getPatientname());
               node.put("bedNo",labOrders1.get(0).getBed());
               node.put("patientCode",labOrders1.get(0).getBlh());
               node.put("requestId",labOrders1.get(0).getRequestId());
               node.put("ward",labOrders.get(0).getHossection());
               node.put("wardName",labOrders.get(0).getHossectionName());
               nodes.put(node);
               System.out.println("key= "+ key + " and value= " + resultMap.get(key));
          }

          System.out.println(nodes.toString());
          return nodes.toString();
     }

}
