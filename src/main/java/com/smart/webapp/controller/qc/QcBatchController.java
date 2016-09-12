package com.smart.webapp.controller.qc;

import com.alibaba.fastjson.JSON;
import com.smart.Constants;
import com.smart.model.lis.Device;
import com.smart.model.lis.Section;
import com.smart.model.qc.QcBatch;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SectionUtil;
import com.smart.webapp.util.UserUtil;
import com.zju.api.service.RMIService;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.management.remote.rmi.RMIServer;
import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title: QcBatchController
 * Description:质控项目设定
 *
 * @Author:zhou
 * @Date:2016/7/28 11:18
 * @Version:
 */
@Controller
@RequestMapping("/qc/qcbatch*")
public class QcBatchController {

    @Autowired
    private QcBatchManager qcBatchManager = null;

    @Autowired
    private DeviceManager deviceManager = null;
    
    @Autowired
    private UserManager userManager = null;
    
    @Autowired
    private RMIService rmiService;
    
    @Autowired
    private SectionManager sectionManager;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, String> labMap = new HashMap<String ,String >();
    	Map<String, String> deviceMap = new HashMap<String ,String >();
    	
    	List<Section> sections = sectionManager.getAll();
    	for(Section section : sections){
    		labMap.put(section.getCode(), section.getName());
    	}
    	User user = UserUtil.getInstance(userManager).getUser(request.getRemoteUser());
    	
    	List<Device> dList = deviceManager.getDeviceByLab(user.getLastLab());
    	for(Device d : dList){
    		deviceMap.put(d.getId(), d.getName());
    	}
    	
    	return new ModelAndView().addObject("sections", sections).addObject("devices",deviceMap);
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
        String deviceId = ConvertUtil.null2String(request.getParameter("deviceid"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        String lab = ConvertUtil.null2String(request.getParameter("lab"));
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        if(lab.isEmpty())
        	lab = UserUtil.getInstance(userManager).getUser(request.getRemoteUser()).getLastLab();
        size =  qcBatchManager.getCount(lab, deviceId, start, end, sidx, sord);
        dataResponse.setRecords(size);
        List<QcBatch> list =  qcBatchManager.getDetails(lab, deviceId,start,end,sidx,sord);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        for(QcBatch info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("qcBatch", ConvertUtil.null2String(info.getQcBatch()));
            map.put("qcBatchName", ConvertUtil.null2String(info.getQcBatchName()));
            map.put("sampleType", ConvertUtil.null2String(info.getSampleType()));
            map.put("qcLevel", ConvertUtil.null2String(info.getQcLevel()));
            map.put("qcCode", ConvertUtil.null2String(info.getQcCode()));
            map.put("factory", ConvertUtil.null2String(info.getFactory()));
            map.put("medthod", ConvertUtil.null2String(info.getMethod()));
            map.put("indate", ConvertUtil.null2String(info.getIndate()));
            map.put("outdate", ConvertUtil.null2String(info.getOutdate()));
            map.put("outer", ConvertUtil.null2String(info.getOuter()));
            map.put("deviceid", ConvertUtil.null2String(info.getDeviceid()));
            map.put("indate", ConvertUtil.null2String(info.getIndate()));
            map.put("labdepart", ConvertUtil.null2String(info.getLabdepart()));
            map.put("expDate", ConvertUtil.null2String(info.getExpDate()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 获取检验项目结果
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchDevice*", method = { RequestMethod.GET })
    @ResponseBody
    public String searchTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = ConvertUtil.null2String(request.getParameter("name"));
        String chosed = ConvertUtil.null2String(request.getParameter("chosed"));
        List<Device> desList =  new ArrayList<Device>();
        if(name.equals("")){
            desList = deviceManager.getAll();
        }else{
        	desList = deviceManager.getByIds(chosed);
            desList.addAll(deviceManager.getDeviceList(name));
        }
        JSONArray array = new JSONArray();
        if (desList != null) {
            for (Device d : desList) {
                JSONObject o = new JSONObject();
                o.put("id", d.getId());
                o.put("name", d.getName());
                array.put(o);
            }
        }
        return array.toString();
    }

    /**
     * 保存质控批次
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/save",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        JSONObject success = new JSONObject();
        Long id= ConvertUtil.getLongValue(request.getParameter("id"));
        QcBatch qcBatch = null;
        if(id>0){
            qcBatch = qcBatchManager.get(id);
        }else {
            qcBatch = new QcBatch();
        }
        String qcBatchNo = ConvertUtil.null2String(request.getParameter("qcBatch"));
        String sampleType = ConvertUtil.null2String(request.getParameter("sampleType"));
        int qcLevel = ConvertUtil.getIntValue(request.getParameter("qcLevel"));
        String qcCode = ConvertUtil.null2String(request.getParameter("qcCode"));
        String factory = ConvertUtil.null2String(request.getParameter("factory"));
        String medthod = ConvertUtil.null2String(request.getParameter("medthod"));
        String indate = ConvertUtil.null2String(request.getParameter("indate"));
        String outdate = ConvertUtil.null2String(request.getParameter("outdate"));
        String outer = ConvertUtil.null2String(request.getParameter("outer"));
        String deviceid = ConvertUtil.null2String(request.getParameter("deviceid"));
        String labdepart = ConvertUtil.null2String(request.getParameter("labdepart"));
        String expDate = ConvertUtil.null2String(request.getParameter("expDate"));
        String qcBatchName = ConvertUtil.null2String(request.getParameter("qcBatchName"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dInDate = sdf.parse(indate);
        Date dOutDate = sdf.parse(outdate);
        Date dExpDate = sdf.parse(expDate);

        qcBatch.setQcBatch(qcBatchNo);
        qcBatch.setSampleType(sampleType);
        qcBatch.setQcLevel(qcLevel);
        qcBatch.setQcCode(qcCode);
        qcBatch.setFactory(factory);
        qcBatch.setMethod(medthod);
        qcBatch.setIndate(dInDate);
        qcBatch.setOutdate(dOutDate);
        qcBatch.setDeviceid(deviceid);
        qcBatch.setOuter(outer);
        qcBatch.setLabdepart(labdepart);
        qcBatch.setExpDate(dExpDate);
        qcBatch.setQcBatchName(qcBatchName);
        try {
            qcBatchManager.save(qcBatch);
        }catch (Exception e){
            e.printStackTrace();
        }

        success.put("success","0");
        return success.toString();
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
    public String delete(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        //检测状态
        QcBatch qcBatch  = qcBatchManager.get(id);
        JSONObject result = new JSONObject();
            qcBatchManager.remove(qcBatch);
            result.put("susess","0");
            return result.toString();
    }
    
    /**
     * 根据id获取qcbatch
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getBatch",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getBactch(HttpServletRequest request, HttpServletResponse response)throws  Exception{
    	Long id = ConvertUtil.getLongValue(request.getParameter("id"));
    	QcBatch qc = qcBatchManager.get(id);
    	JSONObject o = new JSONObject();
    	
    	o.put("id", qc.getId());
    	o.put("qcBatch", qc.getQcBatch());
    	o.put("sampleType", qc.getSampleType());
    	o.put("qcLevel", qc.getQcLevel());
    	o.put("qcCode", qc.getQcCode());
    	o.put("factory", qc.getFactory());
    	o.put("labdepart", qc.getLabdepart());
    	o.put("indate", Constants.SDF.format(qc.getIndate()));
    	o.put("outdate", Constants.SDF.format(qc.getOutdate()));
    	o.put("medthod", qc.getMethod());
    	o.put("expDate", Constants.SDF.format(qc.getExpDate()));
    	o.put("qcBatchName", qc.getQcBatchName()==null?"":qc.getQcBatchName());
    	o.put("deviceid", qc.getDeviceid());
    	
    	String deviceNames = "";
    	for(Device device : deviceManager.getByIds(qc.getDeviceid())){
    		deviceNames += device.getName() + ",";
    	}
    	o.put("deviceName", deviceNames.substring(0, deviceNames.length()-1));
    	
		return o.toString();
    }

}
