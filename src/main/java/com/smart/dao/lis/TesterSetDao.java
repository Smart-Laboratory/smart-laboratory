package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TesterSet;
import com.smart.model.lis.TesterSetPK;

import java.util.List;

/**
 * Created by yuzh on 2016/9/23.
 */
public interface TesterSetDao extends GenericDao<TesterSet, TesterSetPK> {

    List<TesterSet> getByLab(String lab);

    List<TesterSet> saveAll(List<TesterSet> needSaveList);

    void clearTester();
}
