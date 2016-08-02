package com.smart.webapp.controller.micro;

import com.smart.model.lis.Ylxh;
import com.smart.model.micro.CultureMedium;
import com.smart.model.micro.TestCase;
import com.smart.model.micro.TestCaseDetails;
import com.smart.model.rule.Index;
import com.smart.service.lis.YlxhManager;
import com.smart.service.micro.CultureMediumManager;
import com.smart.service.micro.TestCaseDetailsManager;
import com.smart.service.micro.TestCaseManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/19 15:20
 * @Version:
 */
@Controller
@RequestMapping("/micro/testcase*")
public class TestCaseController {

    @Autowired
    private TestCaseManager testCaseManager = null;

    @Autowired
    private TestCaseDetailsManager testCaseDetailsManager = null;

    @Autowired
    private YlxhManager ylxhManager = null;

    @Autowired
    private CultureMediumManager  cultureMediumManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<CultureMedium> cultureMediums = cultureMediumManager.getAll(); //培养基信息
        JSONArray datas = new JSONArray();
        for(CultureMedium info :cultureMediums) {
            JSONObject obj = new JSONObject();
            obj.put("id", ConvertUtil.null2String(info.getId()));
            obj.put("name", ConvertUtil.null2String(info.getName()));
            datas.put(obj);
        }
        ModelAndView view = new ModelAndView();
        view.addObject("antibiotics",datas);
        return view;
    }
    /**
     * 查询信息
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
        List<Ylxh> list =  ylxhManager.getTest("1160703,3120401");
        size = list.size();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(Ylxh info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getYlxh()));
            map.put("name", ConvertUtil.null2String(info.getYlmc()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping( value = "/getTestCaseDetails" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public String getTestCaseDetails(HttpServletRequest request, HttpServletResponse response)throws JSONException {

        String testCaseId = ConvertUtil.null2String(request.getParameter("testCaseId"));
        List<Object[]> testCaseDetailsList = testCaseDetailsManager.getDetails(testCaseId,0,0,"","");
        //JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        for(int i=0;i<testCaseDetailsList.size();i++) {
            TestCaseDetails info = (TestCaseDetails)testCaseDetailsList.get(i)[0];
            obj.put(info.getCultureMediumId(),info.getCultureMediumId());
        }
        return obj.toString();
    }

    /**
     * 明细列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping( value = "/getDetailsList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getDetailsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String testCaseId = ConvertUtil.null2String(request.getParameter("testCaseId"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        size = testCaseDetailsManager.getDetailsCount(testCaseId,start,end,sidx,sord);
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<Object[]> list = new ArrayList<Object[]>();
        list = testCaseDetailsManager.getDetails(testCaseId,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(int i=0;i<list.size();i++) {
            TestCaseDetails info = (TestCaseDetails)list.get(i)[0];
            CultureMedium cultureMedium = (CultureMedium)list.get(i)[1];
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("testCaseId", ConvertUtil.null2String(info.getTestCaseId()));
            map.put("cultureMediumId", ConvertUtil.null2String(info.getCultureMediumId()));
            map.put("cultureMedium", ConvertUtil.null2String(cultureMedium.getName()));
            map.put("cultureMethod", ConvertUtil.null2String(info.getCultureMethod()));
            map.put("vaccinateMethod", ConvertUtil.null2String(info.getVaccinateMethod()));
            map.put("stainingMethod", ConvertUtil.null2String(info.getStainingMethod()));
            map.put("humidity", ConvertUtil.null2String(info.getHumidity()));
            map.put("temperature", ConvertUtil.null2String(info.getTemperature()));
            map.put("air", ConvertUtil.null2String(info.getAir()));
            map.put("cycle", ConvertUtil.null2String(info.getCycle()));
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
        String id = ConvertUtil.null2String(request.getParameter("id"));

//        int reportDays = ConvertUtil.getIntValue(request.getParameter("reportDays"));
//        int lablenumber = ConvertUtil.getIntValue(request.getParameter("lablenumber"));
//        int autoReportDays = ConvertUtil.getIntValue(request.getParameter("autoReportDays"));

        String datas[] = request.getParameterValues("cultureMediumList") ;

        List<TestCaseDetails> testCaseDetailsList = new ArrayList<TestCaseDetails>();
        for(int i=0;i< datas.length;i++){
            TestCaseDetails testCaseDetails = new TestCaseDetails();
            testCaseDetails.setCultureMediumId(datas[i]);
            testCaseDetails.setTestCaseId(id);
            testCaseDetailsList.add(testCaseDetails);
        }
        JSONObject success = new JSONObject();
        try {
            //testCaseDetailsManager.removeByTestCaseId(id);
            testCaseDetailsManager.saveDetails(testCaseDetailsList);  //批量保存明细数据
        }catch (Exception e){
            e.printStackTrace();
        }
        success.put("success","0");
        return success.toString();

    }
    @RequestMapping(value="/saveDetail*",method=RequestMethod.POST)
    @ResponseBody
    public void SaveDetail(@ModelAttribute TestCaseDetails testCaseDetails, HttpServletRequest request, HttpServletResponse response){
        testCaseDetailsManager.save(testCaseDetails);
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
    public String delete(@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws  Exception{

        JSONObject result = new JSONObject();
        try{
            testCaseDetailsManager.removeByTestCaseId(id);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }
    }

    /**
     * 删除明细
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/deleteDetails*",method=RequestMethod.POST)
    @ResponseBody
    public String deleteDetails(@RequestParam(value = "testCaseId") String testCaseId,@RequestParam(value = "detailId") String detailId, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        JSONObject result = new JSONObject();
        try{
            testCaseDetailsManager.removeById(testCaseId,detailId);
            result.put("susess","0");
            return result.toString();
        }catch (Exception ex){
            result.put("susess", ex.getMessage());
            return result.toString();
        }
    }
}
