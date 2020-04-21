package com.copote.coop.bussiness.dao;

import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    private static String selectPermissionsByAccount = "SELECT p.* FROM\n" +
            "        sys_user AS u\n" +
            "\tLEFT JOIN sys_user_role_relation AS ur ON u.id = ur.user_id\n" +
            "\tLEFT JOIN sys_role AS r ON r.id = ur.role_id\n" +
            "\tLEFT JOIN sys_role_permission_relation AS rp ON r.id = rp.role_id\n" +
            "\tLEFT JOIN sys_permission AS p ON p.id = rp.permission_id\n" +
            "    WHERE u.id = ?";

    private static String selectPermissionsByPath = "SELECT pe.* FROM\n" +
            "        sys_request_path AS p\n" +
            "\tLEFT JOIN sys_request_path_permission_relation AS ppe ON p.id = ppe.url_id\n" +
            "\tLEFT JOIN sys_permission AS pe ON ppe.permission_id = pe.id\n" +
            "    WHERE p.url = ?";

    public List<Permission> selectPermissionsByAccount (String userId) {
        List<Permission> permissions = null;
        try {
            permissions = jdbcTemplate.query(selectPermissionsByAccount, new Object[] {userId}, new BeanPropertyRowMapper<>(Permission.class));
        } catch (Exception e) {
            return new ArrayList<Permission>();
        }
        return permissions;
    }

    public List<Permission> selectPermissionsByPath (String path) {
        List<Permission> permissions = null;
//        try {
            permissions = jdbcTemplate.query(selectPermissionsByPath, new Object[] {path}, new BeanPropertyRowMapper<>(Permission.class));
//        } catch (Exception e) {
//            return new ArrayList<Permission>();
//        }
        return permissions;
    }


}
