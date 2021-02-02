package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface HxRoleMenuService {

    PageInfo findList(Map<String, Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

}
