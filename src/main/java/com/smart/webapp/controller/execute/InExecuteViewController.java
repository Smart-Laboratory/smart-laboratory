package com.smart.webapp.controller.execute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.smart.lisservice.WebService;
import com.smart.model.DictionaryType;
import com.smart.model.execute.LabOrder;
import com.smart.service.execute.LabOrderManager;
import com.alibaba.fastjson.*;
import org.codehaus.jettison.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by zcw on 2016/8/22.
 * 住院标本采集控制器
 */
@Controller
@RequestMapping(value = "/nursestation/inexecute*")
public class InExecuteViewController {
    @Autowired
    private LabOrderManager labOrderManager;

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if(v==null)
                return "";
            return v;
        }
    };

     @RequestMapping(method = RequestMethod.GET)
     public ModelAndView handleRequest (HttpServletRequest request, HttpServletResponse response) throws Exception{
          return new ModelAndView();
     }
    private List<LabOrder> labOrdersService = null;
    private Map<String ,List> resultMap = null;
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
         labOrdersService= new WebService().getInExcuteInfo(ward);//重新初始化
         resultMap = new HashMap<String,List>();   //重新初始化
          JSONArray nodes = new JSONArray();
          JSONObject root = new JSONObject();
          if(labOrdersService != null && labOrdersService.size()>0){
               root.put("id",labOrdersService.get(0).getHossection());
               root.put("pId","0");
               root.put("name",labOrdersService.get(0).getHossectionName());
               root.put("open","true");
          }
          nodes.add(root);
          //按病人床位号+patientId分组
          //Map<String ,List> resultMap = new HashMap<String ,List>();
          for(Iterator it = labOrdersService.iterator();it.hasNext();){
               LabOrder  labOrder = (LabOrder)it.next();
               String key = labOrder.getBed()+"_"+labOrder.getPatientid();
               if(!resultMap.isEmpty() && resultMap.containsKey(key)){ //如果已经存在这个数组，就放在这里
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
               node.put("ward",labOrdersService.get(0).getHossection());
               node.put("wardName",labOrdersService.get(0).getHossectionName());
               nodes.add(node);
               System.out.println("key= "+ key + " and value= " + resultMap.get(key));
          }

          System.out.println(nodes.toString());
          return nodes.toString();
     }

    /**
     * 获取标本信息(未采集、已采集)
     * @param ward         病区
     * @param bedNo         床位号
     * @param requestIds     申请ID
     * @return
     */
    @RequestMapping(value ="/getRequestList*", method ={RequestMethod.POST, RequestMethod.GET},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getRequestList(String ward,String bedNo,String patientId,String requestIds){
        //获取病区已采集标本
        List<LabOrder>  labOrderList= labOrderManager.getByRequestIds(ward,bedNo,requestIds);
        List<LabOrder> beCollectedList = new ArrayList<LabOrder>();
        //List<LabOrder> labOrders = new ArrayList<LabOrder>();
        if(!bedNo.isEmpty() && !patientId.isEmpty()){
            beCollectedList =resultMap.get(bedNo+"_"+patientId);
        }else {
            beCollectedList = labOrdersService;
        }
        //未采集标本
        //beCollectedList = labOrders;
        beCollectedList.removeAll(labOrderList);

        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("spidered",labOrderList);       //已采集标本
        jsonObject.put("beollected",beCollectedList); //未采集标本
        System.out.println(jsonObject.toString());
        return  JSON.toJSONString(jsonObject.toString(),filter);


//        for(LabOrder labOrder :labOrders){
//            if(!bedNo.isEmpty()){
//                if(!labOrder.getBed() .equals(bedNo)) continue;
//            }
//            if(!patientId.isEmpty()){
//                if(!labOrder.getPatientid() .equals(patientId)) continue;
//            }
//            for(LabOrder labOrder1 :labOrderList){
//                if(labOrder.getPatientid().equals(labOrder1.getRequestId())){
//                    beCollectedList.add(labOrder);
//                }
//            }
//        }
        //未采集标本

         //return "";
     }


}
