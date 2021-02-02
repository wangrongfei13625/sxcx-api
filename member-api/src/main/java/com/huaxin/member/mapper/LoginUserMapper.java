package com.huaxin.member.mapper;

import com.huaxin.member.model.LoginUser;

import java.util.Map;

public interface LoginUserMapper {

    LoginUser login(Map<String, Object> map);

    LoginUser findByLoginName(String loginName);

}
