package com.huaxin.member.service;

import com.huaxin.member.model.HxOrganizationMenu;

import java.util.List;
import java.util.Map;

public interface HxOrganizationMenuService {


    List<HxOrganizationMenu> findList(Map<String, Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

    void saveOrganizationMenu(List<HxOrganizationMenu> params);

}
