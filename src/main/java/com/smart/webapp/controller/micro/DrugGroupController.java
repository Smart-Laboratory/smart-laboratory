package com.smart.webapp.controller.micro;

import com.smart.model.micro.DrugGroupDetails;
import com.smart.model.micro.MicroItemInfo;
import com.smart.model.rule.Index;
import com.smart.service.micro.DrugGroupDetailsManager;
import com.smart.service.micro.DrugGroupManager;
import com.smart.model.micro.DrugGroup;

import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.IndexMapUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: DrugGroupController
 * Description:微生物-抗生素组合
 *
 * @Author:zhou
 * @Date:2016/7/8 15:43
 * @Version:
 */

@Controller
@RequestMapping("/micro/druggroup*")
public class DrugGroupController {

    @Autowired
    private DrugGroupManager drugGroupManager = null;

    @Autowired
    private DrugGroupDetailsManager drugGroupDetailsManager = null;

    @Autowired
    private IndexManager indexManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Index> list = indexManager.getAntibioticList("",0,0,"","");
        JSONArray datas = new JSONArray();
        for(Index info :list) {
            JSONObject obj = new JSONObject();
            obj.put("id", ConvertUtil.null2String(info.getId()));
            obj.put("indexid", ConvertUtil.null2String(info.getIndexId()));
            obj.put("name", ConvertUtil.null2String(info.getName()));
            obj.put("english", ConvertUtil.null2String(info.getEnglish()));
            obj.put("testclass", ConvertUtil.null2String(info.getTestClass()));
            datas.put(obj);
        }
        ModelAndView view = new ModelAndView();
        view.addObject("antibiotics",datas);
        return view;
    }
    /**
     * 查询信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String query = ConvertUtil.null2String(request.getParameter("query"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        size = drugGroupManager.getDrugGroupsCount(query,start,end,sidx,sord);
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<DrugGroup> list = new ArrayList<DrugGroup>();
        list = drugGroupManager.getDrugGroups(query,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(DrugGroup info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("name", ConvertUtil.null2String(info.getName()));
            map.put("spellcode", ConvertUtil.null2String(info.getSpellCode()));
            map.put("state",ConvertUtil.null2String(info.getState()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping( value = "/getDrugDetails" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public String getDrugDetails(HttpServletRequest request, HttpServletResponse response)throws JSONException {

        String groupId = ConvertUtil.null2String(request.getParameter("groupId"));
        List<DrugGroupDetails> drugGroupDetails = drugGroupDetailsManager.getByGroupId(groupId);
        //JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        for(DrugGroupDetails info :drugGroupDetails){

            obj.put(info.getDrugId(),info.getDrugId());
        }
        return obj.toString();
    }

    /**
     * 抗生素明细列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping( value = "/getDetailsList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getDetailsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String groupId = ConvertUtil.null2String(request.getParameter("groupId"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        size = drugGroupDetailsManager.getDrugDetailsCount(groupId,start,end,sidx,sord);
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<Object[]> list = new ArrayList<Object[]>();
        list = drugGroupDetailsManager.getDrugDetails(groupId,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(int i=0;i<list.size();i++) {
            DrugGroupDetails info = (DrugGroupDetails)list.get(i)[0];
            Index index = (Index)list.get(i)[1];
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("drugId", ConvertUtil.null2String(info.getDrugId()));
            map.put("groupId", ConvertUtil.null2String(info.getGroupId()));
            map.put("drugName", ConvertUtil.null2String(index.getName()));
            map.put("quantitativeResult", ConvertUtil.null2String(info.getQuantitativeResult()));
            map.put("qualitativeResult", ConvertUtil.null2String(info.getQualitativeResult()));
            map.put("method", ConvertUtil.null2String(info.getMethod()));
            map.put("micrange", ConvertUtil.null2String(info.getMicrange()));
            map.put("kbrange", ConvertUtil.null2String(info.getKbrange()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 保存
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/save*",method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        Long id = ConvertUtil.getLongValue(request.getParameter("id"));
        String drugGroupName = ConvertUtil.null2String(request.getParameter("drugGroupName"));
        //String datas= ConvertUtil.null2String(request.getParameter("druglist"));
        String datas[] = request.getParameterValues("druglist") ;
        DrugGroup drugGroup = null;
        if(id > 0){
            drugGroup = drugGroupManager.get(id);
        }else{
            drugGroup = new DrugGroup();
        }
        drugGroup.setName(drugGroupName);
        drugGroup =drugGroupManager.save(drugGroup);
        Long drugGroupId = drugGroup.getId();

        List<DrugGroupDetails> drugGroupDetailsList = new ArrayList<DrugGroupDetails>();
        for(int i=0;i< datas.length;i++){
            DrugGroupDetails drugGroupDetails = new DrugGroupDetails();
            drugGroupDetails.setDrugId(datas[i]);               //抗生素ID
            drugGroupDetails.setGroupId(""+drugGroupId);        //药敏组ID
            drugGroupDetails.setState(0);
            drugGroupDetailsList.add(drugGroupDetails);
        }
        JSONObject success = new JSONObject();
        drugGroupDetailsManager.removeByGroupId(""+drugGroupId);
        drugGroupDetailsManager.saveDetails(drugGroupDetailsList);  //批量保存明细数据
        success.put("success","0");
        return success.toString();

    }
    @RequestMapping(value="/saveDetail*",method=RequestMethod.POST)
    @ResponseBody
    public void SaveDetail(@ModelAttribute DrugGroupDetails drugGroupDetails, HttpServletRequest request, HttpServletResponse response){
        drugGroupDetailsManager.save(drugGroupDetails);
    }
    /**
     * 删除
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/delete*",method=RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws  Exception{

        DrugGroup  drugGroup = drugGroupManager.get(id);
        JSONObject result = new JSONObject();
        try{
            drugGroupManager.remove(drugGroup);
            drugGroupDetailsManager.removeByGroupId(""+id);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }
    }

    /**
     * 删除明细
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/deleteDetails*",method=RequestMethod.POST)
    @ResponseBody
    public String deleteDetails(@RequestParam(value = "groupId") String groupId,@RequestParam(value = "drugId") String drugId, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        JSONObject result = new JSONObject();
        try{
            drugGroupDetailsManager.removeById(groupId,drugId);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }
    }
}
