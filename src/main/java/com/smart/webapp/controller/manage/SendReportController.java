package com.smart.webapp.controller.manage;

import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.WriteOtherSystemUtil;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zcw on 2016/10/17.
 * 重新发送检验报告
 */
@Controller
@RequestMapping("/manage/sendreport*")
public class SendReportController {
    @Autowired
    protected SampleManager sampleManager = null;

    @Autowired
    protected TestResultManager testResultManager = null;

    @Autowired
    protected ProcessManager processManager = null;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }

    /**
     * 重新发送报告单至HIS系统
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendHisReport*", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String sendHisReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String barcode = ConvertUtil.null2String(request.getParameter("barcode"));
        JSONObject flag= new JSONObject();
        try {
            Sample  sample= sampleManager.getSampleByBarcode(barcode);
            if(sample != null) {
                Process process = processManager.getBySampleId(sample.getId());
                List<TestResult> testResultList = testResultManager.getTestBySampleNo(sample.getSampleNo());
                //生成PDF，写HIS、电子病历、PDA
                new WriteOtherSystemUtil().writeOtherSystem(sample, process, testResultList);
                flag.put("sucess", "1");
            }
        }catch (Exception e){
            e.printStackTrace();
            flag.put("sucess","0");
        }
        return flag.toString();
    }
}
