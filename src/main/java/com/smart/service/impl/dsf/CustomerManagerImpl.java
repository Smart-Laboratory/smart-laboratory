package com.smart.service.impl.dsf;

import com.smart.dao.dsf.CustomerDao;
import com.smart.model.dsf.CustomerInfo;
import com.smart.service.dsf.CustomerManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
@Service("customerManager")
public class CustomerManagerImpl extends GenericManagerImpl<CustomerInfo, Long> implements CustomerManager {
    private CustomerDao customerDao;

    @Autowired
    public void setCalculateFormulaDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
        this.dao = customerDao;
    }

    public List<CustomerInfo> getAllCustomerInfo() {
        return customerDao.getAll();
    }

    public List<CustomerInfo> searchByName(String custName,String param ) {
        return customerDao.searchByName(custName,param);
    }

    public CustomerInfo getCustomerById(String cid) {
        return customerDao.searchById(cid);
    }
}
