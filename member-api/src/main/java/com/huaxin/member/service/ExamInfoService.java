package com.huaxin.member.service;

import com.huaxin.member.model.ExamInfo;

import java.util.List;
import java.util.Map;

public interface ExamInfoService {

    void saveExamInfo(List<ExamInfo> params);

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOrUpdateExamInfo(List<ExamInfo> params);

    Map<String,Object> countExam(Map<String,Object> params);

}
