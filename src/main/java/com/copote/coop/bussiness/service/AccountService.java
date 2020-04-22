package com.copote.coop.bussiness.service;

import com.copote.coop.auth.UserDetailsServiceImpl;
import com.copote.coop.auth.jwt.JwtTokenUtil;
import com.copote.coop.bussiness.dao.AccountDao;
import com.copote.coop.bussiness.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author LQZ
 * @Date 2020/4/20 14:30
 **/
@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    public Account loadAccountDetails (String username) { return accountDao.selectAccountByName(username); }
    public Boolean updateAccount (Account account) {
        return accountDao.updateAccount(account);
    }
    public String login (String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

}
