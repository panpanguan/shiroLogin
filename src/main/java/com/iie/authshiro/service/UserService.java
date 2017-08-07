package com.iie.authshiro.service;

import com.iie.authshiro.mapper.UserMapper;
import com.iie.authshiro.pojo.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * Created by cyberpecker on 2017/4/7.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void addRegister(User user) throws Exception{
        if (user !=null){

            Object salt = String.valueOf(new Random());

            Object result = String.valueOf(new SimpleHash("MD5", user.getPwd(), salt, 1024));

            user.setPwd((String) result);
            user.setSalt((String) salt);
            user.setRegisterTime(new Date());

            userMapper.insert(user);

        }

    }

    public User selectByUserName(String username)throws Exception{

        return userMapper.selectByUserName(username);
    }
}
