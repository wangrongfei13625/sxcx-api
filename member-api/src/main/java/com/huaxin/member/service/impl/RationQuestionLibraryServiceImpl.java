package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.RationConfidenceInfoMapper;
import com.huaxin.member.mapper.RationQuestionLibraryMapper;
import com.huaxin.member.model.RationConfidenceInfo;
import com.huaxin.member.model.RationQuestionLibrary;
import com.huaxin.member.service.RationQuestionLibraryService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RationQuestionLibraryServiceImpl implements RationQuestionLibraryService {

    @Autowired
    private RationQuestionLibraryMapper rationQuestionLibraryMapper;

    @Autowired
    private RationConfidenceInfoMapper rationAnswerInfoMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = rationQuestionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            String rationId = map.get("id").toString();
            List<Map<String,Object>> answer = rationAnswerInfoMapper.findList(rationId);
            map.put("confidence",answer);

        }

        return new PageInfo(list);
    }

    @Override
    public  List<Map<String,Object>> findQuestion(Map<String,Object> params){
        if(params.get("manageIds")!=null && params.get("manageIds")!=""){
            String manageIds =params.get("manageIds").toString();
            String[] manageId = manageIds.split(",");
            params.put("manageIds",manageId);
        }
        List<Map<String,Object>> list = rationQuestionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            String rationId = map.get("id").toString();
            List<Map<String,Object>> answer = rationAnswerInfoMapper.findList(rationId);
            map.put("confidence",answer);

        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateLibrary(Map<String,Object> params){
        RationQuestionLibrary ration = new RationQuestionLibrary();

        ration.setTitle(params.get("title").toString());
        ration.setManageId(params.get("manageId").toString());
        ration.setRationType(params.get("rationType").toString());
        ration.setRationRemark(params.get("rationRemark").toString());
        ration.setRationData(params.get("rationData").toString());
        ration.setRationCode(params.get("rationCode").toString());
        ration.setEdition(params.get("edition").toString());
        rationQuestionLibraryMapper.saveRation(ration);

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("confidence");

        List<RationConfidenceInfo> infoList = new ArrayList<>();

        for(Map<String,Object> map : list){

            RationConfidenceInfo rationConfidenceInfo = new RationConfidenceInfo();
            rationConfidenceInfo.setTitle(map.get("title").toString());
            rationConfidenceInfo.setFraction(map.get("fraction").toString());
            rationConfidenceInfo.setRationId(ration.getId());
            infoList.add(rationConfidenceInfo);

        }
        rationAnswerInfoMapper.saveAnswer(infoList);

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
    @Transactional(propagation = Propagation.REQUIRED)
    public void copyRation(Map<String,Object> params){

        //根据版本号 查询所有定量题库数据
        List<Map<String,Object>> list = rationQuestionLibraryMapper.findList(params);
        for(Map<String,Object> map:list){
            String rationId = map.get("id").toString();
            List<Map<String,Object>> answer = rationAnswerInfoMapper.findList(rationId);
            //更改版本号，将数据存入ration,将ID 改为null 进行重新存入
            RationQuestionLibrary ration = new RationQuestionLibrary();
            ration.setTitle(params.get("title").toString());
            ration.setManageId(params.get("manageId").toString());
            ration.setRationType(params.get("rationType").toString());
            ration.setRationRemark(params.get("rationRemark").toString());
            ration.setRationData(params.get("rationData").toString());
            ration.setRationCode(params.get("rationCode").toString());
            ration.setEdition(params.get("edition").toString());
            rationQuestionLibraryMapper.saveRation(ration);


            // 将答案复制
            List<RationConfidenceInfo> infoList = new ArrayList<>();

            for(Map<String,Object> maps : answer){

                RationConfidenceInfo rationConfidenceInfo = new RationConfidenceInfo();
                rationConfidenceInfo.setTitle(map.get("title").toString());
                rationConfidenceInfo.setFraction(map.get("fraction").toString());
                rationConfidenceInfo.setRationId(ration.getId());
                infoList.add(rationConfidenceInfo);

            }
            rationAnswerInfoMapper.saveAnswer(infoList);

        }



    }

}
