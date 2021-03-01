package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.PerformanceManageMapper;
import com.huaxin.member.service.PerformanceManageService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PerformanceManageServiceImpl implements PerformanceManageService {

    @Autowired
    private PerformanceManageMapper performanceManageMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = performanceManageMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdateInfo(Map<String,Object> params){
        performanceManageMapper.saveOrUpdateInfo(params);
    }

    @Override
    public void updateRelease(Map<String,Object> params){
        performanceManageMapper.updateRelease(params);
    }


    @Override
    public void deleteInfo(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        performanceManageMapper.deleteInfo(params);
    }

}
