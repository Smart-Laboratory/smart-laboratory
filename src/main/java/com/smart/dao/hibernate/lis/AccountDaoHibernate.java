package com.smart.dao.hibernate.lis;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.AccountDao;
import com.smart.model.lis.Account;
import org.springframework.stereotype.Repository;

/**
 * Created by yuzh on 2016/9/22.
 */
@Repository("accountDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, Long> implements AccountDao {

    public AccountDaoHibernate() {
        super(Account.class);
    }
}
