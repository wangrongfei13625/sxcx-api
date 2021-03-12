package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.PerformanceManageMapper;
import com.huaxin.member.service.PerformanceManageService;
import com.huaxin.member.service.QualitativeQuestionLibraryService;
import com.huaxin.member.service.RationQuestionLibraryService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceManageServiceImpl implements PerformanceManageService {

    @Autowired
    private PerformanceManageMapper performanceManageMapper;

    @Autowired
    private RationQuestionLibraryService rationQuestionLibraryService;


    @Autowired
    private QualitativeQuestionLibraryService questionLibraryService;



    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = performanceManageMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateInfo(Map<String,Object> params){

        //保存试卷 得到 manageId, 通过 manageId 得到 题库中 试卷的题目， 并复制出来
        performanceManageMapper.saveOrUpdateInfo(params);
        Map<String,Object> map = new HashMap<>();
        map.put("manageIds",params.get("manageId"));
        map.put("edition",params.get("planEdition"));
        if(params.get("id")!=null){
            // 删除 所有图库中的数据， 根据  版本 manageId
            rationQuestionLibraryService.deleteOfIds(map);


            questionLibraryService.deleteOfIds(map);

        }
        // 查询得到 题库题目 根据 manageId
        rationQuestionLibraryService.copyRation(map);

        questionLibraryService.copyQualitative(map);



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
