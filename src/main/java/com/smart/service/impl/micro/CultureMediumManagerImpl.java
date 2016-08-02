package com.smart.service.impl.micro;

import com.smart.dao.micro.CultureMediumDao;
import com.smart.model.micro.CultureMedium;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.micro.CultureMediumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 11:14
 * @Version:
 */
@Service("cultureMediumManager")
public class CultureMediumManagerImpl extends GenericManagerImpl<CultureMedium, Long> implements CultureMediumManager {

    private CultureMediumDao cultureMediumDao = null;

    @Autowired
    public void setCultureMediumDao(CultureMediumDao cultureMediumDao) {
        this.dao = cultureMediumDao;
        this.cultureMediumDao = cultureMediumDao;
    }

    public int getCultureMediumsCount(String query, int start, int end, String sidx, String sord) {
        return cultureMediumDao.getCultureMediumsCount(query,start,end,sidx,sord);
    }

    public List<CultureMedium> getCultureMediums(String query, int start, int end, String sidx, String sord) {
        return cultureMediumDao.getCultureMediums(query,start,end,sidx,sord);
    }
}
