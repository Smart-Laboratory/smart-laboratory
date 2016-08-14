package com.smart.service.dsf;

import com.smart.model.dsf.CustomerInfoDetails;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
public interface CustomerDetailsManager extends GenericManager<CustomerInfoDetails, Long> {
    List <CustomerInfoDetails>getCustomerDetailByCid(String cid);
    CustomerInfoDetails getCustomerDetailBySid(String sid);
    List <CustomerInfoDetails>getCustomerDetailByName(String cid,String cdName,String param);
}
