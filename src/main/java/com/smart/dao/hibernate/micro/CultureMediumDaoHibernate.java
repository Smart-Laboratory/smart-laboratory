package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.micro.CultureMediumDao;
import com.smart.model.micro.CultureMedium;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/6 11:11
 * @Version:
*/
@Repository("cultureMediumDao")
public class CultureMediumDaoHibernate  extends GenericDaoHibernate<CultureMedium, Long> implements CultureMediumDao {
    public CultureMediumDaoHibernate() {
        super(CultureMedium.class);
    }

    public int getCultureMediumsCount(String query, int start, int end, String sidx, String sord) {
        String sql = "select count(*)  from CultureMedium  c where 1=1 ";
        if(query != null && !query.equals(""))
            sql += " and c.name like '%" + query+"%'" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
	public List<CultureMedium> getCultureMediums(String query, int start, int end, String sidx, String sord) {
        String sql = "from CultureMedium c where 1=1  ";
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
