package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.model.LoginUser;

import java.util.List;
import java.util.Map;

public interface LoginUserService {

    LoginUser login(String username, String pwd);

    LoginUser findByLoginName(String loginName);

    List<Map<String,Object>> findNotOfRoleId(Map<String, Object> params);

    List<Map<String,Object>> findOfRoleId(String roleId);

    PageInfo findList(Map<String, Object> params);

    void updateUser(Map<String, Object> params);

    void saveHxUser(Map<String,Object> params);

    void deleteUser(Map<String, Object> params);

}
