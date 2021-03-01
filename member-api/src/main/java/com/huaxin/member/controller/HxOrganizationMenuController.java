package com.huaxin.member.controller;

import com.huaxin.member.model.HxOrganizationMenu;
import com.huaxin.member.model.HxRoleMenu;
import com.huaxin.member.service.HxOrganizationMenuService;
import com.huaxin.member.service.HxRoleMenuService;
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
@RequestMapping("/hxOrganizationMenu")
public class HxOrganizationMenuController {


    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HxOrganizationMenuService hxOrganizationMenuService;


    /**
     * 查询人员组织结构权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<HxOrganizationMenu> list = hxOrganizationMenuService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 修改人员组织结构权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrganizationMenu", method = RequestMethod.POST)
    public Result saveOrganizationMenu(@RequestBody List<HxOrganizationMenu> params){

        Result result = new Result();
        try{
            hxOrganizationMenuService.saveOrganizationMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新人员组织结构权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST)
    public Result saveOrUpdateMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxOrganizationMenuService.saveOrUpdateMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除人员组织结构权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    public Result deleteMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxOrganizationMenuService.deleteMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
