package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaseUserService {


    PageInfo findByUser(Map<String, Object> params);

    List<Map<String,Object>> findHoDepartment(Map<String, Object> params);

}
