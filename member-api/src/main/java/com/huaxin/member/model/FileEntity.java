package com.huaxin.member.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * 附件表
 * 
 * @author leeming
 * @email 100000@qq.com
 * @date 2020-03-22 18:05:14
 */
@Table(name = "hx_file")
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //文件路径
    @Column(name = "file_path")
    private String filePath;
	
	    //文件名
    @Column(name = "file_name")
    private String fileName;
	
	    //保存文件名
    @Column(name = "save_name")
    private String saveName;
	
	    //访问地址
    @Column(name = "url_path")
    private String urlPath;
	
	    //文件类型
    @Column(name = "file_type")
    private String fileType;
	
	    //是否删除
    @Column(name = "is_delete")
    private Integer isDelete;
	
	    //创建时间
    @Column(name = "create_time")
    private Date createTime;
	
	    //创建人userid
    @Column(name = "create_id")
    private Long createId;
	
	    //pdf文件路径
    @Column(name = "pdf_name")
    private String pdfName;

    //文件大小
	@Transient
	private String fileSize;

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：文件路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * 获取：文件路径
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * 设置：文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：文件名
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：保存文件名
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	/**
	 * 获取：保存文件名
	 */
	public String getSaveName() {
		return saveName;
	}
	/**
	 * 设置：访问地址
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	/**
	 * 获取：访问地址
	 */
	public String getUrlPath() {
		return urlPath;
	}
	/**
	 * 设置：文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * 获取：文件类型
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * 设置：是否删除
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getIsDelete() {
		return isDelete;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建人userid
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建人userid
	 */
	public Long getCreateId() {
		return createId;
	}
	/**
	 * 设置：pdf文件路径
	 */
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	/**
	 * 获取：pdf文件路径
	 */
	public String getPdfName() {
		return pdfName;
	}
}
