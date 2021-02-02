package com.huaxin.member.mapper;

import java.util.List;
import java.util.Map;

public interface BaseUserMapper {

    List<Map<String,Object>> findByUser(Map<String,Object> params);

    List<Map<String,Object>> findHoDepartment(Map<String,Object> params);

}
