package com.smart.service.scheduledTask;

import com.smart.lisservice.WebService;
import com.smart.model.lis.Process;
import com.smart.service.lis.ProcessManager;
import com.smart.util.ConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcw on 2016/9/28.
 * PDA采集时间、发送时间信息更新
 * 定时从老LIS系统获取PDA采集及送出信息并更新当前LIS
 *
 */
@Service("pdaTatSchedule")
public class PdaTatSchedule {
    private static final Log log = LogFactory.getLog(PdaTatSchedule.class);
    @Autowired
    private ProcessManager processManager = null;
    private WebService webService = new WebService();
    /**
     * 执行任务
     */
    public void run() {
        //获取等更新信息
        log.info("执行PDA数据更新.............................START");
        List<Process> processes = webService.getPdaInfo();
        Map<Long,Process> processMap = new HashMap<Long,Process>();
        String sampleIds = "";
        String updateIds="";
        for(Process process:processes){
            if(!sampleIds.isEmpty()){
                sampleIds+=",";
                updateIds+=",";
            }
            updateIds+="'A1200"+String.format("%8d",ConvertUtil.null2String(process.getSampleid()))+"'";
            sampleIds += ConvertUtil.null2String(process.getSampleid());
            processMap.put(process.getSampleid(),process);
        }
        List<Process> processList = processManager.getHisProcess(sampleIds);
        for(Process process:processList){
            Process process_1 = processMap.get(process.getSampleid());
            if(process_1 != null){
                process.setExecutor(process_1.getExecutor());
                process.setExecutetime(process_1.getExecutetime());
                process.setSender(process_1.getSender());
                process.setSendtime(process_1.getSendtime());

            }
        }
        processManager.saveAll(processList);
        webService.updatePdaStatus(updateIds);
        log.info("执行PDA数据更新.............................END");
    }
}
