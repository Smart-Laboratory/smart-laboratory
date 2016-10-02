package com.smart.webapp.util;

import com.smart.model.lis.TesterSet;
import com.smart.service.lis.TesterSetManager;
import com.smart.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzh on 2016/10/2.
 */
public class TesterSetMapUtil {
    private static Map<String, String> map = null;
    private static TesterSetMapUtil instance = null;

    private static TesterSetManager testerSetManager = null;

    private TesterSetMapUtil() {
        testerSetManager = (TesterSetManager) SpringContextUtil.getBean("testerSetManager");
    }

    public static TesterSetMapUtil getInstance() {
        if(instance == null) {
            instance = new TesterSetMapUtil();
            instance.map = new HashMap<String, String>();
            for(TesterSet testerSet : testerSetManager.getAll()) {
                if(testerSet.getTester() != null && !testerSet.getTester().isEmpty()) {
                    instance.map.put(testerSet.getSegment(), testerSet.getTester());
                }
            }
        }
        return instance;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void rebuildMap() {
        instance = new TesterSetMapUtil();
        instance.map = new HashMap<String, String>();
        for(TesterSet testerSet : testerSetManager.getAll()) {
            if(testerSet.getTester() != null && !testerSet.getTester().isEmpty()) {
                instance.map.put(testerSet.getSegment(), testerSet.getTester());
            }
        }
    }

    public void updateMap(TesterSet testerSet) {
        map.put(testerSet.getSegment(), testerSet.getTester());
    }

    public String getTester(String segment) {
        String tester = map.get(segment);
        if(tester == null) {
            tester = "";
        }
        return tester;
    }
}
