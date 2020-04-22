package com.copote.coop.bussiness.controller;

import com.alibaba.fastjson.JSONObject;
import com.copote.coop.auth.jwt.JwtTokenUtil;
import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.service.AccountService;
import com.copote.coop.util.JsonResult;
import com.copote.coop.util.ResultCode;
import com.copote.coop.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author LQZ
 * @Date 2020/4/21 14:03
 **/
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/getUser")
    public JsonResult getUser(){
        Account account = accountService.loadAccountDetails("user1");
        return ResultTool.success(account);
    }

    @RequestMapping("/login")
    public JsonResult login(@RequestBody Map<String,String> map){
        String username  = map.get("username");
        String password = map.get("password");

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ResultTool.fail(ResultCode.PARAM_IS_BLANK);
        }
        try {
            return ResultTool.success(accountService.login(username, password));
        }catch (AuthenticationException e){
            return ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
        }
    }

    @RequestMapping("/refreshToken")
    public JsonResult logout(@RequestHeader("${jwt.header}") String token){
        JSONObject newToken = accountService.refreshToken(token);
        if (newToken == null) {
            return ResultTool.fail(ResultCode.USER_TOKEN_EXPIRED);
        }
        return ResultTool.success(newToken);
    }

    @RequestMapping("/register")
    public JsonResult register(@RequestBody Map<String,String> map){
        return ResultTool.fail(ResultCode.FEATURES_BUILDING);
    }

    @CacheEvict(value = "userDetails", key = "#account.account", beforeInvocation = true)
    @RequestMapping("/editAccount")
    public JsonResult editAccount(@RequestBody Account account){
        return ResultTool.fail(ResultCode.FEATURES_BUILDING);
    }
}
