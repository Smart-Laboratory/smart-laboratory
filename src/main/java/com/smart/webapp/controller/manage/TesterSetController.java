package com.smart.webapp.controller.manage;

import com.smart.Constants;
import com.smart.model.Dictionary;
import com.smart.model.lis.Device;
import com.smart.model.lis.Section;
import com.smart.model.lis.TesterSet;
import com.smart.model.lis.TesterSetPK;
import com.smart.model.user.User;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.TesterSetManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by yuzh on 2016/9/23.
 */
@Controller
@RequestMapping("/manage/testerSet*")
public class TesterSetController {

    @Autowired
    private DeviceManager deviceManager = null;

    @Autowired
    private SectionManager sectionManager = null;

    @Autowired
    private TesterSetManager testerSetManager = null;

    @Autowired
    private SampleManager sampleManager = null;

    /**
     * 设置检验段
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return new ModelAndView();
    }

    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET})
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DataResponse dataResponse = new DataResponse();
        User user = UserUtil.getInstance().getUser(request.getRemoteUser());
        List<TesterSet> testerSetList = testerSetManager.getByLab(user.getLastLab());
        List<Device> deviceList = deviceManager.getDeviceByLab(user.getLastLab());
        String segments = sectionManager.getByCode(user.getLastLab()).getSegment();
        List<TesterSet> returnList = new ArrayList<TesterSet>();
        Set<TesterSetPK> usedPK = new HashSet<TesterSetPK>(); //已有的仪器检验段组合
        if(segments != null && !segments.isEmpty()) {
            List<TesterSet> needSaveList = new ArrayList<TesterSet>();
            if(testerSetList.size() > 0) {
                for(TesterSet testerSet : testerSetList) {
                    usedPK.add(new TesterSetPK(testerSet.getSegment(), testerSet.getDeviceId()));
                }
                returnList.addAll(testerSetList);
            }
            for(Device device : deviceList) {
                for(String segment : segments.split(",")) {
                    if(!usedPK.contains(new TesterSetPK(segment,device.getId()))) {
                        usedPK.add(new TesterSetPK(segment,device.getId()));
                        TesterSet testerSet= new TesterSet();
                        testerSet.setDeviceId(device.getId());
                        testerSet.setSegment(segment);
                        testerSet.setLab(user.getLastLab());
                        needSaveList.add(testerSet);
                    }
                }
            }
            if(needSaveList.size() > 0) {
                needSaveList = testerSetManager.saveAll(needSaveList);
                for(TesterSet testerSet : needSaveList) {
                    returnList.add(testerSet);
                }
            }
        }
        dataResponse.setRecords(returnList.size());
        dataResponse.setPage(1);
        dataResponse.setPage(1);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(TesterSet testerSet : returnList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deviceId", testerSet.getDeviceId());
            map.put("segment", testerSet.getSegment());
            map.put("isSet", "<input type=\"checkbox\" onclick=\"setTester('"+ testerSet.getDeviceId() +"','"+ testerSet.getSegment()+"')\"/>");
            map.put("tester", ConvertUtil.null2String(testerSet.getTester()));
            map.put("setTime", ConvertUtil.null2String(testerSet.getSetTime()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping( value = "/set" ,method = {RequestMethod.GET})
    @ResponseBody
    public boolean setTester(HttpServletRequest request) throws Exception {
        try {
            String deviceId = request.getParameter("deviceId");
            String segment = request.getParameter("segment");
            User user = UserUtil.getInstance().getUser(request.getRemoteUser());
            String name = UserUtil.getInstance().getUser(request.getRemoteUser()).getName();
            Date date = new Date();
            TesterSet testerSet = new TesterSet();
            testerSet.setDeviceId(deviceId);
            testerSet.setSegment(segment);
            testerSet.setLab(user.getLastLab());
            testerSet.setSetTime(date);
            testerSet.setTester(name);
            testerSetManager.save(testerSet);
            sampleManager.updateChkoper2(Constants.DF3.format(date) + segment, name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
