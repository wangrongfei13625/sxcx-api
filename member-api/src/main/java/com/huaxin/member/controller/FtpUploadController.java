package com.huaxin.member.controller;

import com.huaxin.common.FtpUtil;
import com.huaxin.member.config.FtpConfigurationProperties;
import com.huaxin.member.util.IDUtils;
import com.huaxin.member.util.base.Result;
import com.huaxin.member.util.base.ResultCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ftpUpload")
public class FtpUploadController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FtpConfigurationProperties ftpConfigurationProperties;

    @ApiOperation(notes = "文件上传", value = "文件上传")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public Result upload(@RequestParam("file") @ApiParam(value = "上传的文件") MultipartFile file, HttpServletRequest request) {
        Result result = new Result();;
        Map<String, Object> map = null;
        try {
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            String oldName = file.getOriginalFilename();
            //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
            String newName = IDUtils.genImageName(false);
            String fileName = oldName.substring(0, oldName.lastIndexOf("."));
            String extendName = oldName.substring(oldName.lastIndexOf("."));
            newName = fileName + newName + extendName;
            //1.3生成文件在服务器端存储的子目录
            String filePath = ftpConfigurationProperties.getUploadDir();

            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = file.getInputStream();

            //3.2调用FtpUtil工具类进行上传
            String basePath = ftpConfigurationProperties.getBaseUrl();
            boolean results = FtpUtil.uploadFile(ftpConfigurationProperties.getHost(), ftpConfigurationProperties.getPort(), ftpConfigurationProperties.getUsername(), ftpConfigurationProperties.getPassword(), basePath, filePath, newName, input);
            if (results) {
                map = new HashMap<>();
                map.put("path", filePath + "/" + newName);
                map.put("fullpath", ftpConfigurationProperties.getNginxUrl() + filePath + "/" + newName);
                map.put("fileName", oldName);
            }
            result.setData(map);
        } catch (Exception e) {
            logger.error("上传失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
