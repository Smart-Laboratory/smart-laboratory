package com.smart.webapp.controller.set;

import com.smart.model.lis.Device;
import com.smart.model.lis.Section;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import org.apache.cxf.common.util.StringUtils;
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
 * Title: SetAjaxController
 * Description:基础设置类相关AJAX
 *
 * @Author:zhou
 * @Date:2016/6/8 11:23
 * @Version:
 */
@Controller
@RequestMapping("/ajax")
public class SetAjaxController {
    @Autowired
    private DeviceManager deviceManager = null;
    @Autowired
    private SectionManager sectionManager = null;
    /**
     * 搜索仪器
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchDevice", method = { RequestMethod.GET })
    @ResponseBody
    public String searchDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        List<Device> deviceList =  deviceManager.getDeviceList(name);
        if(deviceList.size()>10)
            deviceList = deviceList.subList(0, 10);

        JSONArray array = new JSONArray();
        if (deviceList != null) {
            for (Device device : deviceList) {
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("id", device.getId());
                jsonObject.put("type", device.getType());
                jsonObject.put("name",device.getName());
                array.put(jsonObject);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }

    /**
     * 搜索部门
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchSection", method = { RequestMethod.GET })
    @ResponseBody
    public String searchSection(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        List<Section> sectionList =  sectionManager.getSectionList(name);
        if(sectionList.size()>10)
            sectionList = sectionList.subList(0, 10);

        JSONArray array = new JSONArray();
        if (sectionList != null) {
            for (Section section : sectionList) {
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("id", section.getId());
                jsonObject.put("code",section.getCode());
                jsonObject.put("name",section.getName());
                array.put(jsonObject);
            }
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }
}
