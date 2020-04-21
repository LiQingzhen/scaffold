package com.copote.coop.bussiness.model;

import lombok.Data;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:24
 * 映射sys_user表
 **/
@Data
public class Account {

    private String id;
    private String account;
    private String userName;
    private String password;
    private String lastLoginTime;

    private String enabled;
    private String accountNonExpired;
    private String accountNonLocked;
    private String credentialsNonExpired;
    private String createTime;

    private String updateTime;
    private String createUser;
    private String updateUser;

}
