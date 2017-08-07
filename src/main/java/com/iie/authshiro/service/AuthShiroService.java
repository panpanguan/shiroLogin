package com.iie.authshiro.service;

import com.iie.authshiro.pojo.User;
import com.iie.utils.UtilStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyberpecker on 2017/4/24.
 */
@Service
public class AuthShiroService {

    public void loginCheck(User user, UtilStatus authenInfo){
        //Map authenInfo = new HashMap();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPwd());
            //token.setRememberMe(true);
            try {
                // 执行登录.
                currentUser.login(token);
            }
            //认证时异常.
            catch (UnknownAccountException e) {
                authenInfo.setKey(-1);
                authenInfo.setValue("用户名/密码错误");
                //authenInfo.put("unknown","用户名/密码错误");
            } catch (IncorrectCredentialsException e) {
                authenInfo.setKey(-2);
                authenInfo.setValue("用户名/密码错误，剩余"+e.getMessage()+"次");
                //authenInfo.put("incorrect","用户名/密码错误，剩余"+e.getMessage()+"次");
            } catch (ExcessiveAttemptsException e) {
                authenInfo.setKey(-3);
                authenInfo.setValue("登录失败多次，账号"+e.getMessage());
                //authenInfo.put("excessiveAttempt","登录失败多次，账号"+e.getMessage());
            } catch (LockedAccountException e){
                authenInfo.setKey(-4);
                authenInfo.setValue("用户被锁定！");
               //authenInfo.put("locked","用户被锁定！");
            } catch (AuthenticationException e) {
                authenInfo.setKey(-5);
                authenInfo.setValue("验证错误,请联系管理员：" + e.getMessage());
                //authenInfo.put("authenException","验证错误,请联系管理员：" + e.getMessage());
            }
            if (authenInfo.getKey() ==0) {// 验证成功，返回相应的界面或信息

                authenInfo.setKey(1);
                authenInfo.setValue("登录成功！");
                //authenInfo.put("success","登录成功！");
                //return authenInfo;
            }
        }else{
            authenInfo.setKey(2);
            authenInfo.setValue("已经登录！");
            //authenInfo.put("isAuthenticated","已经登录！");
            //return authenInfo;
        }
    }

}
