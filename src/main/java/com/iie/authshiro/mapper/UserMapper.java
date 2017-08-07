package com.iie.authshiro.mapper;

import com.iie.authshiro.pojo.User;

public interface UserMapper {

    int insert(User record);

    User selectByUserName(String usrName);

}