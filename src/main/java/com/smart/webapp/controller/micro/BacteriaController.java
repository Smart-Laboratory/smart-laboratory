package com.smart.webapp.controller.micro;


import com.smart.model.rule.Index;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * Title: BacteriaController
 * Description:微生物-细菌种类控制器
 *
 * @Author:zhou
 * @Date:2016/7/4 15:38
 * @Version:
 */

@Controller
@RequestMapping("/micro/bacteria*")
public class BacteriaController {

    @Autowired
    private IndexManager indexManager = null;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    /**
     * 查询微生物列表信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type =  request.getParameter("type");
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String query = ConvertUtil.null2String(request.getParameter("query"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        boolean isAdmin  = request.getRemoteUser().equals("admin");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        List<Index> list = new ArrayList<Index>();
        int size = 0;
        try{
            size = indexManager.getBacteriaListCount(query,start,end,sidx,sord);
        }catch (Exception e){
            e.printStackTrace();
        }
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        list = indexManager.getBacteriaList(query,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

        for(Index info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("indexid", ConvertUtil.null2String(info.getIndexId()));
            map.put("name", ConvertUtil.null2String(info.getName()));
            map.put("english", ConvertUtil.null2String(info.getEnglish()));
            map.put("sampletype", ConvertUtil.null2String(info.getSampleFrom()));
            map.put("testclass", ConvertUtil.null2String(info.getTestClass()));
            map.put("labdepartment", ConvertUtil.null2String(info.getLabdepartment()));
            map.put("unit", ConvertUtil.null2String(info.getUnit()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }
    /**
     * 保存细菌信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/saveBacteria*",method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        Long id = ConvertUtil.getLongValue(request.getParameter("id"));
        String indexid = ConvertUtil.null2String(request.getParameter("indexid"));
        String name = ConvertUtil.null2String(request.getParameter("name"));
        String english = ConvertUtil.null2String(request.getParameter("english"));
        Index index = null;
        if(id >0){
            index = indexManager.get(id);

        }else{
            index = new Index();
        }
        index.setTestClass("细菌");
        index.setName(name);
        index.setIndexId(indexid);
        index.setEnglish(english);
        JSONObject success = new JSONObject();
        indexManager.save(index);
        success.put("success","0");
        return success.toString();
    }

    /**
     * 删除细菌
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/deleteBacteria*",method=RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws  Exception{

        Index index = indexManager.getBacteriaById(id);
        JSONObject result = new JSONObject();
        try{
            indexManager.remove(index);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }

    }
}
