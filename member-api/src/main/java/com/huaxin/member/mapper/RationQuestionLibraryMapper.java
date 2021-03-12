package com.huaxin.member.mapper;

import com.huaxin.member.model.RationQuestionLibrary;

import java.util.List;
import java.util.Map;

public interface RationQuestionLibraryMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOrUpdateLibrary(Map<String,Object> params);

    void deleteLibrary(Map<String,Object> params);

    void deleteOfId(Map<String,Object> params);


    void saveRation(RationQuestionLibrary ration);

}
