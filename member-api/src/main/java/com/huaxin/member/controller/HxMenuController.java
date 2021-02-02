package com.huaxin.member.controller;


import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.HxMenuService;
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
@RequestMapping("/hxMenu")
public class HxMenuController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HxMenuService hxMenuService;

    /**
     * 查询页面信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = hxMenuService.findList(params);
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
            List<Map<String,Object>> list = hxMenuService.findTree(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新页面信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST)
    public Result saveOrUpdateMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxMenuService.saveOrUpdateMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除页面信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    public Result deleteMenu(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            hxMenuService.deleteMenu(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }




}
