package com.copote.coop.auth;

import com.copote.coop.bussiness.model.Permission;
import com.copote.coop.bussiness.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Author LQZ
 * @Date 2020/4/21 15:14
 **/
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //查询具体某个接口的权限
        List<Permission> permissions =  permissionService.loadUrlPermissions(requestUrl);
        if(permissions == null || permissions.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissions.size()];
        for(int i = 0;i<permissions.size();i++){
            attributes[i] = permissions.get(i).getPermissionCode();
        }
        return SecurityConfig.createList(attributes);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
