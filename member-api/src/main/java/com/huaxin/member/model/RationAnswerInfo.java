package com.huaxin.member.model;

import java.io.Serializable;

public class RationAnswerInfo implements Serializable {

    private static final long serialVersionUID = 6749900880183323588L;

    private Integer id;

    private String answer;

    private String remark;

    private Integer rationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRationId() {
        return rationId;
    }

    public void setRationId(Integer rationId) {
        this.rationId = rationId;
    }
}
