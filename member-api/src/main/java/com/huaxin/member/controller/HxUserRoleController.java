package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.HxUserRoleService;
import com.huaxin.member.util.base.Result;
import com.huaxin.member.util.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/hxUserRole")
public class HxUserRoleController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HxUserRoleService hxUserRoleService;

    /**
     * 查询用户绑定关系
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = hxUserRoleService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新用户绑定关系
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateUserRole", method = RequestMethod.POST)
    public Result saveOrUpdateUserRole(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxUserRoleService.saveOrUpdateUserRole(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除用户绑定关系
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    public Result deleteUserRole(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxUserRoleService.deleteUserRole(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
