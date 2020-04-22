package com.copote.coop.bussiness.controller;

import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.service.AccountService;
import com.copote.coop.util.JsonResult;
import com.copote.coop.util.ResultCode;
import com.copote.coop.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
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
}
