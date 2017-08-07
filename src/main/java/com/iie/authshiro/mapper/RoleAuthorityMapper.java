package com.iie.authshiro.mapper;

import com.iie.authshiro.pojo.RoleAuthority;

import java.util.List;

public interface RoleAuthorityMapper {

    int insert(RoleAuthority record);

    List<RoleAuthority> selectRoleAndAuthority();
}