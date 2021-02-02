package com.huaxin.member.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "file")
public class FileUploadProperteis {

    /**
     * 静态资源对外暴露的访问路径
     */
    private String staticAccessPath;

    /**
     * 文件上传目录
     */
    private String uploadFolder;

    /**
     * 文件访问路径
     */
    private String urlPath;

    /**
     * 图片根目录
     */
    private String imagePath ;
    /**
     * 附件跟目录
     */
    private String attachmentPath ;
    /**
     * 合同根目录
     */
    private String contractPath ;
    /**
     * 图片对外地址
     */
    private String imageUrlPath ;
    /**
     * 附件对外地址
     */
    private String attachmentUrlPath ;
    /**
     * 合同对外地址
     */
    private String contractUrlPath ;

    public String getImageUrlPath() {
        return imageUrlPath;
    }

    public void setImageUrlPath(String imageUrlPath) {
        this.imageUrlPath = imageUrlPath;
    }

    public String getAttachmentUrlPath() {
        return attachmentUrlPath;
    }

    public void setAttachmentUrlPath(String attachmentUrlPath) {
        this.attachmentUrlPath = attachmentUrlPath;
    }

    public String getContractUrlPath() {
        return contractUrlPath;
    }

    public void setContractUrlPath(String contractUrlPath) {
        this.contractUrlPath = contractUrlPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getStaticAccessPath() {
        return staticAccessPath;
    }

    public void setStaticAccessPath(String staticAccessPath) {
        this.staticAccessPath = staticAccessPath;
    }

    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
