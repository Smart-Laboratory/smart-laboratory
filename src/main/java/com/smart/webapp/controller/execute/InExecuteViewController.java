package com.smart.webapp.controller.execute;

import com.smart.model.DictionaryType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zcw on 2016/8/22.
 * 住院标本采集控制器
 */
@Controller
@RequestMapping(value = "/nursestation/inexecute*")
public class InExecuteViewController {
     @RequestMapping(method = RequestMethod.GET)
     public ModelAndView handleRequest (HttpServletRequest request, HttpServletResponse response){
          return new ModelAndView();
     }

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
         // List<DictionaryType> list = dictionaryTypeManager.getAll();
          JSONArray nodes = new JSONArray();
//          for(DictionaryType dictionaryType:list){
//               JSONObject node = new JSONObject();
//               node.put("id",dictionaryType.getId());
//               node.put("name",dictionaryType.getTypeName());
//               nodes.put(node);
//          }
          return nodes.toString();
     }

}
