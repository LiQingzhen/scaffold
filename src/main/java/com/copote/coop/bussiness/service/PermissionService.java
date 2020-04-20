package com.copote.coop.bussiness.service;

import com.copote.coop.bussiness.dao.AccountDao;
import com.copote.coop.bussiness.dao.PermissionDao;
import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:30
 **/
@Service
public class PermissionService {

    @Autowired
    PermissionDao permissionDao;

    public List<Permission> loadAccountPermissions (String username) {
        return permissionDao.selectPermissionByAccount(username);
    }
}
