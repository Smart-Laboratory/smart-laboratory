package com.smart.dao.hibernate.lis;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestTubeDao;
import com.smart.model.lis.TestTube;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zcw on 2016/9/9.
 */
@Repository("testTubeDao")
public class TestTubeDaoHibernate extends GenericDaoHibernate<TestTube,Long> implements TestTubeDao{

    public TestTubeDaoHibernate() {
        super(TestTube.class);
    }

    @Override
    public List<TestTube> getTestTubeList(String query,  int start, int end, String sidx, String sord) {
        String sql = " from TestTube t where 1=1 ";
        if(query != null && !query.equals(""))
            sql += " and t.name  like '%" +query +"%'";

        sidx = sidx.equals("")?"id":sidx;
        sql +=" order by  " +sidx + " " + sord;

        Query q =  getSession().createQuery(sql);

        if(end != 0){
            q.setFirstResult(start);
            q.setMaxResults(end);
        }
        return  q.list();
    }

    @Override
    public int getTestTubeCount(String query) {
        String sql = "select count(1) cnt from lab_testtube where 1=1";
        if(query != null && !query.equals(""))
            sql += " and name like '%" +query +"%'";
        Query q =  getSession().createSQLQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }
}
