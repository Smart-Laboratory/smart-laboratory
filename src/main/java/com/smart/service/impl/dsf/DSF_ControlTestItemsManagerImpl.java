package com.smart.service.impl.dsf;

import com.smart.dao.dsf.DSF_ControlTestItemsDao;
import com.smart.model.dsf.DSF_ControlTestItems;
import com.smart.model.dsf.DSF_TestItems;
import com.smart.service.dsf.DSF_ControlTestItemsManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjn on 2016/8/20.
 */
@Service("dsf_controlTestItemsManager")
public class DSF_ControlTestItemsManagerImpl extends GenericManagerImpl<DSF_ControlTestItems, Long> implements DSF_ControlTestItemsManager {
    private DSF_ControlTestItemsDao dsf_controlTestItemsDao;

    @Autowired
    public void setDsf_controlTestItemsDao(DSF_ControlTestItemsDao dsf_controlTestItemsDao) {
        this.dao = dsf_controlTestItemsDao;
        this.dsf_controlTestItemsDao = dsf_controlTestItemsDao;
    }

    public int getSizeByCustomerid(String query, String customerid) {
        return dsf_controlTestItemsDao.getSizeByCustomerid(query, customerid);
    }

    public List<DSF_TestItems> getYlxhByCustomerid(String query, String lab, int start, int end, String sidx, String sord) {
        return dsf_controlTestItemsDao.getYlxhByCustomerid(query, lab, start, end, sidx, sord);
    }

    public void saveAll(List<DSF_ControlTestItems> ctiList ){
        dsf_controlTestItemsDao.saveAll(ctiList);
    }


}
