package com.huaxin.member.mapper;

import java.util.List;
import java.util.Map;

public interface PerformanceManageMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOrUpdateInfo(Map<String,Object> params);

    void deleteInfo(Map<String,Object> params);

}
