package com.huaxin.member.mapper;

import com.huaxin.member.model.HxMenu;

import java.util.List;
import java.util.Map;

public interface HxMenuMapper {

    List<HxMenu> findList(Map<String, Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

}
