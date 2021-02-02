package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface QualitativeQuestionLibraryService {

    PageInfo findList(Map<String,Object> params);

    void saveOrUpdateLibrary(Map<String,Object> params);

    void deleteLibrary(Map<String,Object> params);

    void copyQualitative(Map<String,Object> params);

}