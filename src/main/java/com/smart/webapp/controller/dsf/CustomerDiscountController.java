package com.smart.webapp.controller.dsf;

import com.smart.model.dsf.DSF_CustomerBaseDiscount;
import com.smart.service.dsf.CustomerBaseDiscountManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * Created by zjn on 2016/8/19.
 */
@Controller
@RequestMapping("/dsf/customerDiscount/*")
public class CustomerDiscountController {
    private static Log log = LogFactory.getLog(CustomerDiscountController.class);
    @Autowired
    private CustomerBaseDiscountManager customerBaseDiscountManager;


    @RequestMapping(value = "/viewCustomerDiscount*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewCustomerDiscount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    /**
     * 获取基础折扣信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/data*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        String customerid = request.getParameter("customerid");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        List<DSF_CustomerBaseDiscount> list = new ArrayList<DSF_CustomerBaseDiscount>();
        int size = 0;
        try {
            size = customerBaseDiscountManager.getSizeByCustomerid(customerid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataResponse dataResponse = new DataResponse();
        list = customerBaseDiscountManager.getDiscountByCustomerid(customerid, start, end, sidx, sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for (DSF_CustomerBaseDiscount cbd : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", cbd.getId());
            map.put("customerid", cbd.getCustomerid());
            map.put("discountrate", cbd.getDiscountrate());

            String thebasisname="未知";
            if("1".equals(cbd.getThebasis())){
                thebasisname="合同";
            }
            if("2".equals(cbd.getThebasis())){
                thebasisname="备案";
            }
            map.put("thebasisname", thebasisname);

            map.put("thebasis", cbd.getThebasis());
            map.put("begintime", cbd.getBegintime());
            map.put("endtime", cbd.getEndtime());
            map.put("customername", cbd.getCustomername());
            map.put("remark", cbd.getRemark());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/ajaxpopDiscount*", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView popDiscount(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/dsf/customerDiscount/popDiscount");
        try {
            String method = ConvertUtil.null2String(request.getParameter("method"));    //add or edit
            String customerid = ConvertUtil.null2String(request.getParameter("customerid"));
            String id = ConvertUtil.null2String(request.getParameter("id"));
            String customername = ConvertUtil.null2String(request.getParameter("customername"));
            if ("addBaseDiscount".equals(method)) {

            }
            if ("editBaseDiscount".equals(method)) {
                DSF_CustomerBaseDiscount cbd = customerBaseDiscountManager.get(Long.parseLong(id));
                modelAndView.addObject("discountInfo", cbd);
                if ("".equals(customerid)){
                    customerid = cbd.getCustomerid();
                }
            }
            modelAndView.addObject("customername",customername);
            modelAndView.addObject("method", method);
            modelAndView.addObject("customerid", customerid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @RequestMapping(value = "/deleteDiscount*", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDiscount(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String id = request.getParameter("id");
        String method = request.getParameter("method");
        JSONObject jsonObject = new JSONObject();
        try {
            if ("deleteBaseDiscount".equals(method)) {
                customerBaseDiscountManager.remove(Long.parseLong(id));
                jsonObject.put("result", "success");
            }
        } catch (Exception e) {
            jsonObject.put("result", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/saveDiscount*", method = RequestMethod.POST)
    @ResponseBody
    public String saveDiscount(@ModelAttribute DSF_CustomerBaseDiscount cbd,HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String method = ConvertUtil.null2String(request.getParameter("method"));
        try {
            List<DSF_CustomerBaseDiscount> resultList = customerBaseDiscountManager.searchByName(cbd.getCustomername(), "like");
            if (null != resultList && resultList.size() > 0) {
                jsonObject.put("msg", "repeat");
            } else {
                customerBaseDiscountManager.save(cbd);
                jsonObject.put("msg", "success");
            }

            if ("addBaseDiscount".equals(method)) {
                customerBaseDiscountManager.save(cbd);
                jsonObject.put("msg", "success");
            }
        } catch (Exception e) {
            jsonObject.put("msg", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/editDiscount*", method = RequestMethod.POST)
    @ResponseBody
    public String editDiscount(@ModelAttribute DSF_CustomerBaseDiscount cbd, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String id = ConvertUtil.null2String(request.getParameter("id"));
        String customerid = ConvertUtil.null2String(request.getParameter("customerid"));
        String method = ConvertUtil.null2String(request.getParameter("method"));
        try {
            if ("editBaseDiscount".equals(method)) {
                customerBaseDiscountManager.save(cbd);
                jsonObject.put("msg", "success");
            }
        } catch (Exception e) {
            jsonObject.put("msg", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
