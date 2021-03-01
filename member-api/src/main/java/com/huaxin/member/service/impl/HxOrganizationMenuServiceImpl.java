package com.huaxin.member.service.impl;

import com.huaxin.member.mapper.HxOrganizationMenuMapper;
import com.huaxin.member.model.HxOrganizationMenu;
import com.huaxin.member.service.HxOrganizationMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HxOrganizationMenuServiceImpl implements HxOrganizationMenuService {

    @Autowired
    private HxOrganizationMenuMapper hxOrganizationMenuMapper;


    @Override
    public List<HxOrganizationMenu> findList(Map<String, Object> params) {

        List<HxOrganizationMenu> list = hxOrganizationMenuMapper.findList(params);

        return list;
    }

    @Override
    public void saveOrUpdateMenu(Map<String,Object> params){
        hxOrganizationMenuMapper.saveOrUpdateMenu(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrganizationMenu(List<HxOrganizationMenu> params){
        //删除所有记录 根据roleId
        HxOrganizationMenu organizationMenu = params.get(0);
        hxOrganizationMenuMapper.deleteOrganizationMenuOfId(organizationMenu.getUserId().toString());

        hxOrganizationMenuMapper.saveOrganizationMenu(params);
    }

    @Override
    public void deleteMenu(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        hxOrganizationMenuMapper.deleteMenu(params);
    }
}
