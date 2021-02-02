package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = roleService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新角色
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateRole", method = RequestMethod.POST)
    public Result saveOrUpdateRole(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            roleService.saveOrUpdateRole(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除角色
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public Result deleteRole(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            roleService.deleteRole(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
