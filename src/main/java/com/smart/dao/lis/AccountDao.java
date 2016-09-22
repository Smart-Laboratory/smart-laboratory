package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Account;

import java.util.List;

/**
 * Created by yuzh on 2016/9/22.
 */
public interface AccountDao extends GenericDao<Account, Long> {
    public List<Account> getAccountByBarcode(String barcode);
    public List<Account> saveAll(List<Account> list);
}
