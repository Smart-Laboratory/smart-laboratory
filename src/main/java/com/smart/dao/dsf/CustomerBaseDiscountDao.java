package com.smart.dao.dsf;

import com.smart.dao.GenericDao;
import com.smart.model.dsf.DSF_CustomerBaseDiscount;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zjn on 2016/8/23.
 */
public interface CustomerBaseDiscountDao extends GenericDao<DSF_CustomerBaseDiscount, Long> {
    @Transactional
    int getSizeByCustomerid(String customerid);
    @Transactional
    List<DSF_CustomerBaseDiscount> getDiscountByCustomerid(String customerid, int start, int end, String sidx, String sord);
    @Transactional
    List <DSF_CustomerBaseDiscount> searchByName(String customername,String param);
}
