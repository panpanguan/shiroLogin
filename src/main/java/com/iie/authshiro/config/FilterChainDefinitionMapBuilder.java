package com.iie.authshiro.config;

import com.iie.authshiro.pojo.RoleAuthority;
import com.iie.authshiro.service.RoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

public class FilterChainDefinitionMapBuilder {

    @Autowired
    private RoleAuthorityService roleAuthorityService;

   /* public RoleAuthorityService getRoleAuthorityService() {
        return roleAuthorityService;
    }

    public void setRoleAuthorityService(RoleAuthorityService roleAuthorityService) {
        this.roleAuthorityService = roleAuthorityService;
    }*/

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() throws Exception {

       /* 配置哪些页面需要受保护.以及访问这些页面需要的权限.
        1). anon 可以被匿名访问
        2). authc 必须认证(即登录)后才可能访问的页面.
        3). logout 登出.
        4). roles 角色过滤器*/
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        //从角色权限表获取数据
        List<RoleAuthority> roleAuthorities = roleAuthorityService.selectRoleAndAuthority();
        if (roleAuthorities!=null){
            for (RoleAuthority roleAuthority : roleAuthorities){

                map.put(""+roleAuthority.getUrl()+"","kickout,authc,roles["+roleAuthority.getRoleKey()+"]");
            }
        }
        return map;
    }

}
