package com.smart.webapp.controller.set;

import com.smart.model.DictionaryType;
import com.smart.service.DictionaryTypeManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
}
