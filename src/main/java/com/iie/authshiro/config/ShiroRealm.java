package com.iie.authshiro.config;

import com.iie.authshiro.pojo.User;
import com.iie.authshiro.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

   /* public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }*/

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException{

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        //2. 从 UsernamePasswordToken 中来获取 username
        String username = upToken.getUsername();

        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        User user = null;
        try {
            user = userService.selectByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if(user== null ){
            throw new UnknownAccountException("用户不存在!");
        }

        //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
        if("locked".equals(user.getRole())){
            throw new LockedAccountException("用户被锁定！");
        }

        //6. 从数据库中获取信息，根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 实现类为: SimpleAuthenticationInfo
        // realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();
        // 盐值.
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(user.getUsername(), user.getPwd(), credentialsSalt, realmName);
        return info;
    }

    //授权会被 shiro 回调的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();
        //2. 利用登录的用户的信息来用户当前用户的角色或权限，需要查询数据库
        User user = null;
        try {
            user = userService.selectByUserName((String) principal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String roleStr = user.getRole();
        Set<String> roles = new HashSet<String>();
        if (roleStr.length() !=0 && roleStr!=null){

            String[] roleArr = roleStr.split(",");
            CollectionUtils.addAll(roles, roleArr);
        }
        if(roles.size()==0){
            throw new UnauthorizedException("该账号为分配角色，授权失败");
        }
        //3. 创建 SimpleAuthorizationInfo, 并设置其 roles 属性.
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4. 返回 SimpleAuthorizationInfo 对象.
        return info;
    }

}
