package com.huaxin.member.controller;

/**
 *
 * @author wangrongf
 */


import com.huaxin.member.model.FileEntity;
import com.huaxin.member.service.FileService;
import com.huaxin.member.util.base.Result;
import com.huaxin.member.util.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("file")
public class FileController  {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FileService fileService ;

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Result uploadImage(@RequestBody MultipartFile file){

        Result result = new Result();
        try{
            FileEntity entity = fileService.uploadImage(file, String.valueOf(1));
            result.setData(entity);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    public Result uploadImages(@RequestBody List<MultipartFile> files){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = new LinkedList<>();
            for(MultipartFile file: files){
                FileEntity entity = fileService.uploadImage(file, String.valueOf(1));
                Map<String,Object> map = new HashMap<>();
                map.put("url",entity.getUrlPath());
                list.add(map);
            }
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(@RequestBody MultipartFile file, @RequestBody String maxSize){

        Result result = new Result();
        try{
            FileEntity fileEntity = fileService.uploadDoc(file, maxSize);
            result.setData(fileEntity);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public Result uploadVideo(@RequestBody MultipartFile file){

        Result result = new Result();
        try{
            FileEntity fileEntity = fileService.uploadVideo(file);
            result.setData(fileEntity);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
