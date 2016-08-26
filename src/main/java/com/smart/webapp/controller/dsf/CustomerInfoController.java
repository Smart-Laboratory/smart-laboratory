package com.smart.webapp.controller.dsf;

import com.smart.model.dsf.CustomerInfo;
import com.smart.model.dsf.CustomerInfoDetails;
import com.smart.model.dsf.DSF_ylxh;
import com.smart.service.dsf.CustomerDetailsManager;
import com.smart.service.dsf.CustomerManager;
import com.smart.service.dsf.DSF_ylxhManager;
import com.smart.util.ConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ViewCustomerInfoController
 * Description:第三方客户信息管理
 * Created by zjn on 2016/8/4.
 */
@Controller
@RequestMapping("/dsf/customer/*")
public class CustomerInfoController {
    private static Log log = LogFactory.getLog(CustomerInfoController.class);
    @Autowired
    private CustomerManager customerManager;
    @Autowired
    private CustomerDetailsManager customerDetailsManager;

    /**
     * 点击管理页面时显示客户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/viewCustomer*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }
    @RequestMapping(value = "/saveCustomer*", method = RequestMethod.POST)
    @ResponseBody
    public String saveCustomer(@ModelAttribute CustomerInfo cust,@ModelAttribute CustomerInfoDetails custd, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            String method = ConvertUtil.null2String(request.getParameter("method"));    //add or edit
            System.out.println(method);
            if("addNewCustomer".equals(method)){
                List<CustomerInfo> resultList = customerManager.searchByName(cust.getCustomername(), "");
                if (null != resultList && resultList.size() > 0) {
                    jsonObject.put("msg", "repeat");
                } else {
                    customerManager.save(cust);
                    jsonObject.put("msg", "success");
                }
            }
            if ("addNewContact".equals(method)){
                List<CustomerInfoDetails> resultList = customerDetailsManager.getCustomerDetailByName(custd.getCustomerid(),custd.getName(), "");
                if (null != resultList && resultList.size() > 0) {
                    jsonObject.put("msg", "repeat");
                } else {
                    customerDetailsManager.save(custd);
                    jsonObject.put("msg", "success");
                }
            }
        } catch (Exception e) {
            jsonObject.put("msg", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/editCustomer*", method = RequestMethod.POST)
    @ResponseBody
    public String editCustomer(@ModelAttribute CustomerInfo cust, CustomerInfoDetails custd, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String method = ConvertUtil.null2String(request.getParameter("method"));

        try {
            if("editCustomer".equals(method)){
                List<CustomerInfo> resultList = customerManager.searchByName(cust.getCustomername(), "");
                    if (null != resultList && resultList.size() > 0) {
                        jsonObject.put("msg", "repeat");
                    } else {
                        customerManager.save(cust);
                        jsonObject.put("msg", "success");
                    }
            }
            if("editContact".equals(method)){
                customerDetailsManager.save(custd);
                jsonObject.put("msg", "success");
            }
        } catch (Exception e) {
            jsonObject.put("msg", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/deleteCustomer*", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String customerid = request.getParameter("id");
        String method = request.getParameter("method");//deleteCustomer
        String serialnumber = request.getParameter("serialnumber");
        JSONObject jsonObject = new JSONObject();
        try {
            if("deleteCustomer".equals(method)){
                customerManager.remove(Long.parseLong(customerid));
                jsonObject.put("result", "success");
            }
            if("deleteContact".equals(method)){
                customerDetailsManager.remove(Long.parseLong(serialnumber));
                jsonObject.put("result", "success");
            }

        } catch (Exception e) {
            jsonObject.put("result", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/getCustomerList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rows = request.getParameter("rows");
        List<CustomerInfo> resultList = new ArrayList();

        String customsName = ConvertUtil.null2String(request.getParameter("query"));

        try {
            resultList = customerManager.searchByName(customsName, "like");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        for (CustomerInfo cust : resultList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("customerid", cust.getCustomerid());
            jsonObject.put("customername", cust.getCustomername());
            jsonObject.put("address", cust.getAddress());
            jsonObject.put("clientnumber", cust.getClientnumber());
            jsonObject.put("sequence", cust.getSequence());

            jsonArray.put(jsonObject);
        }


        response.setContentType("text/html;charset=UTF-8");
        return jsonArray.toString();
    }

    @RequestMapping(value = "/ajaxcustomer*", method = {RequestMethod.GET})
    @ResponseBody
    public String ajaxcustomer(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject obj = new JSONObject();
        //基础信息
        JSONObject baseJsonObject = new JSONObject();
        //联系人
        List<Map> cdResultList = new ArrayList();

        try {
            String method = ConvertUtil.null2String(request.getParameter("method"));    //add or edit
            if ("edit".equals(method)) {
                String cid = request.getParameter("id");
                //客户基本信息
                CustomerInfo customerInfo = customerManager.getCustomerById(cid);
                baseJsonObject.put("customerid", customerInfo.getCustomerid());
                baseJsonObject.put("customername", customerInfo.getCustomername());
                baseJsonObject.put("address", customerInfo.getAddress());
                obj.put("baseCust", baseJsonObject);
                //客户联系人
                List<CustomerInfoDetails> cdList = customerDetailsManager.getCustomerDetailByCid(cid);
                if (cdList != null && cdList.size() > 0) {
                    for (CustomerInfoDetails cd : cdList) {
                        Map<String, String> cdMap = new HashMap();
                        cdMap.put("serialnumber", cd.getSerialnumber().toString());
                        cdMap.put("customerid", cd.getCustomerid());
                        cdMap.put("name", cd.getName());
                        cdMap.put("age", cd.getAge());

                        String sexname = "未知";
                        if ("1".equals(cd.getSex())){
                            sexname = "男";
                        }else{
                            sexname = "女";
                        }
                        cdMap.put("sexname", sexname);

                        cdMap.put("sex", cd.getSex());
                        cdMap.put("position", cd.getPosition());
                        cdMap.put("hobby", cd.getHobby());
                        cdMap.put("birthday", cd.getBirthday());
                        cdMap.put("worktelephone", cd.getWorktelephone());
                        cdMap.put("homephone", cd.getHomephone());
                        cdMap.put("phonenumber", cd.getPhonenumber());
                        cdMap.put("scepticsofcompany", cd.getScepticsofcompany());
                        cdMap.put("besttimetovisit", cd.getBesttimetovisit());
                        cdMap.put("bestplacetovisit", cd.getBestplacetovisit());
                        cdMap.put("bestcallroute", cd.getBestcallroute());

                        String maritalstatusname = "未知";
                        if ("1".equals(cd.getMaritalstatus())){
                            maritalstatusname = "已婚";
                        }
                        if ("2".equals(cd.getMaritalstatus())){
                            maritalstatusname = "未婚";
                        }
                        cdMap.put("maritalstatusname", maritalstatusname);

                        cdMap.put("maritalstatus", cd.getMaritalstatus());
                        cdMap.put("spousename", cd.getSpousename());
                        cdMap.put("spouseoccupation", cd.getSpouseoccupation());
                        cdMap.put("spousehobby", cd.getSpousehobby());
                        cdMap.put("remarks", cd.getRemarks());
                        cdResultList.add(cdMap);
                    }
                }
                obj.put("cdList", cdResultList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    @RequestMapping(value = "/ajaxcustomerRelation*", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView ajaxcustomerRelation(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("/dsf/customer/customerRelation");

        try {
            String method = ConvertUtil.null2String(request.getParameter("method"));    //add or edit
            String serialnumber = ConvertUtil.null2String(request.getParameter("serialnumber"));
            String customerid = ConvertUtil.null2String(request.getParameter("customerid"));
            if("addNewCustomer".equals(method)){

            }
            if("editCustomer".equals(method)){
                CustomerInfo customerInfo = customerManager.getCustomerById(customerid);
                modelAndView.addObject("customerInfo",customerInfo);
            }
            if("addNewContact".equals(method)){

            }
            if("editContact".equals(method)){
                CustomerInfoDetails cd = customerDetailsManager.getCustomerDetailBySid(serialnumber);
                modelAndView.addObject("contactInfo",cd);
            }
            modelAndView.addObject("method",method);
            modelAndView.addObject("customerid",customerid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

}
