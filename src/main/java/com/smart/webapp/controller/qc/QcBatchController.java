package com.smart.webapp.controller.qc;

import com.smart.model.lis.Device;
import com.smart.model.qc.QcBatch;
import com.smart.service.lis.DeviceManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
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
        size =  qcBatchManager.getCount(query, start, end, sidx, sord);
        dataResponse.setRecords(size);
        List<QcBatch> list =  qcBatchManager.getDetails(query,start,end,sidx,sord);
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
            map.put("medthod", ConvertUtil.null2String(info.getMedthod()));
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
        List<Device> desList =  new ArrayList<Device>();
        if(name.equals("")){
            return "";
        }else{
            desList = deviceManager.getDeviceList(name);
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
        qcBatch.setMedthod(medthod);
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

}
