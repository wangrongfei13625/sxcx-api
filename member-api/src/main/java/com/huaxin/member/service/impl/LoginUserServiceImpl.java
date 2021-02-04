package com.huaxin.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.mapper.LoginUserMapper;

import com.huaxin.member.model.LoginUser;
import com.huaxin.member.service.LoginUserService;
import com.huaxin.member.util.PageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService {


    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * 登录
     *
     * @param
     * @return
     * @throws Exception
     */
    public LoginUser login(String username, String pwd) {
        logger.info("登录.....");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("login_name", username);
        map.put("login_passwd", pwd);
        LoginUser user = loginUserMapper.login(map);
        return user;
    }

    @Override
    public LoginUser findByLoginName(String loginName) {
        return loginUserMapper.findByLoginName(loginName);
    }

    @Override
    public List<Map<String,Object>> findNotOfRoleId(String roleId){
        return loginUserMapper.findNotOfRoleId(roleId);
    }

    @Override
    public List<Map<String,Object>> findOfRoleId(String roleId){
        return loginUserMapper.findOfRoleId(roleId);
    }

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<LoginUser> list = loginUserMapper.findList(params);

        return new PageInfo(list);
    }

    @Override
    public void updateUser(Map<String,Object> params){
        loginUserMapper.updateUser(params);
    }

    @Override
    public void deleteUser(Map<String,Object> params){
        if(params.get("ids")!=null && params.get("ids")!=""){
            String ids =params.get("ids").toString();
            String[] id = ids.split(",");
            params.put("ids",id);
        }
        loginUserMapper.deleteUser(params);
    }

}
