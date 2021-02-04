package com.huaxin.member.mapper;


import com.huaxin.member.model.HxUserRole;

import java.util.List;
import java.util.Map;

public interface HxUserRoleMapper {

    List<HxUserRole> findList(Map<String, Object> params);

    void saveOrUpdateUserRole(Map<String, Object> params);

    void deleteUserRole(Map<String, Object> params);

    void saveUserRole(List<HxUserRole> params);

    void deleteOfRoleId(String roleId);



}
