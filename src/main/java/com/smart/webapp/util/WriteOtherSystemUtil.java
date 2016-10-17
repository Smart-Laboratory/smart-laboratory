package com.smart.webapp.util;

import com.smart.lisservice.WebService;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.service.scheduledTask.ReportGenerate;

import java.util.List;

/**
 * Created by yuzh on 2016/10/1.
 * 所有写入其他系统的操作都在此完成
 */
public class WriteOtherSystemUtil {

    public void writeOtherSystem(Sample sample, Process process, List<TestResult> testResultList) throws Exception {
        ReportGenerate reportGenerate = new ReportGenerate();
        //生成PDF
        try {
            reportGenerate.createReportPdf(sample, process, testResultList, false);
        }catch (Exception e){
            e.printStackTrace();
        }
        WebService service = new WebService();
        //写入HIS
        service.saveHisResult(sample,process,testResultList);
        //写入LIS用于电子病历
        service.saveLisResult(sample,process,testResultList);
        //写入PDA信息
        service.savePdaInfo(sample,process);
    }


}
