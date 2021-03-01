package com.huaxin.member.mapper;

import com.huaxin.member.model.ExamInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ExamInfoMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    List<Map<String,Object>> findListOfRationQuestion(Map<String,Object> params);

    LinkedList<Map<String,Object>> findListOfRationConfidence(Map<String,Object> params);

    List<Map<String,Object>> findListOfQualitative(Map<String,Object> params);



    void saveExamInfo(List<ExamInfo> params);

    void saveExamInfoId(ExamInfo examInfo);

    void updateExamInfo(ExamInfo examInfo);


}
