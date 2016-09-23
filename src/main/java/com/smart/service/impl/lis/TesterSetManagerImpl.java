package com.smart.service.impl.lis;

import com.smart.dao.lis.TesterSetDao;
import com.smart.model.lis.TesterSet;
import com.smart.model.lis.TesterSetPK;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TesterSetManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuzh on 2016/9/23.
 */
@Service("testerSetManager")
public class TesterSetManagerImpl extends GenericManagerImpl<TesterSet, TesterSetPK> implements TesterSetManager {

    private TesterSetDao testSetDao;

    @Autowired
    public void setTesterSetDao(TesterSetDao testSetDao) {
        this.testSetDao = testSetDao;
        this.dao = testSetDao;
    }

     public List<TesterSet> getByLab(String lab) {
         return testSetDao.getByLab(lab);
     }

    public List<TesterSet> saveAll(List<TesterSet> needSaveList) {
        return testSetDao.saveAll(needSaveList);
    }

    public void clearTester() {
        testSetDao.clearTester();
    }

}
