package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.CultureMedium;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:培养基信息
 *
 * @Author:zhou
 * @Date:2016/7/6 11:10
 * @Version:
 */
public interface CultureMediumDao extends GenericDao<CultureMedium, Long> {
    int getCultureMediumsCount(String query, int start, int end, String sidx, String sord);
    List<CultureMedium> getCultureMediums(String query, int start, int end, String sidx, String sord);
}
