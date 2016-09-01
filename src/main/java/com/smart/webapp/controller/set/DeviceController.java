package com.smart.webapp.controller.set;

import com.smart.model.Dictionary;
import com.smart.model.lis.Device;
import com.smart.service.DictionaryManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.DeviceTypeUtil;
import com.smart.webapp.util.SectionUtil;
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
 * Title: DeviceController
 * Description:仪器设备
 *
 * @Author:zhou
 * @Date:2016/5/25 10:49
 * @Version:
 */
@Controller
@RequestMapping("/set/device*")
public class DeviceController {
    @Autowired
    DictionaryManager dictionaryManager = null;

    @Autowired
    SectionManager sectionManager = null;

    @Autowired
    DeviceManager deviceManager = null;

    /**
     * 从字典表获取仪器类别信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTypeList*", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getDeviceTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //仪器类别 type=3
        List<Dictionary> dictionaryList = dictionaryManager.getDictionaryList("", "3", 0, 0, "", "");
        JSONArray nodes = new JSONArray();
        for (Dictionary dictionary : dictionaryList) {
            JSONObject info = new JSONObject();
            info.put("id", dictionary.getId());
            info.put("name", dictionary.getValue());
            info.put("type", dictionary.getType());
            nodes.put(info);
        }
        return nodes.toString();
    }

    /**
     * 获取仪器列表信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDeviceList*")
    @ResponseBody
    public DataResponse getDeviceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        List<Device> list = new ArrayList<Device>();
        int size = deviceManager.getDeviceCount(query, type);

        list = deviceManager.getDeviceList(query, type, start, end, sidx, sord);

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for (Device info : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("type", info.getType());
            map.put("typename", DeviceTypeUtil.getInstance(dictionaryManager).getValue(info.getType()));
            map.put("name", info.getName());
            map.put("lab", info.getLab());
            map.put("labname", DepartUtil.getInstance(sectionManager).getValue(info.getLab()));
            map.put("comport", info.getComport());
            map.put("baudrate", info.getBaudrate());
            map.put("parity", info.getParity());
            map.put("databit", info.getDatabit());
            map.put("stopbit", info.getStopbit());
            map.put("handshark", info.getHandshake());
            map.put("datawind", info.getDatawind());
            map.put("status", info.getStatus());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        //仪器类型
        Map<String, String> typeList = DeviceTypeUtil.getInstance(dictionaryManager).getMap();
        //实验室部门信息
        Map<String, String> departList = DepartUtil.getInstance(sectionManager).getMap();
//        for(String key:typeList.keySet()){
//            System.out.println(key+"====="+typeList.get(key));
//        }
        modelAndView.addObject("typeList", typeList);
        modelAndView.addObject("departList", departList);
        return modelAndView;
    }

    /**
     * 保存仪器信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveDevice*", method = RequestMethod.POST)
    @ResponseBody
    public String saveDevice(@ModelAttribute @Validated Device device, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        JSONObject success = new JSONObject();
        String method = request.getParameter("method");
        //System.out.println("method==>"+method);
        if ("add".equals(method)) {
            device.setStatus(1);
            if (deviceManager.getDeviceByCode(device.getId()) != null) {
                success.put("success", "编号已经存在");
                return success.toString();
            }
        }
        deviceManager.save(device);
        success.put("success", "0");
        return success.toString();
    }

    /**
     * 删除仪器
     *
     * @param id       仪器ID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteDevice*", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //检测状态
        Device device = deviceManager.getDeviceByCode(id);
        JSONObject result = new JSONObject();
        if (device.getInuse() == 1) {
            result.put("susess", "仪器己在使用中,不允许删除！");
            return result.toString();
        }
        try {
            deviceManager.remove(device);
            result.put("susess", "0");
            return result.toString();
        } catch (Exception ex) {
            result.put("susess", ex.getMessage());
            return result.toString();
        }

    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/getSearchList*")
    @ResponseBody
    public String getSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String query = request.getParameter("query");
        List<Device> deviceList = deviceManager.getDeviceList(query.toUpperCase());
        if(query.trim().isEmpty())
            deviceList = deviceManager.getAll();

        List<String> list = new ArrayList<String>();
        for(Device d : deviceList){
            list.add(d.getId()+""+d.getName());
        }

        JSONObject o = new JSONObject();
        o.put("list",list);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

}
