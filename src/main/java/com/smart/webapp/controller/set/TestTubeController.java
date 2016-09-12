package com.smart.webapp.controller.set;

import com.smart.model.lis.TestTube;
import com.smart.service.lis.TestTubeManager;
import com.smart.webapp.util.DataResponse;
import org.codehaus.jettison.json.JSONArray;
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
 * Created by zcw on 2016/9/9.
 */
@Controller
@RequestMapping("/set/testTube*")
public class TestTubeController {
    @Autowired
    TestTubeManager testTubeManager = null;


    /**
     * 获取列表信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getTestTubeList*")
    @ResponseBody
    public DataResponse getTestTubeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
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
        List<TestTube> list = new ArrayList<TestTube>();
        int size = testTubeManager.getTestTubeCount(query);
        list = testTubeManager.getTestTubeList(query, start, end, sidx, sord);

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for (TestTube info : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("name", info.getName());
            map.put("price", info.getPrice());
            map.put("feeItemId", info.getFeeItemId());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    /**
     * 保存信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save*", method = RequestMethod.POST)
    @ResponseBody
    public String save(@ModelAttribute @Validated TestTube testTube, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        JSONObject success = new JSONObject();
        String method = request.getParameter("method");
        //System.out.println("method==>"+method);
        try {
            testTubeManager.save(testTube);
        }catch (Exception e){
            e.printStackTrace();
        }
        success.put("success", "0");
        return success.toString();
    }

    /**
     * 删除
     *
     * @param id       ID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/delete*", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //检测状态
        TestTube testTube = testTubeManager.get(id);
        JSONObject result = new JSONObject();

        try {
            testTubeManager.remove(testTube);
            result.put("susess", "0");
            return result.toString();
        } catch (Exception ex) {
            result.put("susess", ex.getMessage());
            return result.toString();
        }

    }
}
