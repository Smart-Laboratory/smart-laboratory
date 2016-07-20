package com.smart.webapp.controller.micro;

import com.smart.model.micro.CultureMedium;
import com.smart.model.micro.MicroItemInfo;
import com.smart.service.micro.CultureMediumManager;
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
 * Title: .IntelliJ IDEA
 * Description:培养基
 *
 * @Author:zhou
 * @Date:2016/7/6 11:33
 * @Version:
 */
@Controller
@RequestMapping("/micro/culturemedium*")
public class CultureMediumController {

    @Autowired
    private CultureMediumManager cultureMediumManager = null;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    /**
     * 查询培养基信息
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
        size = cultureMediumManager.getCultureMediumsCount(query,start,end,sidx,sord);
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<CultureMedium> list = new ArrayList<CultureMedium>();
        list = cultureMediumManager.getCultureMediums(query,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(CultureMedium info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("name", ConvertUtil.null2String(info.getName()));
            map.put("shortname", ConvertUtil.null2String(info.getShortName()));
            map.put("air",ConvertUtil.null2String(info.getAir()));
            map.put("temperature",ConvertUtil.null2String(info.getTemperature()));
            map.put("humidity",ConvertUtil.null2String(info.getHumidity()));
            map.put("vaccinateMethod",ConvertUtil.null2String(info.getVaccinateMethod()));
            map.put("cultureMethod",ConvertUtil.null2String(info.getCultureMethod()));
            map.put("stainingMethod",ConvertUtil.null2String(info.getStainingMethod()));
            map.put("cycle",ConvertUtil.null2String(info.getCycle()));
            map.put("isVaccinate",ConvertUtil.null2String(info.getIsVaccinate()));
            map.put("isCulture",ConvertUtil.null2String(info.getIsCulture()));
            map.put("isSmear",ConvertUtil.null2String(info.getIsSmear()));
            map.put("isInstrument",ConvertUtil.null2String(info.getIsInstrument()));
            map.put("isIdentification",ConvertUtil.null2String(info.getIsIdentification()));
            map.put("state",ConvertUtil.null2String(info.getState()));
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
    public String save(@ModelAttribute  @Validated CultureMedium cultureMedium,  HttpServletRequest request, HttpServletResponse response)throws  Exception{
        Long id = ConvertUtil.getLongValue(request.getParameter("id"));
        JSONObject success = new JSONObject();
        cultureMediumManager.save(cultureMedium);
        success.put("success","0");
        return success.toString();
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

        CultureMedium  cultureMedium = cultureMediumManager.get(id);
        JSONObject result = new JSONObject();
        try{
            cultureMediumManager.remove(cultureMedium);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }

    }
}
