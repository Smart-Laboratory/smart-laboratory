package com.smart.webapp.controller.micro;

import com.smart.model.micro.MicroItemInfo;
import com.smart.service.micro.MicroItemInfoManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: .IntelliJ IDEA
 * Description:细菌培养结果
 *
 * @Author:zhou
 * @Date:2016/7/6 9:40
 * @Version:
 */
@Controller
@RequestMapping("/micro/cultureresult")
public class CultureResultController {

    @Autowired
    private MicroItemInfoManager microItemInfoManager = null;

    private static final  String CLASSNAME="培养结果";
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
        List<MicroItemInfo> list = new ArrayList<MicroItemInfo>();
        int size = 0;
        try{
            size = microItemInfoManager.getMicroItemInfosCount(CLASSNAME,query,start,end,sidx,sord);
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
        list = microItemInfoManager.getMicroItemInfos(CLASSNAME,query,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

        for(MicroItemInfo info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("indexid", ConvertUtil.null2String(info.getIndexId()));
            map.put("name", ConvertUtil.null2String(info.getName()));
            map.put("english", ConvertUtil.null2String(info.getEnglish()));
            map.put("type",ConvertUtil.null2String(info.getType()));
            map.put("typename",getTypeName(ConvertUtil.null2String(info.getType())));
            map.put("classname",ConvertUtil.null2String(info.getClassName()));
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
        String indexid = ConvertUtil.null2String(request.getParameter("indexid"));
        String name = ConvertUtil.null2String(request.getParameter("name"));
        String english = ConvertUtil.null2String(request.getParameter("english"));
        String type = ConvertUtil.null2String(request.getParameter("type"));
        MicroItemInfo microItemInfo = null;
        if(id >0){
            microItemInfo = microItemInfoManager.get(id);

        }else{
            microItemInfo = new MicroItemInfo();
        }
        microItemInfo.setClassName(CLASSNAME);
        microItemInfo.setName(name);
        microItemInfo.setIndexId(indexid);
        microItemInfo.setEnglish(english);
        microItemInfo.setType(type);
        JSONObject success = new JSONObject();
        microItemInfoManager.save(microItemInfo);
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

        MicroItemInfo microItemInfo = microItemInfoManager.getMicroItemInfo(CLASSNAME,id);
        JSONObject result = new JSONObject();
        try{
            microItemInfoManager.remove(microItemInfo);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }

    }

    private String getTypeName (String type){
        switch (type){
            case "0":
                return "阴性结果";
            case "1":
                return "阳性结果";
            case "2":
                return "其他结果";
            default:
                return "其他结果";
        }
    }
}
