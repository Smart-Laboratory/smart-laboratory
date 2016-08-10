package com.smart.webapp.controller.set;

import com.smart.model.lis.ProfileTest;
import com.smart.model.rule.Index;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.ProfileTestManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
 * Title: ProfileTestController
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:54
 * @Version:
 */
@Controller
@RequestMapping(value = "/set/profiletest*")
public class ProfileTestController {
    @Autowired
    private ProfileTestManager profileTestManager = null;
    @Autowired
    private SectionManager sectionManager = null;
    @Autowired
    private DeviceManager deviceManager = null;
    @Autowired
    private IndexManager indexManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        return  view;
    }

    /**
     * 获取试验组合列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page  = ConvertUtil.getIntValue(request.getParameter("page"));
        int row = ConvertUtil.getIntValue(request.getParameter("rows"));
        String query = ConvertUtil.null2String(request.getParameter("query"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        List<ProfileTest> list = new ArrayList<ProfileTest>();
        int size = profileTestManager.getProfileTestSize(query);
        list = profileTestManager.getProfileTestList(query,start,end,sidx,sord);

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for(ProfileTest info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("deviceid", info.getDeviceId());
            map.put("profiletest", info.getProfileTest());
            map.put("profilename", info.getProfileName());
            map.put("profilecode", info.getProfileCode());
            map.put("profiledescribe", info.getProfileDescribe());
            map.put("devicename", DeviceUtil.getInstance(deviceManager).getValue(info.getDeviceId()));
            map.put("frequencytime", info.getFrequencyTime());
            map.put("sectionname", DepartUtil.getInstance(sectionManager).getValue(info.getSection()));
            map.put("sampletype", info.getSampleType());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }


    /**
     * 获取已选项目
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping( value = "/getIndexList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getIndexList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page  = ConvertUtil.getIntValue(request.getParameter("page"));
        int row = ConvertUtil.getIntValue(request.getParameter("rows"));
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");

        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        ProfileTest profileTest = profileTestManager.get(id);
        String indexid = spilt(profileTest.getProfileTest());
        List<Index> list = indexManager.getIndexsByQueryIds(indexid);
        int size = list.size();

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for(Index info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", info.getId());
            map.put("indexid", info.getIndexId());
            map.put("name", info.getName());
            map.put("sampletype", info.getSampleFrom());
            map.put("english", info.getEnglish());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 新增组合套餐
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxprofiletest*",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editprofiletest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("set/ajaxprofiletest");
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        if(id >0) {
            ProfileTest profileTest = profileTestManager.get(id);
            String indexid = profileTest.getProfileTest();
            List<Index> indexList = new ArrayList<Index>();
            JSONObject jsonProfiletest = new JSONObject();
            jsonProfiletest.put("id",profileTest.getId());
            jsonProfiletest.put("profileCode",profileTest.getProfileCode());
            jsonProfiletest.put("profileName",profileTest.getProfileName());
            jsonProfiletest.put("profileDescribe",profileTest.getProfileDescribe());
            jsonProfiletest.put("deviceId",profileTest.getDeviceId());
            jsonProfiletest.put("deviceName",DeviceUtil.getInstance(deviceManager).getValue(profileTest.getDeviceId()));
            jsonProfiletest.put("frequencyTime",profileTest.getFrequencyTime());
            jsonProfiletest.put("sampleType",profileTest.getSampleType());
            jsonProfiletest.put("sectionId",profileTest.getSection());
            jsonProfiletest.put("sectionName", DepartUtil.getInstance(sectionManager).getValue(profileTest.getSection()));
            jsonProfiletest.put("useNow",profileTest.getUseNow());
            try {
                if (!indexid.equals("")) {
                    String queryIndexids = spilt(indexid);
                    indexList = indexManager.getIndexsByQueryIds(queryIndexids);
                }
                JSONArray arrRows = new JSONArray();
                for(Index index:indexList){
                    JSONObject obj = new JSONObject();
                    obj.put("indexid",index.getIndexId());
                    obj.put("english",index.getEnglish());
                    obj.put("name",index.getName());
                    arrRows.put(obj);
                }
                jsonProfiletest.put("indexs",arrRows);
                view.addObject("profileTest",jsonProfiletest);
                view.addObject("id",id);
                System.out.println(jsonProfiletest.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return view;
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/saveProfileTest*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void saveProfileTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取组合试验JSON
        ProfileTest profileTest = null;
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        if(id >0){
            profileTest = profileTestManager.get(id);
        }else{
            profileTest = new ProfileTest();
        }
        profileTest.setSampleType(ConvertUtil.null2String(request.getParameter("sampletype")));
        profileTest.setProfileCode(ConvertUtil.null2String(request.getParameter("profilecode")));
        profileTest.setProfileName(ConvertUtil.null2String(request.getParameter("profilename")));
        profileTest.setProfileDescribe(ConvertUtil.null2String(request.getParameter("profiledescribe")));
        profileTest.setSection(ConvertUtil.null2String(request.getParameter("sectionid")));
        profileTest.setDeviceId(ConvertUtil.null2String(request.getParameter("deviceid")));
        System.out.println(ConvertUtil.null2String(request.getParameter("indexids")));
        profileTest.setProfileTest(ConvertUtil.null2String(request.getParameter("indexids")));
        profileTest.setFrequencyTime(ConvertUtil.getIntValue(request.getParameter("frequencytime"),0));
        profileTest.setUseNow(ConvertUtil.getIntValue(request.getParameter("usenow"),1));
        String userid=request.getRemoteUser();
        profileTest.setOperator(userid);
        //保存
        try{
        	profileTestManager.save(profileTest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/remove*",method = RequestMethod.POST)
    @ResponseBody
    public void Delete(@RequestParam(value = "id") Long id , HttpServletRequest request, HttpServletResponse response) throws Exception{
        profileTestManager.remove(id);
    }
    /**
     * 分割字符加引号，用于SQL查询
     * @param str
     * @return
     */
    private String spilt(String str) {
        StringBuffer sb = new StringBuffer();
        String[] temp = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            if (!"".equals(temp[i]) && temp[i] != null)
                sb.append("'" + temp[i] + "',");
        }
        String result = sb.toString();
        String tp = result.substring(result.length() - 1, result.length());
        if (",".equals(tp))
            return result.substring(0, result.length() - 1);
        else
            return result;
    }
}
