package com.smart.service.scheduledTask;

import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.lis.TesterSetManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuzh on 2016/8/30.
 */
@Service("dataClear")
public class DataClear {


    @Autowired
    private SampleNoBuilderManager sampleNoBuilderManager = null;

    @Autowired
    private TesterSetManager testerSetManager = null;

    public void run() {
        sampleNoBuilderManager.clearNo();
        testerSetManager.clearTester();
    }
}
