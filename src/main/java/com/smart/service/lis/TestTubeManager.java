package com.smart.service.lis;

import com.smart.model.lis.TestTube;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zcw on 2016/9/9.
 */
public interface TestTubeManager  extends GenericManager<TestTube, Long> {
    List<TestTube> getTestTubeList(String query, int start, int end, String sidx, String sord);
    int getTestTubeCount(String query);
}
