package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.model.HxOrganization;

import java.util.List;
import java.util.Map;

public interface HxOrganizationService {


    PageInfo findList(Map<String,Object> params);

    List<Map<String,Object>> findTree(Map<String,Object> params);

    void saveOrUpdateOrg(Map<String,Object> params);

    void deleteOrgId(Map<String,Object> params);

}
