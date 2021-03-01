package com.huaxin.member.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.member.model.ExamInfo;
import com.huaxin.member.service.ExamInfoService;
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

/**
 * 数据采集
 */
@RestController
@RequestMapping("/examInfo")
public class ExamInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ExamInfoService examInfoService;


    /**
     * 查询题目
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = examInfoService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 提交
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveExamInfo", method = RequestMethod.POST)
    public Result saveExamInfo(@RequestBody List<ExamInfo> params){

        Result result = new Result();
        try{
            examInfoService.saveExamInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 确认
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateExamInfo", method = RequestMethod.POST)
    public Result saveOrUpdateExamInfo(@RequestBody List<ExamInfo> params){

        Result result = new Result();
        try{
            examInfoService.saveOrUpdateExamInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 计算得出分数
     * @param params
     * @return
     */
    @RequestMapping(value = "/countExam", method = RequestMethod.POST)
    public Result countExam(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = examInfoService.countExam(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }




}
