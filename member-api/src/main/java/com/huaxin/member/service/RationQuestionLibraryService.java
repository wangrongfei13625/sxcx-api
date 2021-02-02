package com.huaxin.member.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface RationQuestionLibraryService {

    PageInfo findList(Map<String,Object> params);

    void saveOrUpdateLibrary(Map<String,Object> params);

    void deleteLibrary(Map<String,Object> params);

    void copyRation(Map<String,Object> params);

}
