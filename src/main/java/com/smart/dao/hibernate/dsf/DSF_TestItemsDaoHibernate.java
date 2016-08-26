package com.smart.dao.hibernate.dsf;

import com.smart.model.dsf.DSF_TestItems;
import com.smart.dao.dsf.DSF_TestItemsDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by zjn on 2016/8/17.
 */
@Repository("dsf_testItemsDao")
public class DSF_TestItemsDaoHibernate extends GenericDaoHibernate<DSF_TestItems, Long> implements DSF_TestItemsDao{
    public DSF_TestItemsDaoHibernate() {
        super(DSF_TestItems.class);
    }
}
