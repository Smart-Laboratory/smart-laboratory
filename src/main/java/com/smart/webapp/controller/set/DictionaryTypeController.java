package com.smart.webapp.controller.set;

import com.smart.model.DictionaryType;
import com.smart.service.DictionaryManager;
import com.smart.service.DictionaryTypeManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: DictionaryController
 * Description:字典类别
 *
 * @Author:zhou
 * @Date:2016/5/17 13:53
 * @Version:
 */
@Controller
@RequestMapping("/set/dictionaryType*")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeManager dictionaryTypeManager = null;
    @Autowired
    private DictionaryManager dictionaryManager = null;

    /**
     * 获取字典列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="/getList", method ={RequestMethod.POST, RequestMethod.GET},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<DictionaryType> list = dictionaryTypeManager.getAll();
        JSONArray nodes = new JSONArray();
        for(DictionaryType dictionaryType:list){
            JSONObject node = new JSONObject();
            node.put("id",dictionaryType.getId());
            node.put("name",dictionaryType.getTypeName());
            nodes.put(node);
        }
        return nodes.toString();
    }

    /**
     * 保存字典类别
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveType",method = RequestMethod.POST)
    public ModelAndView saveType(HttpServletRequest request, HttpServletResponse response)throws Exception{
        DictionaryType dictionaryType = new DictionaryType();
        String name = request.getParameter("name");
        dictionaryType.setTypeName(name);
        dictionaryTypeManager.save(dictionaryType);
        return new ModelAndView("redirect:/set/dictionary");
    }

    /**
     * 删除字典类别
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/removeType*", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, String> removeType(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        //检查是否包含明细数据
        Map<String, String> map = new HashMap<String, String>();
        int size = dictionaryManager.getDictionaryCount("", id + "");
        if (size > 0) {
            //包含明细数据不允许删除
            map.put("success", "false");
        } else{
            try {
                dictionaryTypeManager.remove(id);
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("success", "true");
        }
        return map;
    }
}
