package com.smart.webapp.controller.set;

import com.smart.model.lis.ReceivePoint;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SectionUtil;
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
 * Created by yuzh on 2016/9/27.
 */
@Controller
@RequestMapping(value = "/set/point*")
public class PointSetController {

    @Autowired
    private ReceivePointManager receivePointManager = null;

    @Autowired
    private SectionManager sectionManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        //实验室部门信息
        Map<String, String> departList = SectionUtil.getInstance(sectionManager).getLabMap();
        view.addObject("departList", departList);
        return  view;
    }

    /**
     * 获取接收点和送出点设置基本信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getList*")
    @ResponseBody
    public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        List<ReceivePoint> list = new ArrayList<ReceivePoint>();
        int size = receivePointManager.getPointCount(query, type);

        list = receivePointManager.getList(query, type, start, end, sidx, sord);

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for (ReceivePoint info : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("type", info.getType());
            map.put("typeName", info.getType() == 0 ? "接收点" : "送出点");
            map.put("name", info.getName());
            map.put("lab", info.getLab());
            map.put("code", info.getCode());
            map.put("labName", SectionUtil.getInstance(sectionManager).getValue(info.getLab()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 保存样本流转地点信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePoint*", method = RequestMethod.POST)
    @ResponseBody
    public String saveDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String method = request.getParameter("method");
        JSONObject success = new JSONObject();
        ReceivePoint receivePoint = new ReceivePoint();
        if ("add".equals(method)) {
            if (id != null && !id.isEmpty() && receivePointManager.get(Long.parseLong(id)) != null) {
                success.put("success", "编号已经存在,添加失败！");
                return success.toString();
            }
            receivePoint.setCode(request.getParameter("code"));
            receivePoint.setLab(request.getParameter("lab"));
            receivePoint.setName(request.getParameter("name"));
            receivePoint.setType(Integer.parseInt(request.getParameter("type")));
        } else {
            receivePoint = receivePointManager.get(Long.parseLong(id));
            receivePoint.setCode(request.getParameter("code"));
            receivePoint.setLab(request.getParameter("lab"));
            receivePoint.setName(request.getParameter("name"));
            receivePoint.setType(Integer.parseInt(request.getParameter("type")));
        }
        try {
            receivePointManager.save(receivePoint);
            success.put("success", "0");
            return success.toString();
        } catch (Exception ex) {
            success.put("success", ex.getMessage());
            return success.toString();
        }
    }

    /**
     * 删除样本流转地点信息
     *
     * @param id       地点ID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deletePoint*", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //检测状态
        ReceivePoint receivePoint = receivePointManager.get(id);
        JSONObject result = new JSONObject();
        try {
            receivePointManager.remove(receivePoint);
            result.put("success", "0");
            return result.toString();
        } catch (Exception ex) {
            result.put("success", ex.getMessage());
            return result.toString();
        }
    }

}
