package com.copote.coop.auth;

import com.alibaba.fastjson.JSON;
import com.copote.coop.bussiness.model.Account;
import com.copote.coop.bussiness.service.AccountService;
import com.copote.coop.util.JsonResult;
import com.copote.coop.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author LQZ
 * @Date 2020/4/20 15:24
 **/
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //更新用户表上次登录时间、更新人、更新时间等字段
        UserDetailsModel userDetails = (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.loadAccountDetails(userDetails.getUsername());
//        account.setPassword();
        accountService.updateAccount(account);

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //返回json数据
        JsonResult result = ResultTool.success();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));

    }
}
