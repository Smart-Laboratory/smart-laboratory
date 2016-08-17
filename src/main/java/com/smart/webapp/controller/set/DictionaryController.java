package com.smart.webapp.controller.set;

import com.smart.model.Dictionary;
import com.smart.model.DictionaryType;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: DictionaryController
 * Description: 字典
 *
 * @Author:zhou
 * @Date:2016/5/17 17:05
 * @Version:
 */
@Controller
@RequestMapping("/set/dictionary*")
public class DictionaryController {
    @Autowired
    private DictionaryManager dictionaryManager = null;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response ){
        return new ModelAndView();
    }

    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type =  request.getParameter("type");
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String query = request.getParameter("query");
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        List<Dictionary> list = new ArrayList<Dictionary>();
        int size = dictionaryManager.getDictionaryCount(query,type);

        //System.out.println("type--->"+type);
        list =dictionaryManager.getDictionaryList(query,type,start,end,sidx,sord);

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for(Dictionary info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("sign", info.getSign());
            map.put("value", info.getValue());
            map.put("type", info.getType());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/saveDictionary",method = RequestMethod.POST)
    public ModelAndView saveDictionary(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        Dictionary dictionary = new Dictionary();
        Long id = Long.parseLong(request.getParameter("id"));
        String sign = request.getParameter("sign");
        int type = Integer.parseInt(request.getParameter("type"));
        String value = request.getParameter("value");
        dictionary.setId(id);
        dictionary.setType(type);
        dictionary.setSign(sign);
        dictionary.setValue(value);
        dictionary = dictionaryManager.save(dictionary);
        SampleUtil.updateMap(dictionary);
        return new ModelAndView("redirect:/set/dictionary?type="+type);
    }
    @RequestMapping("/remove")
    public void removeDictionary(HttpServletRequest request,HttpServletResponse response){
        Long id = Long.parseLong(request.getParameter("id"));
        Dictionary dictionary = dictionaryManager.get(id);
        SampleUtil.remove(dictionary);
        dictionaryManager.remove(id);
    }
}
