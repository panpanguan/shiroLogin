package com.iie.authshiro.service;

import com.iie.authshiro.mapper.RoleAuthorityMapper;
import com.iie.authshiro.pojo.RoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cyberpecker on 2017/4/19.
 */
@Service
public class RoleAuthorityService {

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    public List<RoleAuthority> selectRoleAndAuthority() throws Exception{

        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectRoleAndAuthority();

        return roleAuthorities;
    }

}
