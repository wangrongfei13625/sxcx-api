package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.ManageTypeInfoService;
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
@RequestMapping("/manageTypeInfo")
public class ManageTypeInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ManageTypeInfoService manageTypeInfoService;

    /**
     * 查询管理类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = manageTypeInfoService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新管理类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateInfo", method = RequestMethod.POST)
    public Result saveOrUpdateInfo(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            manageTypeInfoService.saveOrUpdateInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除管理类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteInfo", method = RequestMethod.POST)
    public Result deleteInfo(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            manageTypeInfoService.deleteInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



}
