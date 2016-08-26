package com.smart.dao.hibernate.dsf;

import com.smart.dao.dsf.CustomerBaseDiscountDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.dsf.DSF_CustomerBaseDiscount;
import com.smart.model.dsf.DSF_TestItems;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zjn on 2016/8/23.
 */
@Repository("customerBaseDiscountDao")
public class CustomerBaseDiscountDaoHibernate extends GenericDaoHibernate<DSF_CustomerBaseDiscount, Long> implements CustomerBaseDiscountDao {
    public CustomerBaseDiscountDaoHibernate() {
        super(DSF_CustomerBaseDiscount.class);
    }

    public int getSizeByCustomerid(String customerid) {
        String sql = "select count(*)  from DSF_CustomerBaseDiscount where customerid='" + customerid + "'";
        Query q =  getSession().createQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }

    public List<DSF_CustomerBaseDiscount> getDiscountByCustomerid(String customerid, int start, int end, String sidx, String sord) {
        String sql = "from DSF_CustomerBaseDiscount where customerid='" + customerid + "'";
        sidx = sidx.equals("") ? "customerid" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    public List <DSF_CustomerBaseDiscount> searchByName(String customername,String param){
        String sql = "from DSF_CustomerBaseDiscount ";
        if("like".equals(param)){
            sql += " where customername like '%"+customername+"%'";
        }else{
            sql += " where customername="+customername;
        }
        Query q = getSession().createQuery(sql);
        return q.list();
    }
}
