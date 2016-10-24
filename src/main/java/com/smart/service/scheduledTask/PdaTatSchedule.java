package com.smart.service.scheduledTask;

import com.smart.lisservice.WebService;
import com.smart.model.lis.Process;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.smart.util.ConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
    @Autowired
    private SampleManager sampleManager = null;
    private WebService webService = new WebService();

//    private static int thread_num = 5;
//    private static int client_num = 20;
    /**
     * 执行任务
     */
    public void run() {
//        ExecutorService exec = Executors.newCachedThreadPool();
//        // thread_num个线程可以同时访问
//        final Semaphore semp = new Semaphore(thread_num);
//        // 模拟client_num个客户端访问
//        for (int index = 0; index < client_num; index++) {
//            final int NO = index;
//            Runnable run = new Runnable() {
//                public void run() {
//                    try {
//                        // 获取许可
//                        semp.acquire();
//                        System.out.println("Thread并发事情>>>"+ NO);
//                        try {
//                            String result = sampleManager.generateSampleNo("XCG",0);
//                            System.err.println("接口调用返回结果：" + result);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        semp.release();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            exec.execute(run);
//        }
//        // 退出线程池
//        exec.shutdown();


            //获取等更新信息
        log.info("执行PDA数据更新.............................START");
        List<Process> processes = webService.getPdaInfo();
        Map<Long,Process> processMap = new HashMap<Long,Process>();
        String sampleIds = "";
        String updateIds="";
        int i=0;

        for(Process process:processes){
            if(i>50) break;
            if(!sampleIds.isEmpty()){
                sampleIds+=",";
                updateIds+=",";
            }
            try {
                updateIds+="'A1200"+ConvertUtil.null2String(process.getSampleid())+"'";
            }catch (Exception e){
                e.printStackTrace();
            }

            sampleIds += ConvertUtil.null2String(process.getSampleid());
            processMap.put(process.getSampleid(),process);
            i++;
        }
        List<Process> processList = processManager.getHisProcess(sampleIds);
        for(Process process:processList){
            Process process_1 = processMap.get(process.getSampleid());
            if(process_1 != null){
                if(process_1.getExecutetime()!=null){
                    process.setExecutor(process_1.getExecutor());
                    process.setExecutetime(process_1.getExecutetime());
                }
                if(process_1.getSendtime() !=null){
                    process.setSender(process_1.getSender());
                    process.setSendtime(process_1.getSendtime());
                }


            }
        }
        processManager.saveAll(processList);
        webService.updatePdaStatus(updateIds);
        log.info("执行PDA数据更新.............................END");
    }
}
