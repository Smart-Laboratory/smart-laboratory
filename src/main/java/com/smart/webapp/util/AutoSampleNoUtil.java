package com.smart.webapp.util;

import com.smart.model.execute.SampleNoBuilder;
import com.smart.service.execute.SampleNoBuilderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzh on 2016/8/25.
 */
public class AutoSampleNoUtil {

    private static AutoSampleNoUtil instance = new AutoSampleNoUtil();
    private static Map<String, List<SampleNoBuilder>> map = null;

    private AutoSampleNoUtil() {

    }

    public static AutoSampleNoUtil getInstance(SampleNoBuilderManager sampleNoBuilderManager) {
        if(map == null) {
            map = new HashMap<String, List<SampleNoBuilder>>();
            for(SampleNoBuilder sampleNoBuilder : sampleNoBuilderManager.getAllByOrder()) {
                if(map.containsKey(sampleNoBuilder.getLabDepart())) {
                    map.get(sampleNoBuilder.getLabDepart()).add(sampleNoBuilder);
                } else {
                    List<SampleNoBuilder> list = new ArrayList<SampleNoBuilder>();
                    list.add(sampleNoBuilder);
                    map.put(sampleNoBuilder.getLabDepart(), list);
                }
            }
        }
        return instance;
    }

    public Map<String, List<SampleNoBuilder>> getMap() {
        return map;
    }

    public synchronized void updateSampleNoBuilder(SampleNoBuilderManager sampleNoBuilderManager, SampleNoBuilder sampleNoBuilder) {
        sampleNoBuilderManager.save(sampleNoBuilder);
        for(int i = 0; i < map.get(sampleNoBuilder.getLabDepart()).size(); i++) {
            if(map.get(sampleNoBuilder.getLabDepart()).get(i).getId() == sampleNoBuilder.getId()) {
                map.get(sampleNoBuilder.getLabDepart()).get(i).setNowNo(sampleNoBuilder.getNowNo());
            }
        }
    }
}
