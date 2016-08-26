package com.smart.webapp.controller.dsf;

import com.smart.model.dsf.DSF_TestItems;
import com.smart.model.dsf.DSF_ylxh;
import com.smart.model.lis.Ylxh;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.dsf.DSF_TestItemsManager;
import com.smart.service.dsf.DSF_ylxhManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.UserUtil;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjn on 2016/8/15.
 */
@Controller
@RequestMapping("/dsf/testObjective/*")
public class TestObjectiveController {

    private static Log log = LogFactory.getLog(TestObjectiveController.class);
    @Autowired
    private DSF_ylxhManager dsf_ylxhManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private DSF_TestItemsManager dsf_testItemsManager;

    protected Map<String, DSF_TestItems> idMap = new HashMap<String, DSF_TestItems>();
    protected synchronized void initMap() {
        List <DSF_TestItems>list = dsf_testItemsManager.getAll();
        for (DSF_TestItems ti : list) {
            idMap.put(ti.getIndexId(), ti);
        }
    }


    @RequestMapping(value = "/viewTestObjective*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
        String lab = "";
        String department = user.getDepartment();
        if (user.getLastLab() != null) {
            lab = user.getLastLab();
        }
        if (department != null) {
            if(lab.isEmpty()) {
                if(department.indexOf(",") > 0) {
                    lab = department.substring(0, department.indexOf(","));
                } else {
                    lab = department;
                }
            }
        }
        return new ModelAndView().addObject("lab", lab);
    }

    /**
     * 选择左侧客户时加载检验目的
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/data*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String customerid = request.getParameter("customerid");
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String lab = request.getParameter("lab");
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        String query = request.getParameter("query");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        List<DSF_ylxh> list = new ArrayList<DSF_ylxh>();
        int size = 0;
        try{
            size = dsf_ylxhManager.getSizeByLab(query,lab);
        }catch (Exception e){
            e.printStackTrace();
        }
        DataResponse dataResponse = new DataResponse();
        list = dsf_ylxhManager.getYlxhByLab(query,lab,start,end,sidx,sord,customerid);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for(DSF_ylxh y : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",y.getId());
            map.put("ylxh", y.getYlxh());
            map.put("ylmc", y.getYlmc());
            map.put("english", y.getEnglish());
            map.put("mzpb", y.getMzpb());
            map.put("zypb", y.getZypb());
            map.put("mzpbStr", y.getMzpb() == 1 ? "是" : "否");
            map.put("zypbStr", y.getZypb() == 1 ? "是" : "否");
            map.put("price", y.getPrice());
            map.put("qbgdd", y.getQbgdd());
            map.put("qbgsj", y.getQbgsj());
            map.put("yblx", y.getYblx());
            map.put("bbl", y.getBbl());
            map.put("sglx", y.getSglx());
            map.put("ptest", ConvertUtil.null2String(y.getProfiletest()));
            map.put("ptest2", ConvertUtil.null2String(y.getProfiletest2()));
            map.put("ptest3", ConvertUtil.null2String(y.getProfiletest3()));
            map.put("customerid",y.getCustomerid());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }




    @RequestMapping(value = "/editYlsf*", method = RequestMethod.POST)
    @ResponseBody
    public String editYlxh(@ModelAttribute DSF_ylxh dsf_ylxh) throws Exception {
        JSONObject success = new JSONObject();
        try {
            dsf_ylxhManager.save(dsf_ylxh);
            success.put("success", "0");
        } catch (Exception e) {
           // log.error(e.getMessage(), e);
            e.printStackTrace();
            success.put("success", "1");
        }
        return success.toString();
    }

    @RequestMapping(value = "/ajax/getTests*", method = RequestMethod.GET)
    @ResponseBody
    public String getTests(HttpServletRequest request, HttpServletResponse response) {
        JSONObject obj = new JSONObject();
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            DSF_ylxh y = dsf_ylxhManager.get(id);
            if(idMap == null || idMap.size() == 0) {
                initMap();
            }
            String profiletest = ConvertUtil.null2String(y.getProfiletest());
            String profiletest2 = ConvertUtil.null2String(y.getProfiletest2());
            String profiletest3 = ConvertUtil.null2String(y.getProfiletest3());
            if(!profiletest.isEmpty()) {
                JSONArray jsonArray = new JSONArray();
                for(String s : profiletest.split(",")) {
                    if(idMap.containsKey(s)) {
                        JSONObject o = new JSONObject();
                        o.put("id", s);
                        o.put("name", idMap.get(s).getName());
                        jsonArray.put(o);
                    }
                }
                obj.put("profiletest", jsonArray);
            }
            if(!profiletest2.isEmpty()) {
                JSONArray jsonArray = new JSONArray();
                for(String s : profiletest2.split(",")) {
                    if(idMap.containsKey(s)) {
                        JSONObject o = new JSONObject();
                        o.put("id", s);
                        o.put("name", idMap.get(s).getName());
                        jsonArray.put(o);
                    }
                }
                obj.put("profiletest2", jsonArray);
            }
            if(!profiletest3.isEmpty()) {
                JSONArray jsonArray = new JSONArray();
                for(String s : profiletest3.split(",")) {
                    if(idMap.containsKey(s)) {
                        JSONObject o = new JSONObject();
                        o.put("id", s);
                        o.put("name", idMap.get(s).getName());
                        jsonArray.put(o);
                    }
                }
                obj.put("profiletest3", jsonArray);
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/editProfile*", method = RequestMethod.POST)
    @ResponseBody
    public String editProfile(HttpServletRequest request) throws Exception {
        JSONObject success = new JSONObject();
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            int type = Integer.parseInt(request.getParameter("type"));
            String edit = request.getParameter("edit");
            String indexId = request.getParameter("index");
            DSF_ylxh y = dsf_ylxhManager.get(id);
            String profiletest = "";
            if(type == 1) {
                profiletest = ConvertUtil.null2String(y.getProfiletest());
            } else if(type == 2) {
                profiletest = ConvertUtil.null2String(y.getProfiletest2());
            } else {
                profiletest = ConvertUtil.null2String(y.getProfiletest3());
            }
            if(edit.equals("add")) {
                profiletest += indexId + ",";
            } else {
                profiletest = profiletest.replace(indexId + ",", "");
            }
            if(type == 1) {
                y.setProfiletest(profiletest);
            } else if(type == 2) {
                y.setProfiletest2(profiletest);
            } else {
                y.setProfiletest3(profiletest);
            }
            dsf_ylxhManager.save(y);
            success.put("success", profiletest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            success.put("success", "0");
        }
        return success.toString();
    }
}
