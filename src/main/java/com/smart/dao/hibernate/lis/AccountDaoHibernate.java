package com.smart.dao.hibernate.lis;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.AccountDao;
import com.smart.model.lis.Account;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzh on 2016/9/22.
 */
@Repository("accountDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, Long> implements AccountDao {

    public AccountDaoHibernate() {
        super(Account.class);
    }

    @Override
    public List<Account> getAccountByBarcode(String barcode) {
        String hql = "from Account where barcode=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,barcode);
        return query.list();
    }

    public List<Account> saveAll(List<Account> list) {
        Session s = getSessionFactory().openSession();
        List<Account> returnList = new ArrayList<Account>();
        for(Account account : list) {
            returnList.add((Account) s.merge(account));
        }
        s.flush();
        s.close();
        return returnList;
    }
}
