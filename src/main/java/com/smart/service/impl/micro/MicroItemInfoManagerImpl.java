package com.smart.service.impl.micro;

import com.smart.dao.micro.MicroItemInfoDao;
import com.smart.model.micro.MicroItemInfo;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.micro.MicroItemInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 9:32
 * @Version:
 */
@Service("microItemInfoManager")
public class MicroItemInfoManagerImpl  extends GenericManagerImpl<MicroItemInfo, Long> implements MicroItemInfoManager {
    private MicroItemInfoDao microItemInfoDao;

    @Autowired
    public void setMicroItemInfoDao(MicroItemInfoDao microItemInfoDao) {
        this.dao = microItemInfoDao;
        this.microItemInfoDao = microItemInfoDao;
    }

    public int getMicroItemInfosCount(String className, String query, int start, int end, String sidx, String sord) {
        return microItemInfoDao.getMicroItemInfosCount(className,query,start,end,sidx,sord);
    }

    public List<MicroItemInfo> getMicroItemInfos(String className, String query, int start, int end, String sidx, String sord) {
        return microItemInfoDao.getMicroItemInfos(className,query,start,end,sidx,sord);
    }

    public MicroItemInfo getMicroItemInfo(String className, Long id) {
        return microItemInfoDao.getMicroItemInfo(className,id);
    }

    public MicroItemInfo getMicroItemInfo(String className, String indexId) {
        return microItemInfoDao.getMicroItemInfo(className,indexId);
    }
}
