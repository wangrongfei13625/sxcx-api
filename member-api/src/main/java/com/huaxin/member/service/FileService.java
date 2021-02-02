package com.huaxin.member.service;

import com.huaxin.member.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileEntity uploadImage(MultipartFile file, String userId);

    FileEntity uploadDoc(MultipartFile file,String maxSizes);

    FileEntity uploadVideo(MultipartFile file);

}
