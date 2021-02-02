package com.huaxin.member.service;

import com.huaxin.member.model.LoginUser;

public interface LoginUserService {

    LoginUser login(String username, String pwd);

    LoginUser findByLoginName(String loginName);

}
