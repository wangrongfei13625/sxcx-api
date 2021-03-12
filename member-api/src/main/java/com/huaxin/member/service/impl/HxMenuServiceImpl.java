package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.HxMenuMapper;
import com.huaxin.member.model.HxMenu;
import com.huaxin.member.service.HxMenuService;
import com.huaxin.member.util.PageUtils;
import com.huaxin.member.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HxMenuServiceImpl implements HxMenuService {

    @Autowired
    private HxMenuMapper hxMenuMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxMenu> list = hxMenuMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public  List<Map<String,Object>> findTree(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxMenu> hxMenus = hxMenuMapper.findList(params);
        List<Map<String,Object>> list = new ArrayList<>();
        for(HxMenu hxMenu:hxMenus){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hxMenu.getId());
            map.put("name",hxMenu.getName()==null?"":hxMenu.getName());
            map.put("url",hxMenu.getUrl()==null?"":hxMenu.getUrl());
            map.put("type",hxMenu.getType()==null?"":hxMenu.getType());
            map.put("pid",hxMenu.getParentId()==null?"":hxMenu.getParentId());
            map.put("icon",hxMenu.getIcon()==null?"":hxMenu.getIcon());
            map.put("orderNum",hxMenu.getOrderNum()==null?"":hxMenu.getOrderNum());
            map.put("createBy",hxMenu.getCreateBy()==null?"":hxMenu.getCreateBy());
            map.put("createTime",hxMenu.getCreateTime()==null?"":hxMenu.getCreateTime());
            list.add(map);
        }

        TreeUtil util = new TreeUtil();
        return util.treeNodeList(list);
    }


    @Override
    public  List<Map<String,Object>> findTreeOfLoginName(Map<String,Object> params){
        //PageUtils.initPage(params);
        List<HxMenu> hxMenus = hxMenuMapper.findTreeOfLoginName(params);
        List<Map<String,Object>> list = new ArrayList<>();
        for(HxMenu hxMenu:hxMenus){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hxMenu.getId());
            map.put("name",hxMenu.getName()==null?"":hxMenu.getName());
            map.put("url",hxMenu.getUrl()==null?"":hxMenu.getUrl());
            map.put("type",hxMenu.getType()==null?"":hxMenu.getType());
            map.put("pid",hxMenu.getParentId()==null?"":hxMenu.getParentId());
            map.put("icon",hxMenu.getIcon()==null?"":hxMenu.getIcon());
            map.put("orderNum",hxMenu.getOrderNum()==null?"":hxMenu.getOrderNum());
            map.put("createBy",hxMenu.getCreateBy()==null?"":hxMenu.getCreateBy());
            map.put("createTime",hxMenu.getCreateTime()==null?"":hxMenu.getCreateTime());
            list.add(map);
        }

        TreeUtil util = new TreeUtil();
        return util.treeNodeList(list);
    }

    @Override
    public void saveOrUpdateMenu(Map<String,Object> params){
        hxMenuMapper.saveOrUpdateMenu(params);
    }

    @Override
    public void deleteMenu(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        hxMenuMapper.deleteMenu(params);
    }



}
