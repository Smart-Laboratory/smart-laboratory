package com.smart.dao.hibernate.dsf;

import com.smart.dao.dsf.CustomerDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.dsf.CustomerInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zjn on 2016/8/4.
 */
@Repository("customerDao")
public class CustomerDaoHibernate extends GenericDaoHibernate<CustomerInfo, Long> implements CustomerDao {

    public CustomerDaoHibernate() {
        super(CustomerInfo.class);
    }

    public List<CustomerInfo> searchByName(String custName,String param) {
        String sql = "from CustomerInfo ";
        if("like".equals(param)){
            if(!custName.equals("")){
                sql += " where customername like '%" + custName + "%'";
            }
        }else{
            if(!custName.equals("")){
                sql += " where customername = '" + custName + "'";
            }
        }

        Query q = getSession().createQuery(sql);
        return q.list();
    }

    public CustomerInfo searchById(String cid){
        String sql = "from CustomerInfo where customerid = '"+cid+"'";
        Query q = getSession().createQuery(sql);
        List <CustomerInfo>reusltList = q.list();
        if(null!=reusltList&&reusltList.size()>0){
            return reusltList.get(0);
        }else{
            return null;
        }
    }
}
