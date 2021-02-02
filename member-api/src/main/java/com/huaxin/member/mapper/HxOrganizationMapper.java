package com.huaxin.member.mapper;



import com.huaxin.member.model.HxOrganization;

import java.util.List;
import java.util.Map;

public interface HxOrganizationMapper {

    List<HxOrganization> findList(Map<String,Object> params);

    void saveOrUpdateOrg(Map<String,Object> params);

    void deleteOrgId(Map<String,Object> params);

}
