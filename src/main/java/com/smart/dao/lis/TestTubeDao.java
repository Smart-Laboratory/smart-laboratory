package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestTube;

import java.util.List;

/**
 * Created by zcw on 2016/9/9.
 */
public interface TestTubeDao extends GenericDao<TestTube,Long> {
    List<TestTube> getTestTubeList(String query,  int start, int end, String sidx, String sord);
    int getTestTubeCount(String query);
}
