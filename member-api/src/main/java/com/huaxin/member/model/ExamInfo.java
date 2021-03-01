package com.huaxin.member.model;

import java.io.Serializable;

public class ExamInfo implements Serializable {


    private static final long serialVersionUID = 6749900880183323588L;

    private Integer id;

    private Integer libraryId;

    private Integer libraryType;

    private String answer;

    private String confidenceValue;

    private Integer userId;

    private Integer manageId;

    private Integer questionnaireType;

    private String edition;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Integer getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(Integer libraryType) {
        this.libraryType = libraryType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getConfidenceValue() {
        return confidenceValue;
    }

    public void setConfidenceValue(String confidenceValue) {
        this.confidenceValue = confidenceValue;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getManageId() {
        return manageId;
    }

    public void setManageId(Integer manageId) {
        this.manageId = manageId;
    }

    public Integer getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(Integer questionnaireType) {
        this.questionnaireType = questionnaireType;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
