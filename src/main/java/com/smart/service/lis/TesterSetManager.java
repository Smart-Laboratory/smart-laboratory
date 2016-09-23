package com.smart.service.lis;

import com.smart.model.lis.TesterSet;
import com.smart.model.lis.TesterSetPK;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yuzh on 2016/9/23.
 */
public interface TesterSetManager extends GenericManager<TesterSet, TesterSetPK> {

    @Transactional
    List<TesterSet> getByLab(String lab);

    List<TesterSet> saveAll(List<TesterSet> needSaveList);

    @Transactional
    void clearTester();
}
