package com.smart.dao.hibernate.lis;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TesterSetDao;
import com.smart.model.lis.TesterSet;
import com.smart.model.lis.TesterSetPK;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzh on 2016/9/23.
 */
@Repository("testerSetDao")
public class TesterSetDaoHibernate extends GenericDaoHibernate<TesterSet, TesterSetPK> implements TesterSetDao {

    public TesterSetDaoHibernate() {
        super(TesterSet.class);
    }

    @SuppressWarnings("unchecked")
    public List<TesterSet> getByLab(String lab) {
        return getSession().createQuery("from TesterSet where lab='" + lab + "' order by segment asc").list();
    }

    public List<TesterSet> saveAll(List<TesterSet> list) {
        Session s = getSessionFactory().openSession();
        List<TesterSet> returnList = new ArrayList<TesterSet>();
        for(TesterSet testerSet : list) {
            returnList.add((TesterSet) s.merge(testerSet));
        }
        s.flush();
        s.close();
        return returnList;
    }

    public void clearTester() {
        getSession().createSQLQuery("update L_TESTER_SET set SETTIME=null, TESTER=null").executeUpdate();
    }
}
