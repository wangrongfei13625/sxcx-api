package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.ManageTypeInfoMapper;
import com.huaxin.member.service.ManageTypeInfoService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class ManageTypeInfoServiceImpl implements ManageTypeInfoService {

    @Autowired
    private ManageTypeInfoMapper manageTypeInfoMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = manageTypeInfoMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public List<Map<String,Object>> findNoPageOfList(Map<String,Object> params){

        List<Map<String,Object>> list = manageTypeInfoMapper.findList(params);

        return  list;
    }

    @Override
    public void saveOrUpdateInfo(Map<String,Object> params){
        manageTypeInfoMapper.saveOrUpdateInfo(params);
    }

    @Override
    public void deleteInfo(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        manageTypeInfoMapper.deleteInfo(params);
    }

}
