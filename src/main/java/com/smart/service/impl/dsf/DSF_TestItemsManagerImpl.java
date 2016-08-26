package com.smart.service.impl.dsf;

import com.smart.model.dsf.DSF_TestItems;
import com.smart.dao.dsf.DSF_TestItemsDao;
import com.smart.service.dsf.DSF_TestItemsManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zjn on 2016/8/17.
 */
@Service("dsf_testItemsManager")
public class DSF_TestItemsManagerImpl extends GenericManagerImpl<DSF_TestItems, Long> implements DSF_TestItemsManager {
    private DSF_TestItemsDao dsf_testItemsDao;

    @Autowired
    public void setDsf_testItemsDao(DSF_TestItemsDao dsf_testItemsDao) {
        this.dao = dsf_testItemsDao;
        this.dsf_testItemsDao = dsf_testItemsDao;
    }
}
