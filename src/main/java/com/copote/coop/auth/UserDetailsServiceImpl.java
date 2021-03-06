package com.copote.coop.auth;

import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.model.Permission;
import com.copote.coop.bussiness.service.AccountService;
import com.copote.coop.bussiness.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:09
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountService accountService;
    @Autowired
    PermissionService permissionService;

    @Cacheable(value = "userDetails", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("哈哈哈哈哈哈哈");
        if(StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户不能为空");
        }
        Account account = accountService.loadAccountDetails(username);
        if (null == account) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        List<Permission> permissions = permissionService.loadAccountPermissions(account.getId());

        permissions.forEach(permission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionCode());
            grantedAuthorities.add(grantedAuthority);
        });

        return new UserDetailsModel(account.getAccount(), account.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
