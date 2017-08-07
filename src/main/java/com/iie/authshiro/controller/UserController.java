package com.iie.authshiro.controller;

import com.iie.authshiro.service.AuthShiroService;
import com.iie.authshiro.pojo.User;
import com.iie.authshiro.service.UserService;
import com.iie.utils.UtilStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by cyberpecker on 2017/4/7.
 */
@Controller
@RequestMapping("shiro")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthShiroService authShiroService;

    @RequestMapping("/loginCheck")
    public String loginCheck(ModelMap modelMap, User user){

        //获取登录验证信息
        /*1）1 登录成功
        * 2）2 已经验证登录
        * 3）-1 用户不存在
        * 4）-2 密码错误
        * 5）-3 登录失败多次，被锁定*时间
        * 6）-4 用户被锁定
        * 7）-5 其它验证错误*/
        UtilStatus authenInfo = new UtilStatus();
        authShiroService.loginCheck(user, authenInfo);
        if (authenInfo.getKey()!=0){
            //验证通过，或者已经是登录状态
            if (authenInfo.getKey()>0 ){
                return redircetUrl();
            }else if (authenInfo.getKey()<0){ //验证错误，返回错误信息
                String error = null;
                error = authenInfo.getValue();
                modelMap.addAttribute("error", error);
                return "error";
            }
        }
        return "redirect:/404.jsp";
    }
    /*根据用户的角色跳转页面*/
    private String redircetUrl(){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.hasRole("user")
                && currentUser.hasRole("admin")){
            return "list";
        }else if(currentUser.hasRole("admin")){
            return "admin";
        }else if (currentUser.hasRole("user")){
            return "user";
        } else {
            return "redirect:/index.jsp";
        }
    }
    @RequestMapping("/logout")
    public String logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/index.jsp";
    }

    @RequestMapping("/loginAdmin")
    public String loginAdmin(HttpSession session){

        return "admin";
    }

    @RequestMapping("/loginUser")
    public String loginUser(){
        return "user";
    }

    @RequestMapping("/register")
    public String register(){
        return "redirect:/register.jsp";
    }

    @RequestMapping("/addRegister")
    public String addRegister(User user){
        if (user !=null){
            if(user.getUsername().length()>0
                && user.getPwd().length()>0){
                try {
                    userService.addRegister(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error";
                }
                return "success";
            }
        }
        return "error";
    }

}

