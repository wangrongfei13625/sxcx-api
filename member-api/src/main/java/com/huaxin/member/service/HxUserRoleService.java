package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface HxUserRoleService {

    PageInfo findList(Map<String, Object> params);

    void saveOrUpdateUserRole(Map<String, Object> params);

    void deleteUserRole(Map<String, Object> params);

}
