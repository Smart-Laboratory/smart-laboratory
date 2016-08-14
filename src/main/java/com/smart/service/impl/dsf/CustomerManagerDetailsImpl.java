package com.smart.service.impl.dsf;

import com.smart.dao.dsf.CustomerDao;
import com.smart.dao.dsf.CustomerDetailsDao;
import com.smart.model.dsf.CustomerInfo;
import com.smart.model.dsf.CustomerInfoDetails;
import com.smart.service.dsf.CustomerDetailsManager;
import com.smart.service.dsf.CustomerManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
@Service("customerDetailsManager")
public class CustomerManagerDetailsImpl extends GenericManagerImpl<CustomerInfoDetails, Long> implements CustomerDetailsManager {
    private CustomerDetailsDao customerDetailsDao;

    @Autowired
    public void setCalculateFormulaDao(CustomerDetailsDao customerDetailsDao) {
        this.customerDetailsDao = customerDetailsDao;
        this.dao = customerDetailsDao;
    }

    public List <CustomerInfoDetails> getCustomerDetailByCid(String cid){
        return customerDetailsDao.getCustomerDetailByCid(cid);
    }
    public CustomerInfoDetails getCustomerDetailBySid(String sid){
        return customerDetailsDao.getCustomerDetailBySid(sid);
    }
    public List <CustomerInfoDetails> getCustomerDetailByName(String cid,String cdName,String param){
        return customerDetailsDao.getCustomerDetailByName(cid,cdName,param);
    }
}
