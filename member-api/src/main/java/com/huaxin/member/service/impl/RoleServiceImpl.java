package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.RoleMapper;
import com.huaxin.member.model.HxRole;
import com.huaxin.member.service.RoleService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxRole> list = roleMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdateRole(Map<String,Object> params){
        roleMapper.saveOrUpdateRole(params);
    }

    @Override
    public void deleteRole(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        roleMapper.deleteRole(params);
    }

}
