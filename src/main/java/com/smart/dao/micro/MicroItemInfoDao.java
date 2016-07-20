package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.MicroItemInfo;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 8:56
 * @Version:
 */
public interface MicroItemInfoDao extends GenericDao<MicroItemInfo, Long> {

    int getMicroItemInfosCount(String className,String query, int start, int end, String sidx, String sord);

    List<MicroItemInfo> getMicroItemInfos(String className, String query, int start, int end, String sidx, String sord);

    MicroItemInfo getMicroItemInfo(String className,Long id);

    MicroItemInfo getMicroItemInfo(String className,String indexid);
}
