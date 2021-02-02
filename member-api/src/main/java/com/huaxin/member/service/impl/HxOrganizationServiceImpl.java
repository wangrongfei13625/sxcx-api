package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.HxOrganizationMapper;
import com.huaxin.member.model.HxOrganization;
import com.huaxin.member.service.HxOrganizationService;
import com.huaxin.member.util.PageUtils;
import com.huaxin.member.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HxOrganizationServiceImpl implements HxOrganizationService {

    @Autowired
    private HxOrganizationMapper hxOrganizationMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxOrganization> list = hxOrganizationMapper.findList(params);
        return new PageInfo(list);
    }


    @Override
    public  List<Map<String,Object>> findTree(Map<String,Object> params){
        PageUtils.initPage(params);
        List<HxOrganization> hxOrganizations = hxOrganizationMapper.findList(params);
        List<Map<String,Object>> list = new ArrayList<>();
        for(HxOrganization hxOrganization:hxOrganizations){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hxOrganization.getId());
            map.put("organizationName",hxOrganization.getOrganizationName()==null?"":hxOrganization.getOrganizationName());
            map.put("telPhone",hxOrganization.getTelPhone()==null?"":hxOrganization.getTelPhone());
            map.put("describe",hxOrganization.getDescribe()==null?"":hxOrganization.getDescribe());
            map.put("contacts",hxOrganization.getContacts()==null?"":hxOrganization.getContacts());
            map.put("organizationType",hxOrganization.getOrganizationType()==null?"":hxOrganization.getOrganizationType());
            map.put("organizationNum",hxOrganization.getOrganizationNum()==null?"":hxOrganization.getParentId());
            map.put("pid",hxOrganization.getParentId()==null?"":hxOrganization.getParentId());
            map.put("createTime",hxOrganization.getCreateTime()==null?"":hxOrganization.getCreateTime());
            list.add(map);
        }

        TreeUtil util = new TreeUtil();
        List<Map<String,Object>> lists = util.treeNodeList(list);
        return lists;
    }

    @Override
    public void saveOrUpdateOrg(Map<String,Object> params){
        hxOrganizationMapper.saveOrUpdateOrg(params);
    }

    @Override
    public void deleteOrgId(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        hxOrganizationMapper.deleteOrgId(params);
    }



}
