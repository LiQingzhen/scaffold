package com.copote.coop.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author LQZ
 * @Date 2020/4/21 15:48
 **/
@Service
public class SecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;
    @Autowired
    public void setMyAccessDecisionManager(AccessDecisionManagerImpl accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.filterInvocationSecurityMetadataSource;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(filterInvocation);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        //filterInvocation里面有一个被拦截的url
        //里面调用InvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取filterInvocation对应的所有权限
        //再调用AccessDecisionManager的decide方法来校验用户的权限是否满足
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            //执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }
}
