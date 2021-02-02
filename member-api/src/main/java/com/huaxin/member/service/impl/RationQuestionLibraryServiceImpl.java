package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.RationAnswerInfoMapper;
import com.huaxin.member.mapper.RationQuestionLibraryMapper;
import com.huaxin.member.model.RationQuestionLibrary;
import com.huaxin.member.service.RationQuestionLibraryService;
import com.huaxin.member.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RationQuestionLibraryServiceImpl implements RationQuestionLibraryService {

    @Autowired
    private RationQuestionLibraryMapper rationQuestionLibraryMapper;

    @Autowired
    private RationAnswerInfoMapper rationAnswerInfoMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = rationQuestionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            String rationId = map.get("id").toString();
            List<Map<String,Object>> answer = rationAnswerInfoMapper.findList(rationId);
            map.put("answer",answer);

        }

        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdateLibrary(Map<String,Object> params){

        rationQuestionLibraryMapper.saveOrUpdateLibrary(params);

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("answer");

        for(Map<String,Object> map : list){
            rationAnswerInfoMapper.saveOrUpdateAnswer(map);
        }

    }

    @Override
    public void deleteLibrary(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        rationQuestionLibraryMapper.deleteLibrary(params);
    }

    /**
     * 复制版本数据
     */
    @Override
    public void copyRation(Map<String,Object> params){

        //根据版本号 查询所有定量题库数据
        List<Map<String,Object>> list = rationQuestionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            String rationId = map.get("id").toString();
            List<Map<String,Object>> answer = rationAnswerInfoMapper.findList(rationId);
            //更改版本号，将数据存入ration,将ID 改为null 进行重新存入
            map.put("id",null);
            map.put("edition",params.get("edition"));
            RationQuestionLibrary rationQuestionLibrary = rationQuestionLibraryMapper.saveRation(map);
            // 将答案复制
            for(Map<String,Object> answerMap : answer){
                answerMap.put("rationId",rationQuestionLibrary.getId());
                rationAnswerInfoMapper.saveOrUpdateAnswer(answerMap);
            }

        }



    }

}
