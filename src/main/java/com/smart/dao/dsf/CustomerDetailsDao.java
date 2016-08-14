package com.smart.dao.dsf;

import com.smart.dao.GenericDao;
import com.smart.model.dsf.CustomerInfo;
import com.smart.model.dsf.CustomerInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
public interface CustomerDetailsDao extends GenericDao<CustomerInfoDetails, Long> {
    @Transactional
    public List<CustomerInfoDetails> getCustomerDetailByCid(String cid);
    @Transactional
    public CustomerInfoDetails getCustomerDetailBySid(String sid);
    @Transactional
    public List<CustomerInfoDetails> getCustomerDetailByName(String cid,String cdName,String param);
}
