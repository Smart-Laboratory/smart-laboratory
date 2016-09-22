package com.smart.service.lis;

import com.smart.model.lis.Account;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by yuzh on 2016/9/22.
 */
public interface AccountManager extends GenericManager<Account, Long> {
    public List<Account> getAccountByBarcode(String barcode);
    public List<Account> saveAll(List<Account> list);
}
