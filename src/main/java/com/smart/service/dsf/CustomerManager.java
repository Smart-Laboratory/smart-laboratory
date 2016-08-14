package com.smart.service.dsf;

import com.smart.model.dsf.CustomerInfo;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
public interface CustomerManager  extends GenericManager<CustomerInfo, Long> {
    List<CustomerInfo> getAllCustomerInfo();
    List<CustomerInfo> searchByName(String custName,String param);
    CustomerInfo getCustomerById(String cid);
}
