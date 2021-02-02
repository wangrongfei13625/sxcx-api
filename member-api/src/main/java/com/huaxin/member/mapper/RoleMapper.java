package com.huaxin.member.mapper;

import com.huaxin.member.model.HxRole;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    List<HxRole> findList(Map<String,Object> params);

    void saveOrUpdateRole(Map<String,Object> params);

    void deleteRole(Map<String,Object> params);



}
