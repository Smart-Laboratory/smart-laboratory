package com.smart.service.impl.dsf;

import com.smart.dao.dsf.CustomerBaseDiscountDao;
import com.smart.model.dsf.DSF_CustomerBaseDiscount;
import com.smart.service.dsf.CustomerBaseDiscountManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjn on 2016/8/23.
 */
@Service("customerBaseDiscountManager")
public class CustomerBaseDiscountManagerImpl extends GenericManagerImpl<DSF_CustomerBaseDiscount, Long> implements CustomerBaseDiscountManager {
    private CustomerBaseDiscountDao customerBaseDiscountDao;

    @Autowired
    public void setCustomerBaseDiscountDao(CustomerBaseDiscountDao customerBaseDiscountDao) {
        this.dao = customerBaseDiscountDao;
        this.customerBaseDiscountDao = customerBaseDiscountDao;
    }

    public int getSizeByCustomerid(String customerid) {
        return customerBaseDiscountDao.getSizeByCustomerid(customerid);
    }

    public List<DSF_CustomerBaseDiscount> getDiscountByCustomerid(String customerid, int start, int end, String sidx, String sord) {
        return customerBaseDiscountDao.getDiscountByCustomerid(customerid, start, end, sidx, sord);
    }
    public List <DSF_CustomerBaseDiscount> searchByName(String customername,String param){
        return customerBaseDiscountDao.searchByName(customername,param);
    }
}
