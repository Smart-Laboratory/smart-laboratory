package com.smart.service.scheduledTask;

import com.smart.service.execute.SampleNoBuilderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuzh on 2016/8/30.
 */
@Service("sampleNoClear")
public class SampleNoClear {


    @Autowired
    private SampleNoBuilderManager sampleNoBuilderManager = null;

    public void run() {
        sampleNoBuilderManager.clearNo();
    }
}
