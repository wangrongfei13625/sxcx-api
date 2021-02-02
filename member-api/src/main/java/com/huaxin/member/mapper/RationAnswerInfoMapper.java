package com.huaxin.member.mapper;

import java.util.List;
import java.util.Map;

public interface RationAnswerInfoMapper {

    List<Map<String,Object>> findList(String rationId);

    void saveOrUpdateAnswer(Map<String,Object> params);

}
