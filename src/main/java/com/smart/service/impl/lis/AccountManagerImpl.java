package com.smart.service.impl.lis;

import com.smart.dao.lis.AccountDao;
import com.smart.model.lis.Account;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuzh on 2016/9/22.
 */
@Service("accountManager")
public class AccountManagerImpl extends GenericManagerImpl<Account, Long> implements AccountManager {

    private AccountDao accountDao;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.dao = accountDao;
        this.accountDao = accountDao;
    }
}
