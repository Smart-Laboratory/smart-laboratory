package com.smart.dao.hibernate.dsf;

import com.smart.dao.dsf.CustomerDao;
import com.smart.dao.dsf.CustomerDetailsDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.dsf.CustomerInfo;
import com.smart.model.dsf.CustomerInfoDetails;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
@Repository("customerDetailsDao")
public class CustomerDetailsDaoHibernate extends GenericDaoHibernate<CustomerInfoDetails, Long> implements CustomerDetailsDao {

    public CustomerDetailsDaoHibernate() {
        super(CustomerInfoDetails.class);
    }

    public List<CustomerInfoDetails> getCustomerDetailByCid(String cid) {
        String sql = "from CustomerInfoDetails where customerid ='" + cid + "'";
        Query q = getSession().createQuery(sql);
        List<CustomerInfoDetails> reusltList = q.list();
        if (null != reusltList && reusltList.size() > 0) {
            return reusltList;
        } else {
            return null;
        }
    }

    public  CustomerInfoDetails getCustomerDetailBySid(String sid){
        String sql = "from CustomerInfoDetails where serialnumber ='" + sid + "'";
        Query q = getSession().createQuery(sql);
        List<CustomerInfoDetails> resultList = q.list();
        if(null!=resultList&&resultList.size()>0){
            return resultList.get(0);
        }else{
            return null;
        }
    }

    public List<CustomerInfoDetails> getCustomerDetailByName(String cid, String cdName, String param) {
        String sql = "from CustomerInfoDetails where customerid ='" + cid + "' ";
        if (!cdName.equals("")) {
            if ("like".equals(param)) {
                sql += " and  name like '%" + cdName + "%'";
            }else{
                sql += " and  name = '" + cdName + "'";
            }
            Query q = getSession().createQuery(sql);
            List <CustomerInfoDetails>cdList = q.list();
            if(null!=cdList&&cdList.size()>0){
                return cdList;
            }else{
                return null;
            }
        } else {
            return null;
        }
    }
}
