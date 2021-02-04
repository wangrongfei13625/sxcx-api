package com.huaxin.member.mapper;


import com.huaxin.member.model.LoginUser;

import java.util.List;
import java.util.Map;

public interface LoginUserMapper {

    LoginUser login(Map<String, Object> map);

    LoginUser findByLoginName(String loginName);

    List<Map<String,Object>> findNotOfRoleId(String roleId);

    List<Map<String,Object>> findOfRoleId(String roleId);

    List<LoginUser> findList(Map<String, Object> params);

    void updateUser(Map<String, Object> params);

    void deleteUser(Map<String, Object> params);

}
