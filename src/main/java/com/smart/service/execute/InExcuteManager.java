package com.smart.service.execute;

import com.smart.model.execute.LabOrder;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.service.GenericManager;

/**
 * Created by zcw on 2016/8/29.
 */
public interface InExcuteManager{

    /**
     * 保存住院标本采集信息
     * @param sample
     * @param process
     * @param labOrder
     * @return
     */
    String saveInExcute(Sample sample, Process process,LabOrder labOrder);

    /**
     * 移除住院标本采集信息
     * @param sample
     * @param process
     * @param labOrder
     * @return
     */
    boolean removeInExcute(Sample sample, Process process,LabOrder labOrder);
}
