package com.zhaosy.vodservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile vidioFile);

    void removeVideoById(String id) throws Exception;

    void deletePatchVideos(List<String> videoIds) throws ClientException;
}
