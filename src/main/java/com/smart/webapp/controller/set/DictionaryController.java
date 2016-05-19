package com.smart.webapp.controller.set;

import com.smart.model.Dictionary;
import com.smart.model.DictionaryType;
import com.smart.service.DictionaryManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse  response){
        return new ModelAndView();
    }
    @RequestMapping( value = "/getList" ,method = RequestMethod.GET )
    public ModelAndView getList(@RequestParam(required = false, value = "q") String query) throws Exception {
        Dictionary dictionary = new Dictionary();
        List<Dictionary> list = dictionaryManager.getDictionaryList(dictionary,0,10,"id","asc");
//        for(Dictionary dic :list){
//            System.out.println("id==>"+dic.getType());
//            System.out.println("id==>"+dic.getId());
//            System.out.println("id==>"+dic.getValue());
//        }
        return new ModelAndView().addObject("list", list.size() > 0 ? list : null);
    }

    @RequestMapping(value = "/saveDictionary",method = RequestMethod.POST)
    public void saveDictionary(@ModelAttribute("dictionary") Dictionary dictionary,
                                       HttpServletResponse response, Model model){

        Dictionary dictionary1 = dictionaryManager.save(dictionary);
        model.addAttribute(dictionary1);
    }
}
