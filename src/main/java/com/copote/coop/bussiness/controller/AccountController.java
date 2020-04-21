package com.copote.coop.bussiness.controller;

import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.service.AccountService;
import com.copote.coop.util.JsonResult;
import com.copote.coop.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
