package com.smart.dao.hibernate.micro;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.micro.TestCaseDao;
import com.smart.model.micro.TestCase;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:45
 * @Version:
 */
@Repository("testCaseDao")
public class TestCaseDaoHibernate extends GenericDaoHibernate<TestCase, Long> implements TestCaseDao {
    public TestCaseDaoHibernate() {
        super(TestCase.class);
    }

    public int getTestCaseCount(String query, int start, int end, String sidx, String sord) {
        String sql = "select count(c.*)  from TestCase c,Ylxh i where 1=1 c.testId = i.ylxh ";
        if(query != null && !query.equals(""))
            sql += " and i.name like '%" + query+"%'" ;
        sidx = sidx.equals("") ? "id" : sidx;
        sql +=" order by  " +sidx + " " + sord;
        Query q =  getSession().createQuery(sql);
        return new Integer(q.uniqueResult() + "");
    }

    @SuppressWarnings("unchecked")
    public List<TestCase> getTestCaseList(String query, int start, int end, String sidx, String sord) {
        String sql = "from TestCase c,Ylxh i where 1=1 c.testId = i.ylxh ";
        if(query != null && !query.equals(""))
            sql += " and i.name like '%" + query+"%'" ;
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