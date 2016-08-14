package com.smart.dao.dsf;

import java.util.List;
import com.smart.dao.GenericDao;
import com.smart.model.dsf.CustomerInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zjn on 2016/8/4.
 */
public interface CustomerDao extends GenericDao<CustomerInfo, Long> {
    @Transactional
    public List<CustomerInfo> searchByName(String custName,String param);
    @Transactional
    public CustomerInfo searchById(String cid);
}
