package com.smart.service.micro;

import com.smart.model.micro.CultureMedium;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 11:14
 * @Version:
 */
public interface CultureMediumManager  extends GenericManager<CultureMedium, Long> {
    int getCultureMediumsCount(String query, int start, int end, String sidx, String sord);
    List<CultureMedium> getCultureMediums(String query, int start, int end, String sidx, String sord);
}
