package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;

import com.huaxin.member.service.HxOrganizationService;
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
@RequestMapping("/hxOrg")
public class HxOrganizationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HxOrganizationService hxOrganizationService;

    /**
     * 查询公司信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = hxOrganizationService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.POST)
    public Result findTree(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = hxOrganizationService.findTree(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }




    /**
     * 新增/更新公司信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateOrg", method = RequestMethod.POST)
    public Result saveOrUpdateOrg(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxOrganizationService.saveOrUpdateOrg(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除公司
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteOrgId", method = RequestMethod.POST)
    public Result deleteOrgId(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxOrganizationService.deleteOrgId(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
