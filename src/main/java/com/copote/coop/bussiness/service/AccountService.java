package com.copote.coop.bussiness.service;

import com.copote.coop.bussiness.dao.AccountDao;
import com.copote.coop.bussiness.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:30
 **/
@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    public Account loadAccountDetails (String username) {
        return accountDao.selectAccountByName(username);
    }
    public Boolean updateAccount (Account account) {
        return accountDao.updateAccount(account);
    }

}
