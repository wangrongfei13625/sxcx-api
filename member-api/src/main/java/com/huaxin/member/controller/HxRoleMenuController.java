package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
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
@RequestMapping("/hxRoleMenu")
public class HxRoleMenuController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HxRoleMenuService hxRoleMenuService;


    /**
     * 查询菜单权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = hxRoleMenuService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    /**
     * 新增/更新菜单权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST)
    public Result saveOrUpdateMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxRoleMenuService.saveOrUpdateMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除菜单权限
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    public Result deleteMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxRoleMenuService.deleteMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
