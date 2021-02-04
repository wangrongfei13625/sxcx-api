package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.model.HxUserRole;

import java.util.List;
import java.util.Map;

public interface HxUserRoleService {

    PageInfo findList(Map<String, Object> params);

    void saveOrUpdateUserRole(Map<String, Object> params);

    void deleteUserRole(Map<String, Object> params);

    void saveUserRole(List<HxUserRole> params);

}
