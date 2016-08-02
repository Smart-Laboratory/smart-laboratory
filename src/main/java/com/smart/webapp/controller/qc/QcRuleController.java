package com.smart.webapp.controller.qc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.qc.QcRule;
import com.smart.service.qc.QcRuleManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/qc/rule*")
public class QcRuleController {
	
	@Autowired
	private QcRuleManager qcRuleManager = null;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/list*",method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getRuleList( HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
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
		List<QcRule> list = new ArrayList<QcRule>();
		list = qcRuleManager.getRuleList(query,start,end,sidx,sord);

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		int x = list.size() % (row == 0 ? list.size() : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (list.size() + x) / (row == 0 ? list.size() : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		for(QcRule info :list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", info.getId());
			map.put("name", info.getName());
			map.put("describe", info.getDescribe());
			map.put("inuse", info.getInuse());
			map.put("useinfo", info.getUseInfo());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
     * 新增质控规则
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/add*",method=RequestMethod.POST)
    @ResponseBody
    public String addRule(@ModelAttribute @Validated QcRule qcRule, HttpServletRequest request, HttpServletResponse response)throws  Exception{
        JSONObject success = new JSONObject();
        try {
        	qcRuleManager.save(qcRule);
        	success.put("success","0");
        } catch(Exception e) {
        	success.put("success", "新增质控规则失败！");
        }
        return success.toString();
    }
    
    /**
     * 编辑质控规则
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/edit*",method=RequestMethod.POST)
    @ResponseBody
    public String editRule(@ModelAttribute @Validated QcRule qcRule, HttpServletRequest request, HttpServletResponse response)throws  Exception{
        JSONObject success = new JSONObject();
        try {
        	qcRuleManager.save(qcRule);
        	success.put("success","0");
        } catch(Exception e) {
        	success.put("success", "质控规则保存失败！");
        }
        return success.toString();
    }
    
    /**
     * 删除质控规则
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/delete*",method=RequestMethod.POST)
    @ResponseBody
    public String deleteRule(@ModelAttribute @Validated QcRule qcRule, HttpServletRequest request, HttpServletResponse response)throws  Exception{
        JSONObject success = new JSONObject();
        try {
        	System.out.println(qcRule.getId());
        	qcRuleManager.remove(qcRule.getId());
        	success.put("success","0");
        } catch(Exception e) {
        	success.put("success", "质控规则删除失败！");
        }
        success.put("success","0");
        return success.toString();
    }
}
