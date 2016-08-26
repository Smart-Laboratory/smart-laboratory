package com.smart.dao.dsf;

import com.smart.dao.GenericDao;
import com.smart.model.dsf.DSF_ControlTestItems;
import com.smart.model.dsf.DSF_TestItems;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zjn on 2016/8/20.
 */
public interface DSF_ControlTestItemsDao  extends GenericDao<DSF_ControlTestItems, Long> {

    @Transactional
    int getSizeByCustomerid(String customerid, String sidx);

    @Transactional
    List<DSF_TestItems> getYlxhByCustomerid(String query, String customerid, int start, int end, String sidx, String sord);

    @Transactional
    void saveAll(List<DSF_ControlTestItems> ctiList );

}
