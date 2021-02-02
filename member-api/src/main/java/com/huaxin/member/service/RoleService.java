package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface RoleService {

    PageInfo findList(Map<String,Object> params);

    void saveOrUpdateRole(Map<String,Object> params);

    void deleteRole(Map<String,Object> params);

}
