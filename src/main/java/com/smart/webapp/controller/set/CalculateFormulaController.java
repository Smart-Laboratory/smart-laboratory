package com.smart.webapp.controller.set;

import com.smart.model.lis.CalculateFormula;
import com.smart.model.lis.CalculateFormulaVo;
import com.smart.model.lis.Device;
import com.smart.model.lis.TestReference;
import com.smart.model.rule.Index;
import com.smart.service.lis.CalculateFormulaManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.IndexMapUtil;
import com.smart.webapp.util.SampleUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.drools.core.util.index.IndexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Title: CalculateFormulaController
 * Description:计算公式
 *
 * @Author:zhou
 * @Date:2016/6/14 8:53
 * @Version:
 */
@Controller
@RequestMapping("/set/calculateformula*")
public class CalculateFormulaController {

    @Autowired
    private CalculateFormulaManager calculateFormulaManager = null;
    @Autowired
    private IndexManager indexManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    /**
     * 查询项目列表信息
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
        int page = ConvertUtil.getIntValue(request.getParameter("page"));
        int row = ConvertUtil.getIntValue(request.getParameter("rows"));
        String query = ConvertUtil.null2String(request.getParameter("query"));
        String sidx = ConvertUtil.null2String(request.getParameter("sidx"));
        String sord = ConvertUtil.null2String(request.getParameter("sord"));

        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        List<CalculateFormulaVo> list = new ArrayList<CalculateFormulaVo>();
        int size = calculateFormulaManager.getCalculateFormulaListCount(query,start,end,sidx,sord);
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        list = calculateFormulaManager.getCalculateFormulaList(query,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

        for(CalculateFormulaVo info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("testId", ConvertUtil.null2String(info.getCalculateFormula().getTestId()));
            map.put("testName", info.getTestName());
            map.put("formulaDescribe", ConvertUtil.null2String(info.getCalculateFormula().getFormulaDescribe()));
            map.put("excludeDescribe", ConvertUtil.null2String(info.getCalculateFormula().getExcludeDescribe()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 编辑公式信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxeditdformula*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView editFormula( HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("set/ajaxeditformula");
        String testId = ConvertUtil.null2String(request.getParameter("testid"));
        try {
            if (!testId.equals("")) {
                CalculateFormulaVo calculateFormulaVo = calculateFormulaManager.getCalculateFormulaByTestId(testId);
                CalculateFormula calculateFormula = calculateFormulaVo.getCalculateFormula();
                String formula = calculateFormula.getFormula();
                String formulaDescriber = calculateFormula.getFormulaDescribe();
                String formulaItem = calculateFormula.getFormulaItem();
                int testNumb = calculateFormula.getTestNumb();
                String[] arrTestItem = formulaItem.split(",");
                String tmpFormula = "";
                for (String str : arrTestItem) {
                    System.out.println(str);
                    formula = formula.replace(str, "@");
                }
                char[] operators = formula.toCharArray();
                List<String> formulaList = new ArrayList<String>();
                List<String> formulaDescribe = new ArrayList<String>();
                int i = 0;
                for (char c : operators) {
                    if (c == '@') {
                        formulaList.add(arrTestItem[i]);
                        String indexId = arrTestItem[i].substring(0, arrTestItem[i].indexOf("["));

                        System.out.print(indexId);
                        Index index = indexManager.getIndex(indexId);
                        if (index != null) formulaDescribe.add(index.getName());
                        i++;
                    } else {
                        formulaList.add(String.valueOf(c));
                        formulaDescribe.add(String.valueOf(c));
                    }
                }
                JSONObject jsFormula = new JSONObject();
                jsFormula.put("formula",formulaList);
                jsFormula.put("formulaDescribe",formulaDescribe);
                jsFormula.put("formulaItem",Arrays.asList(arrTestItem));
                view.addObject("formula",jsFormula);
                view.addObject("calculateFormula",calculateFormula);
                view.addObject("testName",calculateFormulaVo.getTestName());
                //System.out.println(formulaList.toString());
                //System.out.println(formulaDescribe.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }


    /**
     * 保存公式信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/saveInfo*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String saveInfo(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String testId = ConvertUtil.null2String(request.getParameter("testid"));
        String formula = ConvertUtil.null2String(request.getParameter("formula"));
        String formulaDescribe = ConvertUtil.null2String(request.getParameter("formuladescribe"));
        String formulaItem = ConvertUtil.null2String(request.getParameter("formulaitem"));
        int testNumb = ConvertUtil.getIntValue(request.getParameter("testnumb"),0);

        CalculateFormula calculateFormula = new CalculateFormula();
        calculateFormula.setTestId(testId);
        calculateFormula.setFormula(formula);
        calculateFormula.setFormulaDescribe(formulaDescribe);
        calculateFormula.setSampleType("1");
        calculateFormula.setTestNumb(testNumb);
        calculateFormula.setFormulaItem(formulaItem);
        try{
            calculateFormulaManager.save(calculateFormula);
            return new JSONObject().put("result", "true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return new JSONObject().put("result", "false").toString();
        }
    }

    /**
     * 删除项目
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFormula*",method =RequestMethod.POST)
    @ResponseBody
    public String deleteIndex(@RequestParam(value = "testid") String testid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            CalculateFormula calculateFormula =  calculateFormulaManager.getCalculateFormulaByTestId(testid).getCalculateFormula();
            calculateFormulaManager.remove(calculateFormula);
            return  new JSONObject().put("result","true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return  new JSONObject().put("result","false").toString();
        }
    }

}
