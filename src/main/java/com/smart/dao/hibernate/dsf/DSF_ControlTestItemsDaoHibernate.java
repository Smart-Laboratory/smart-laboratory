package com.smart.dao.hibernate.dsf;

import com.smart.dao.dsf.DSF_ControlTestItemsDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.dsf.DSF_ControlTestItems;
import com.smart.model.dsf.DSF_TestItems;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zjn on 2016/8/20.
 */
@Repository("dsf_controlTestItemsDao")
public class DSF_ControlTestItemsDaoHibernate  extends GenericDaoHibernate<DSF_ControlTestItems, Long> implements DSF_ControlTestItemsDao {
    public DSF_ControlTestItemsDaoHibernate() {
        super(DSF_ControlTestItems.class);
    }

    public int getSizeByCustomerid(String query, String customerid) {
        String sql = "select count(*)  from DSF_TestItems where customerid='" + customerid + "'";
        if(query!= null && !query.isEmpty()) {
            sql +=" and (name like '" + query + "%' )";
        }
        Query q =  getSession().createQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }

    public List<DSF_TestItems> getYlxhByCustomerid(String query, String customerid, int start, int end, String sidx, String sord) {
        String sql = "from DSF_TestItems where customerid='" + customerid + "'";
        if(query!= null && !query.isEmpty()) {
            sql +=" and (name like '" + query + "%' )";
        }
        sidx = sidx.equals("") ? "customerid" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }

    public void saveAll(List<DSF_ControlTestItems> ctiList ){
        Session s = getSessionFactory().openSession();
        for(DSF_ControlTestItems cti : ctiList) {
            s.saveOrUpdate(cti);
        }
        s.flush();
        s.close();
    }
}
