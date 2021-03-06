package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.HxUserRoleMapper;
import com.huaxin.member.model.HxUserRole;
import com.huaxin.member.service.HxUserRoleService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HxUserRoleServiceImpl implements HxUserRoleService {

    @Autowired
    private HxUserRoleMapper hxUserRoleMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxUserRole> list = hxUserRoleMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdateUserRole(Map<String,Object> params){
        hxUserRoleMapper.saveOrUpdateUserRole(params);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserRole(List<HxUserRole> params) {
        //删除角色关系 根据RoleId
        HxUserRole hxUserRole = params.get(0);
        hxUserRoleMapper.deleteOfRoleId(hxUserRole.getRoleId().toString());

        hxUserRoleMapper.saveUserRole(params);
    }
    @Override
    public void deleteUserRole(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        hxUserRoleMapper.deleteUserRole(params);
    }

}
