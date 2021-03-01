package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface HxMenuService {

    PageInfo findList(Map<String, Object> params);

    List<Map<String,Object>> findTree(Map<String,Object> params);

    List<Map<String,Object>> findTreeOfLoginName(Map<String,Object> params);

    void saveOrUpdateMenu(Map<String, Object> params);

    void deleteMenu(Map<String, Object> params);

}
