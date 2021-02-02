package com.huaxin.member.service.impl;



import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.BaseUserMapper;
import com.huaxin.member.service.BaseUserService;
import com.huaxin.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Override
    public PageInfo findByUser(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String,Object>> userList = baseUserMapper.findByUser(params);
        for(Map<String,Object> user: userList){
            Map<String,Object> map = new HashMap<>();

            map.put("userId",user.get("id")==null?null:user.get("id").toString());
            map.put("userName",user.get("user_name")==null?null:user.get("user_name").toString());
            map.put("loginName",user.get("login_name")==null?null:user.get("login_name").toString());
            map.put("password",user.get("login_password")==null?null:user.get("login_password").toString());
            map.put("orgId",user.get("ORGANIZATION_ID")==null?null:user.get("ORGANIZATION_ID").toString());
            map.put("createTime",user.get("create_time")==null?null:user.get("create_time").toString());
            map.put("sex",user.get("sex")==null?null:user.get("sex").toString());
            map.put("phone",user.get("phone")==null?null:user.get("phone").toString());
            map.put("address",user.get("address")==null?null:user.get("address").toString());

            list.add(map);

        }

        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String,Object>> findHoDepartment(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> departMentList = baseUserMapper.findHoDepartment(params);
        for(Map<String,Object> departMent: departMentList){
            Map<String,Object> map = new HashMap<>();

            map.put("orgId",departMent.get("id")==null?null:departMent.get("id").toString());
            map.put("code",departMent.get("CODE")==null?null:departMent.get("CODE").toString());
            map.put("orgName",departMent.get("NAME")==null?null:departMent.get("NAME").toString());
            map.put("description",departMent.get("DESCRIPTION")==null?null:departMent.get("DESCRIPTION").toString());
            map.put("companyCode",departMent.get("COMPANY_CODE")==null?null:departMent.get("COMPANY_CODE").toString());
            map.put("pid",departMent.get("pid")==null?null:departMent.get("pid").toString());
            list.add(map);

        }


        return list;
    }



}
