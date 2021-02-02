package com.huaxin.member.model;

import java.io.Serializable;
import java.util.Date;

public class HxRole implements Serializable {

    private static final long serialVersionUID = 6749900880183323588L;

    private Integer id;

    private Integer organizationId;

    private String roleName;

    private String remark;

    private Integer roleFlagId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRoleFlagId() {
        return roleFlagId;
    }

    public void setRoleFlagId(Integer roleFlagId) {
        this.roleFlagId = roleFlagId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
