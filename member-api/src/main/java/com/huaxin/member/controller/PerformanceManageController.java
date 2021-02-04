package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.PerformanceManageService;
import com.huaxin.member.service.QualitativeQuestionLibraryService;
import com.huaxin.member.service.RationQuestionLibraryService;
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
@RequestMapping("/performanceManage")
public class PerformanceManageController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerformanceManageService performanceManageService;

    @Autowired
    private QualitativeQuestionLibraryService questionLibraryService;

    @Autowired
    private RationQuestionLibraryService rationQuestionLibraryService;

    /**
     * 查询绩效管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = performanceManageService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查看定性评估
     * @param params
     * @return
     */
    @RequestMapping(value = "/findQualitativeQuestion", method = RequestMethod.POST)
    public Result findQualitativeQuestion(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = questionLibraryService.findQuestion(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 查看定量评估
     * @param params
     * @return
     */
    @RequestMapping(value = "/findRationQuestion", method = RequestMethod.POST)
    public Result findRationQuestion(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = rationQuestionLibraryService.findQuestion(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新绩效管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateInfo", method = RequestMethod.POST)
    public Result saveOrUpdateInfo(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            performanceManageService.saveOrUpdateInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除绩效管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteInfo", method = RequestMethod.POST)
    public Result deleteInfo(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            performanceManageService.deleteInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
