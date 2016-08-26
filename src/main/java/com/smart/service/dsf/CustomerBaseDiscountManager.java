package com.smart.service.dsf;

import com.smart.model.dsf.DSF_CustomerBaseDiscount;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zjn on 2016/8/23.
 */
public interface CustomerBaseDiscountManager extends GenericManager<DSF_CustomerBaseDiscount, Long> {
    int getSizeByCustomerid(String customerid);

    List<DSF_CustomerBaseDiscount> getDiscountByCustomerid(String customerid, int start, int end, String sidx, String sord);

    /**
     * 参数2，如果不填写默认等于，如果填写LIKE就是LIKE
     * @param customername
     * @return
     */
    List <DSF_CustomerBaseDiscount> searchByName(String customername,String param);

}
