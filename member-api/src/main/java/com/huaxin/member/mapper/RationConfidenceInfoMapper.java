package com.huaxin.member.mapper;

import com.huaxin.member.model.RationConfidenceInfo;

import java.util.List;
import java.util.Map;

public interface RationConfidenceInfoMapper {

    List<Map<String,Object>> findList(String rationId);

    void saveOrUpdateAnswer(Map<String,Object> params);

    void saveAnswer(List<RationConfidenceInfo> params);

}
