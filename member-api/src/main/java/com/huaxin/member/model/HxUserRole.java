package com.huaxin.member.model;

import java.io.Serializable;
import java.util.Date;

public class HxUserRole implements Serializable {

    private static final long serialVersionUID = 6749900880183323588L;

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Integer organizationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
}
