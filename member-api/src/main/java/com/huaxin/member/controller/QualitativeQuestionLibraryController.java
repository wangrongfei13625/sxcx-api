package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.service.QualitativeQuestionLibraryService;
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
@RequestMapping("/questionLibrary")
public class QualitativeQuestionLibraryController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private QualitativeQuestionLibraryService questionLibraryService;


    /**
     * 查询定性题库
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = questionLibraryService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新定性题库
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateLibrary", method = RequestMethod.POST)
    public Result saveOrUpdateLibrary(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            questionLibraryService.saveOrUpdateLibrary(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除定性题库
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteLibrary", method = RequestMethod.POST)
    public Result deleteLibrary(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            questionLibraryService.deleteLibrary(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 复制
     * @param params
     * @return
     */
    @RequestMapping(value = "/copyQualitative", method = RequestMethod.POST)
    public Result copyQualitative(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            questionLibraryService.copyQualitative(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



}
