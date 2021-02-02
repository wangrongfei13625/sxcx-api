package com.huaxin.member.mapper;

import com.huaxin.member.model.HxRoleMenu;

import java.util.List;
import java.util.Map;

public interface HxRoleMenuMapper {

    List<HxRoleMenu> findList(Map<String, Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

}
