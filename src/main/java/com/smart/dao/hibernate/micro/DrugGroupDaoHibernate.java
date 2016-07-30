package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.micro.DrugGroup;
import com.smart.dao.micro.DrugGroupDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("drugGroupDao")
public class DrugGroupDaoHibernate extends GenericDaoHibernate<DrugGroup, Long> implements DrugGroupDao {

    public DrugGroupDaoHibernate() {
        super(DrugGroup.class);
    }

    @Override
    public int getDrugGroupsCount(String query, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from DrugGroup  c where 1=1 ";
        if(query != null && !query.equals(""))
            sql += " and c.name like '%" + query+"%'" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
	public List<DrugGroup> getDrugGroups(String query, int start, int end, String sidx, String sord) {
        String sql = "from DrugGroup c where 1=1  ";
        if(query != null && !query.equals(""))
            sql += " and c.name like '%" + query+"%'" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q = getSession().createQuery(sql);
        //System.out.println(sql);
        if(end !=0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return q.list();
    }
}
