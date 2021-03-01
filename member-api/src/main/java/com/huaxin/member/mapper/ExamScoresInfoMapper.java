package com.huaxin.member.mapper;

import java.util.List;
import java.util.Map;

public interface ExamScoresInfoMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveExamScoresInfo(Map<String,Object> params);

    List<Map<String,Object>> findSumOfUserId(Map<String,Object> params);

}
