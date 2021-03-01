package com.huaxin.member.mapper;

import com.huaxin.member.model.HxOrganizationMenu;

import java.util.List;
import java.util.Map;

public interface HxOrganizationMenuMapper {

    List<HxOrganizationMenu> findList(Map<String, Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

    void deleteOrganizationMenuOfId(String roleId);

    void saveOrganizationMenu(List<HxOrganizationMenu> params);

}
