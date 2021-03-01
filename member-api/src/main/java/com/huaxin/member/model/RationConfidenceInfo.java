package com.huaxin.member.model;

import java.io.Serializable;

public class RationConfidenceInfo implements Serializable {

    private static final long serialVersionUID = 6749900880183323588L;

    private Integer id;

    private String title;

    private String fraction;

    private Integer rationId;

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

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public Integer getRationId() {
        return rationId;
    }

    public void setRationId(Integer rationId) {
        this.rationId = rationId;
    }
}
