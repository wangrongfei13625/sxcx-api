package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.QualitativeQuestionLibraryMapper;
import com.huaxin.member.service.QualitativeQuestionLibraryService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QualitativeQuestionLibraryServiceImpl implements QualitativeQuestionLibraryService {

    @Autowired
    private QualitativeQuestionLibraryMapper questionLibraryMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = questionLibraryMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdateLibrary(Map<String,Object> params){
        questionLibraryMapper.saveOrUpdateLibrary(params);
    }

    @Override
    public void deleteLibrary(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        questionLibraryMapper.deleteLibrary(params);
    }

    @Override
    public void copyQualitative(Map<String,Object> params){

        //根据版本号查询出所有定量题库
        List<Map<String,Object>> list = questionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            map.put("id",null);
            map.put("edition",params.get("edition"));
            questionLibraryMapper.saveOrUpdateLibrary(map);
        }

    }

}
