package com.copote.coop.auth;

import com.alibaba.fastjson.JSON;
import com.copote.coop.util.JsonResult;
import com.copote.coop.util.ResultCode;
import com.copote.coop.util.ResultTool;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author LQZ
 * @Date 2020/4/21 14:57
 **/
@Component
public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
