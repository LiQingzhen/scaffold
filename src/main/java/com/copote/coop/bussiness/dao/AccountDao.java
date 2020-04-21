package com.copote.coop.bussiness.dao;

import com.copote.coop.bussiness.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:28
 **/
@Slf4j
@Repository
public class AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String selectAccountByName = "select * from sys_user where account = ?";
    private static String updateAccount = "";

    public Account selectAccountByName (String username) {
        Account account = jdbcTemplate.queryForObject(selectAccountByName, new Object[] {username}, Account.class);
        return account;
    }

    public Boolean updateAccount (Account account) {
        try {
            jdbcTemplate.update(updateAccount);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
