package com.smart.webapp.controller.dsf;

import com.smart.model.dsf.DSF_ControlTestItems;
import com.smart.model.dsf.DSF_TestItems;
import com.smart.model.rule.Index;
import com.smart.service.dsf.DSF_ControlTestItemsManager;
import com.smart.service.dsf.DSF_TestItemsManager;
import com.smart.service.lis.YlxhManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/dsf/controlTestItems/*")
public class ControlTestItemsController {
    private static Log log = LogFactory.getLog(ControlTestItemsController.class);
    @Autowired
    private YlxhManager ylxhManager;
    @Autowired
    protected IndexManager indexManager;
    @Autowired
    protected DSF_ControlTestItemsManager dsf_controlTestItemsManager ;
    @Autowired
    protected DSF_TestItemsManager dsf_testItemsManager;


    protected Map<String, Index> idMap = new HashMap<String, Index>();
    protected synchronized void initMap() {
        List<Index> list = indexManager.getAll();
        for (Index t : list) {
            idMap.put(t.getIndexId(), t);
        }
    }



    @RequestMapping(value = "/viewControlTestItems*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewControlTestItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(value = "/ajax/getTests*", method = RequestMethod.GET)
    @ResponseBody
    public String getTests(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String customerid = ConvertUtil.null2String(request.getParameter("customerid"));
            DSF_TestItems testItems = dsf_testItemsManager.get(id);

            Map<String, DSF_ControlTestItems> initDZMap = new HashMap<String, DSF_ControlTestItems>();
            List<DSF_ControlTestItems> list = dsf_controlTestItemsManager.getAll();
            for (DSF_ControlTestItems t : list) {
                if (t.getCustomerid().equals(customerid)) {
                    initDZMap.put(t.getCustomeritems(), t);
                }
            }
            JSONObject o = new JSONObject();
            if(initDZMap.containsKey(testItems.getIndexId())){
                o.put("id", testItems.getId());
                o.put("name", testItems.getName());
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/data*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        String customerid = request.getParameter("customerid");
        String query = request.getParameter("query");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        Map<String, DSF_ControlTestItems> initDZMap = new HashMap<String, DSF_ControlTestItems>();
        List<DSF_ControlTestItems> ctiList = dsf_controlTestItemsManager.getAll();
        for (DSF_ControlTestItems t : ctiList) {
            if (t.getCustomerid().equals(customerid)) {
                initDZMap.put(t.getCustomeritems(), t);
            }
        }
        List<DSF_TestItems> list = new ArrayList<DSF_TestItems>();
        int size = 0;
        try {
            size = dsf_controlTestItemsManager.getSizeByCustomerid(query, customerid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataResponse dataResponse = new DataResponse();
        list = dsf_controlTestItemsManager.getYlxhByCustomerid(query, customerid, start, end, sidx, sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for (DSF_TestItems ti : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (initDZMap.containsKey(ti.getIndexId())) {
                //该ID是对照表的id
                map.put("dzid", initDZMap.get(ti.getIndexId()).getId());
                //通过对照表获取到的本地检验项目的ID
                map.put("localitems", initDZMap.get(ti.getIndexId()).getLocalitems());
                map.put("localitemsname", initDZMap.get(ti.getIndexId()).getLocalitemsname());
            } else {
                //该ID是对照表的id
                map.put("dzid", "");
                //通过对照表获取到的本地检验项目的ID
                map.put("localitems", "");
                map.put("localitemsname", "");
            }

            map.put("id", ti.getId());
            map.put("customerid", ti.getCustomerid());
            map.put("customeritemsname", ti.getName());
            map.put("customeritems", ti.getIndexId());

            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/editComparison*", method = RequestMethod.POST)
    @ResponseBody
    public String editProfile(HttpServletRequest request) throws Exception {
        JSONObject success = new JSONObject();
        try {
            String edit = request.getParameter("edit");
            String localIndex = request.getParameter("localIndex");
            String loaclName = request.getParameter("loaclName");
            String customeritems = request.getParameter("customeritems");
            String customeritemsname = request.getParameter("customeritemsname");
            String customerid = request.getParameter("customerid");
            String dzid = ConvertUtil.null2String(request.getParameter("dzid"));
            DSF_ControlTestItems dsf_controlTestItems = new DSF_ControlTestItems();
            if("add".equals(edit)){
                if(!"".equals(dzid)){
                    Long dzid_long = Long.parseLong(dzid);
                    dsf_controlTestItems = dsf_controlTestItemsManager.get(dzid_long);
                    dsf_controlTestItems.setLocalitems(localIndex);
                    dsf_controlTestItems.setLocalitemsname(loaclName);
                    dsf_controlTestItemsManager.save(dsf_controlTestItems);
                }else {
                    dsf_controlTestItems.setCustomerid(customerid);
                    dsf_controlTestItems.setCustomeritems(customeritems);
                    dsf_controlTestItems.setCustomeritemsname(customeritemsname);
                    dsf_controlTestItems.setLocalitems(localIndex);
                    dsf_controlTestItems.setLocalitemsname(loaclName);
                    dsf_controlTestItemsManager.save(dsf_controlTestItems);
                }
            }else{
                Long dzid_long = Long.parseLong(dzid);
                dsf_controlTestItemsManager.remove(dzid_long);
            }
            success.put("success", "1");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            success.put("success", "0");
        }
        return success.toString();
    }

    @RequestMapping(value = "/autoComparison*", method = RequestMethod.POST)
    @ResponseBody
    public String autoComparison(HttpServletRequest request) throws Exception {
        JSONObject success = new JSONObject();
        List<DSF_ControlTestItems> dsfControlTestItemsList = new ArrayList<DSF_ControlTestItems>();
        try {
            String customerid = request.getParameter("customerid");
            DSF_ControlTestItems dsf_controlTestItems = new DSF_ControlTestItems();

            List <Index>localList = new ArrayList<Index>();
            localList = indexManager.getAll();

            List <DSF_TestItems> clist = new ArrayList<DSF_TestItems>();
            clist = dsf_testItemsManager.getAll();
            for (DSF_TestItems dsfTestItems :clist){
                if(customerid.equals(dsfTestItems.getCustomerid())){
                    for (Index i :localList){
                        if(dsfTestItems.getName().equals(i.getName())){
                            dsf_controlTestItems.setCustomerid(customerid);
                            dsf_controlTestItems.setCustomeritems(dsfTestItems.getIndexId());
                            dsf_controlTestItems.setCustomeritemsname(dsfTestItems.getName());
                            dsf_controlTestItems.setLocalitems(i.getIndexId());
                            dsf_controlTestItems.setLocalitemsname(i.getName());
                            dsfControlTestItemsList.add(dsf_controlTestItems);
                        }
                    }
                }
            }
            dsf_controlTestItemsManager.saveAll(dsfControlTestItemsList);
            success.put("success", "1");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            success.put("success", "0");
        }
        return success.toString();
    }

}
