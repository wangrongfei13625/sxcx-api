package com.huaxin.member.model;

import java.io.Serializable;

public class RationQuestionLibrary implements Serializable {

    private static final long serialVersionUID = 6749900880183323588L;


    private Integer id;

    private String title;

    private String manageId;

    private String rationType;

    private String rationRemark;

    private String rationData;

    private String rationCode;

    private String edition;

    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

    public String getRationType() {
        return rationType;
    }

    public void setRationType(String rationType) {
        this.rationType = rationType;
    }

    public String getRationRemark() {
        return rationRemark;
    }

    public void setRationRemark(String rationRemark) {
        this.rationRemark = rationRemark;
    }

    public String getRationData() {
        return rationData;
    }

    public void setRationData(String rationData) {
        this.rationData = rationData;
    }

    public String getRationCode() {
        return rationCode;
    }

    public void setRationCode(String rationCode) {
        this.rationCode = rationCode;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
