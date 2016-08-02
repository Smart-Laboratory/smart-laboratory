package com.smart.service.impl.micro;

import com.smart.dao.micro.DrugGroupDao;
import com.smart.model.micro.DrugGroup;
import com.smart.service.micro.DrugGroupManager;
import com.smart.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("drugGroupManager")
public class DrugGroupManagerImpl extends GenericManagerImpl<DrugGroup, Long> implements DrugGroupManager {
    private DrugGroupDao drugGroupDao = null;

    @Autowired
    public void setCultureMediumDao(DrugGroupDao drugGroupDao) {
        this.dao = drugGroupDao;
        this.drugGroupDao = drugGroupDao;
    }

    public int getDrugGroupsCount(String query, int start, int end, String sidx, String sord) {
        return drugGroupDao.getDrugGroupsCount(query,start,end,sidx,sord);
    }

    public List<DrugGroup> getDrugGroups(String query, int start, int end, String sidx, String sord) {
        return drugGroupDao.getDrugGroups(query,start,end,sidx,sord);
    }
}