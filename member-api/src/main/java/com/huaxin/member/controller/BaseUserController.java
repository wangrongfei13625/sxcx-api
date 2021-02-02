package com.huaxin.member.controller;


import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.BaseUserService;

import com.huaxin.member.util.base.Result;
import com.huaxin.member.util.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baseUser")
public class BaseUserController {


    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;

    /**
     * 查询用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Result findByUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = baseUserService.findByUser(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 查询部门信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHoDepartment", method = RequestMethod.POST)
    public Result findHoDepartment(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = baseUserService.findHoDepartment(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



}
