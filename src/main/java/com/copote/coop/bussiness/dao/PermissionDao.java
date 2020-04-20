package com.copote.coop.bussiness.dao;

import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:28
 **/
@Slf4j
@Repository
public class PermissionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String selectPermissionByAccount = "select * from accounts where account_name = ?";
    private static String loadUserdetailsByName = "select CC_ACCOUNT_ID username, CC_PASSWORD password, CC_ALIVE, enabled from accounts where account_name = ?";

    private static String addUser = "";

    public List<Permission> selectPermissionByAccount (String username) {
        return new ArrayList<Permission>();
    }


}
