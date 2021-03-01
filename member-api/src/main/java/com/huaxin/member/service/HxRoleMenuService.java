package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.model.HxRoleMenu;

import java.util.List;
import java.util.Map;

public interface HxRoleMenuService {

    List<HxRoleMenu> findList(Map<String, Object> params);


    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

    void saveRoleMenu(List<HxRoleMenu> params);

}
