package com.smart.service.impl.lis;

import com.smart.dao.lis.TestTubeDao;
import com.smart.model.lis.TestTube;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TestTubeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zcw on 2016/9/9.
 */
@Service("testTubeManager")
public class TestTubeManagerImpl extends GenericManagerImpl<TestTube,Long> implements TestTubeManager {

    private TestTubeDao testTubeDao = null;
    @Autowired
    public void setTestTubeDao(TestTubeDao testTubeDao){
        this.testTubeDao = testTubeDao;
        this.dao =testTubeDao;
    }

    @Override
    public List<TestTube> getTestTubeList(String query, int start, int end, String sidx, String sord) {
        return testTubeDao.getTestTubeList(query,start,end,sidx,sord);
    }

    @Override
    public int getTestTubeCount(String query) {
        return testTubeDao.getTestTubeCount(query);
    }
}
