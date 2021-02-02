package com.huaxin.member.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.huaxin.member.model.FileEntity;
import com.huaxin.member.service.FileService;
import com.huaxin.member.util.FileUploadProperteis;
import com.huaxin.member.util.RRException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FileUploadProperteis fileUploadProperteis;

    @Value("${uploadFile.imageType}")
    private String imageType;

    @Value("${uploadFile.docType}")
    private String docType;



    @Value("${uploadFile.fileType}")
    private String fileType;


    @Value("${uploadFile.maxFileSize}")
    private int maxFileSize;

    @Override
    public FileEntity uploadImage(MultipartFile file, String userId) {
        String fileName = file.getOriginalFilename();
        String uploadFileType = FilenameUtils.getExtension(fileName).toLowerCase();
        // 1.判断文件类型是否在合法类型中
        String[] fileTypeArr = imageType.split("\\|");
        List<String> fileTypeList = Arrays.asList(fileTypeArr);
        if (!fileTypeList.contains(uploadFileType)) {
            throw new RRException("文件格式不支持");
        }
        FileEntity newFile = null;
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "." + uploadFileType;
        String filePath = fileUploadProperteis.getAttachmentPath()+ File.separator+ DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)+File.separator+saveName;
        InputStream is = null;
        File toFile = new File(fileUploadProperteis.getAttachmentPath()+File.separator+DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN), saveName);
        try {
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
            // FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, saveName));
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, toFile);
            // 如果是图片类型jpg,png,gif,需要对大图进行压缩
//            if ("jpg".equals(uploadFileType) || "jpeg".equals(uploadFileType)
//                    || "png".equals(uploadFileType) || "gif".equals(uploadFileType)) {
//                // 文件大小
//                long size = file.getSize();
//                int[] widthHeight = this.getImgWidthHeight(file);
//                float scale = 1f;
//                // 图片大于500K
//                if (size > 500 * 1024) {
//                    // 图片宽度大于1080px，缩小宽度e
//                    if (widthHeight[0] > 1080) {
//                        BigDecimal scaleWidth = new BigDecimal((float) 1000 / (float) widthHeight[0]).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        scale = scaleWidth.floatValue();
//                        Thumbnails.of(is).scale(scale).outputQuality(0.8f).toFile(toFile);
//                    } else {
//                        Thumbnails.of(is).scale(1f).outputQuality(0.6f).toFile(toFile);
//                    }
//                } else {// 图片不大于500K
//                    // 图片宽度大于1080px，缩小宽度
//                    if (widthHeight[0] > 1080) {
//                        BigDecimal scaleWidth = new BigDecimal((float) 1000 / (float) widthHeight[0]).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        scale = scaleWidth.floatValue();
//                        Thumbnails.of(is).scale(scale).outputQuality(1f).toFile(toFile);
//                    } else {
//                        Thumbnails.of(is).scale(1f).outputQuality(1f).toFile(toFile);
//                    }
//                }
//            } else {
//                FileUtils.copyInputStreamToFile(is, toFile);
//            }
            newFile = this.saveEntityInfo(fileName, saveName, uploadFileType, filePath, userId,null, FileOpration.IMAGE.getValue());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("文件上传失败");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 去除前端不需要的东西
        if (null != newFile) {
            newFile.setFilePath(null);
            newFile.setCreateId(null);
            newFile.setCreateTime(null);
        }
        return newFile;
    }

    private FileEntity saveEntityInfo(String fileName, String saveName, String uploadFileType, String filePath, String userId,String pdfPath,String fileOpration) {
        FileEntity forAddFile = new FileEntity();
        forAddFile.setFilePath(filePath);
        forAddFile.setFileName(fileName);
        forAddFile.setSaveName(saveName);
        String urlPath = filePath.replace(fileUploadProperteis.getAttachmentPath(),fileUploadProperteis.getAttachmentUrlPath());
        forAddFile.setUrlPath(urlPath);
        forAddFile.setFileType(uploadFileType);
        forAddFile.setCreateId(new BigDecimal(userId).longValue());
        forAddFile.setCreateTime(new Date());
        if(!StringUtils.isEmpty(pdfPath)){
            forAddFile.setPdfName(pdfPath);
        }
        return forAddFile;
    }

    @Override
    public FileEntity uploadVideo(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String uploadFileType = FilenameUtils.getExtension(fileName).toLowerCase();
        // 1.判断文件类型是否在合法类型中
        String[] fileTypeArr = fileType.split("\\|");
        List<String> fileTypeList = Arrays.asList(fileTypeArr);
        if (!fileTypeList.contains(uploadFileType)) {
            throw new RRException("文件格式不支持");
        }
        FileEntity newFile = null;
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "." + uploadFileType;
        InputStream is = null;
        String filePath = fileUploadProperteis.getAttachmentPath()+File.separator+DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)+File.separator+saveName;
        File toFile = FileUtil.touch(new File(filePath));
        try {
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, toFile);
            String   urlPath = filePath.replace(fileUploadProperteis.getAttachmentPath(),fileUploadProperteis.getAttachmentUrlPath());
            newFile=new FileEntity() ;
            newFile.setFileName(fileName);
            newFile.setUrlPath(urlPath);
            //newFile.setFileSize(String.valueOf(new BigDecimal(size).divide(new BigDecimal(unit_mb),2, RoundingMode.HALF_UP)));
        }catch (Exception e) {
            throw new RRException("文档格式错误");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 去除前端不需要的东西
        if (null != newFile) {
            newFile.setFilePath(null);
            newFile.setCreateId(null);
            newFile.setCreateTime(null);
        }
        return newFile;


    }



    /**
     * 上传文件
     * @param file
     * @param maxSizes MB
     * @return
     */
    @Override
    public FileEntity uploadDoc(MultipartFile file,String maxSizes){
        final long size = file.getSize();
        final long unit_mb = 1024*1024 ;
        int maxSize = 0 ;
        if(StringUtils.isEmpty(maxSizes)){
            throw new RRException("文件最大值没有指定");
        }
        maxSize = Integer.parseInt(maxSizes) ;
        if(maxSize > 0  ){
            if(maxSize > maxFileSize){
                throw new RRException("文件最大只支持："+maxFileSize+"MB");
            }
            if(size>maxSize*1024*1024){
                throw new RRException("文件超过最大："+maxSize+"MB");
            }
        }
        String fileName = file.getOriginalFilename();
        String uploadFileType = FilenameUtils.getExtension(fileName).toLowerCase();
        // 1.判断文件类型是否在合法类型中
        String[] fileTypeArr = fileType.split("\\|");
        List<String> fileTypeList = Arrays.asList(fileTypeArr);
        if (!fileTypeList.contains(uploadFileType)) {
            throw new RRException("文件格式不支持");
        }
        FileEntity newFile = null;
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "." + uploadFileType;
        InputStream is = null;
        String filePath = fileUploadProperteis.getAttachmentPath()+File.separator+DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)+File.separator+saveName;
        File toFile = FileUtil.touch(new File(filePath));
        try {
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, toFile);
            String   urlPath = filePath.replace(fileUploadProperteis.getAttachmentPath(),fileUploadProperteis.getAttachmentUrlPath());
            newFile=new FileEntity() ;
            newFile.setFileName(fileName);
            newFile.setUrlPath(urlPath);
            newFile.setFileSize(String.valueOf(new BigDecimal(size).divide(new BigDecimal(unit_mb),2, RoundingMode.HALF_UP)));
        }catch (Exception e) {
            throw new RRException("文档格式错误");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 去除前端不需要的东西
        if (null != newFile) {
            newFile.setFilePath(null);
            newFile.setCreateId(null);
            newFile.setCreateTime(null);
        }
        return newFile;
    }


    /** 文件操作 key */
    public enum FileOpration{
        IMAGE("1"),
        ATTACHMENT("2");
        private String value ;
        FileOpration(String value){this.value = value ;}
        public String getValue(){return value;}
    }


    /**
     * 获取图片宽度和高度
     *
     * @param file 文件
     * @return 返回图片的宽度
     */
    private int[] getImgWidthHeight(MultipartFile file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            // 获得文件输入流
            is = file.getInputStream();
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图片宽
            result[1] = src.getHeight(null);// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }


}
