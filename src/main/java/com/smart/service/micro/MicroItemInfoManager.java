package com.smart.service.micro;

import com.smart.model.micro.MicroItemInfo;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 9:31
 * @Version:
 */
public interface MicroItemInfoManager extends GenericManager<MicroItemInfo, Long> {

    int getMicroItemInfosCount(String className,String query, int start, int end, String sidx, String sord);

    List<MicroItemInfo> getMicroItemInfos(String className, String query, int start, int end, String sidx, String sord);

    MicroItemInfo getMicroItemInfo(String className,Long id);

    MicroItemInfo getMicroItemInfo(String className,String indexid);
}
