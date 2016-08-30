package com.smart.service.impl.execute;

import com.smart.dao.execute.LabOrderDao;
import com.smart.dao.lis.ProcessDao;
import com.smart.dao.lis.SampleDao;
import com.smart.model.execute.ExecuteUnusual;
import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.service.execute.ExecuteUnusualManager;
import com.smart.service.execute.InExcuteManager;
import com.smart.service.impl.GenericManagerImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zcw on 2016/8/29.
 */
@Service("inExcuteManager")
public class InExcuteManagerImpl implements InExcuteManager {
    private LabOrderDao labOrderDao;
    private SampleDao sampleDao;
    private ProcessDao processDao;

    @Autowired
    public void setLabOrderDao(LabOrderDao labOrderDao) {
        this.labOrderDao = labOrderDao;
    }

    @Autowired
    public void setSampleDao(SampleDao sampleDao) {
        this.sampleDao = sampleDao;
    }

    @Autowired
    public void setProcessDao(ProcessDao processDao) {
        this.processDao = processDao;
    }

    /**
     * 住院标本采集信息保存
     * @param sample
     * @param process
     * @param labOrder
     * @return
     */
    @Transactional
    public String saveInExcute(Sample sample, Process process, LabOrder labOrder) {
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try{
            Long sample1Id = sampleDao.save(sample).getId();
            Long processId = processDao.save(process).getId();
            Long labOrderId = labOrderDao.save(labOrder).getLaborder();

            jsonObject.put("sample1Id",sample1Id);
            jsonObject.put("processId",processId);
            jsonObject.put("labOrderId",labOrderId);
            jsonObject.put("state", 1);
        }catch (Exception e){
            e.printStackTrace();
            try {
                jsonObject.put("state", 0);
            }catch (JSONException je){
                je.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    @Override
    @Transactional
    public boolean removeInExcute(Sample sample, Process process, LabOrder labOrder) {
        boolean flag = false;
        try{
            sampleDao.remove(sample);
            processDao.remove(process);
            labOrderDao.remove(labOrder);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
