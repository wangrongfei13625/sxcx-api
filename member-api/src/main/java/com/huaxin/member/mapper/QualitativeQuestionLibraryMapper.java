package com.huaxin.member.mapper;

import java.util.List;
import java.util.Map;

public interface QualitativeQuestionLibraryMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOrUpdateLibrary(Map<String,Object> params);

    void deleteLibrary(Map<String,Object> params);
}
