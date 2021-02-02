package com.huaxin.member.service.impl;


import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.HxRoleMenuMapper;

import com.huaxin.member.model.HxRoleMenu;
import com.huaxin.member.service.HxRoleMenuService;
import com.huaxin.member.util.PageUtils;
import com.huaxin.member.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HxRoleMenuServiceImpl implements HxRoleMenuService {

    @Autowired
    private HxRoleMenuMapper hxRoleMenuMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxRoleMenu> list = hxRoleMenuMapper.findList(params);

        return new PageInfo(list);
    }



    @Override
    public void saveOrUpdateMenu(Map<String,Object> params){
        hxRoleMenuMapper.saveOrUpdateMenu(params);
    }

    @Override
    public void deleteMenu(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        hxRoleMenuMapper.deleteMenu(params);
    }

}
