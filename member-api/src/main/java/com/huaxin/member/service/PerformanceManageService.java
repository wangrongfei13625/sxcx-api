package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PerformanceManageService {

    PageInfo findList(Map<String,Object> params);

    void saveOrUpdateInfo(Map<String,Object> params);

    void deleteInfo(Map<String,Object> params);

}
