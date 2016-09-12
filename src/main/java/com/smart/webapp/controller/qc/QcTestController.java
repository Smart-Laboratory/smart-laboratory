package com.smart.webapp.controller.qc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.qc.QcBatch;
import com.smart.model.qc.QcTest;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.service.qc.QcTestManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.TestIdMapUtil;
import com.smart.webapp.util.UserUtil;
import com.smart.webapp.util.instanceTest;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/qc/qctest*")
public class QcTestController {

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
    
    @Autowired
    private QcTestManager qcTestManager;
    
    @Autowired
    private IndexManager indexManager;
    
	@RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String qcBatch = ConvertUtil.null2String(request.getParameter("qcBatch"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();

        int size = 0;
        size = qcTestManager.getCount(qcBatch, start, end, sidx, sord);
        dataResponse.setRecords(size);
        List<QcTest> list =  qcTestManager.getDetails(qcBatch, start, end, sidx, sord); 
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        instanceTest instance = instanceTest.getInstance(sectionManager);
        for(QcTest info :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("testId", ConvertUtil.null2String(info.getTestId()));
            map.put("chineseName", indexManager.getIndex(info.getTestId()).getName());
            map.put("englishab", ConvertUtil.null2String(info.getEnglishab()));
            map.put("targetValue", ConvertUtil.null2String(info.getTargetValue()));
            map.put("stdV", ConvertUtil.null2String(info.getStdV()));
            map.put("frequency", ConvertUtil.null2String(info.getFrequency()));
            map.put("inuse", ConvertUtil.null2String(info.getInuse()));
            map.put("deviceid", ConvertUtil.null2String(info.getDeviceid()));
            map.put("labDepart", ConvertUtil.null2String(info.getLabDepart()));
            map.put("ptlow", ConvertUtil.null2String(info.getPtlow()));
            map.put("pthigh", ConvertUtil.null2String(info.getPthigh()));
            
            String labname = instance.getValue(info.getLabDepart());
            map.put("labDepart", labname);
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }
	
	 /**
     * 保存质控靶值
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/save*",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        JSONObject success = new JSONObject();
        Long id= ConvertUtil.getLongValue(request.getParameter("id"));
        QcTest qcTest = null;
        if(id>0){
            qcTest = qcTestManager.get(id);
        }else {
            qcTest = new QcTest();
        }
        String qcBatch = ConvertUtil.null2String(request.getParameter("qcBatch"));
        String sampleType = ConvertUtil.null2String(request.getParameter("sampleType"));
        String testId = ConvertUtil.null2String(request.getParameter("testId"));
        String englishab = ConvertUtil.null2String(request.getParameter("englishab"));
        String targetValue = ConvertUtil.null2String(request.getParameter("targetValue"));
        String stdV = ConvertUtil.null2String(request.getParameter("stdV"));
        String frequency = ConvertUtil.null2String(request.getParameter("frequency"));
        int inuse = ConvertUtil.getIntValue(request.getParameter("inuse"));
        String deviceid = ConvertUtil.null2String(request.getParameter("deviceid"));
        String labDepart = ConvertUtil.null2String(request.getParameter("labDepart"));
        String ptlow = ConvertUtil.null2String(request.getParameter("ptlow"));
        String pthigh = ConvertUtil.null2String(request.getParameter("pthigh"));

        qcTest.setQcBatch(qcBatch);
        qcTest.setSampleType(sampleType);
        qcTest.setTestId(testId);
        qcTest.setEnglishab(englishab);
        qcTest.setTargetValue(targetValue);
        qcTest.setStdV(stdV);
        qcTest.setFrequency(frequency);
        qcTest.setInuse(inuse);
        qcTest.setDeviceid(deviceid);
        qcTest.setLabDepart(labDepart);
        qcTest.setPtlow(ptlow);
        qcTest.setPthigh(pthigh);
        
        try {
            qcTestManager.save(qcTest);
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
        QcTest qcTest  = qcTestManager.get(id);
        JSONObject result = new JSONObject();
            qcTestManager.remove(qcTest);
            result.put("susess","0");
            return result.toString();
    }
}
