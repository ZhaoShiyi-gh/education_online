package com.zhaosy.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.zhaosy.vodservice.service.VodService;
import com.zhaosy.vodservice.utils.ConstantPropertiesUtils;
import com.zhaosy.vodservice.utils.Initializing;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile vidioFile) {
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String fileName = vidioFile.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        try {
            InputStream inputStream = vidioFile.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId =  response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                System.out.println(errorMessage);
                videoId =  response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeVideoById(String id) throws Exception{
        DefaultAcsClient client = Initializing.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        DeleteVideoResponse response = client.getAcsResponse(request);

    }

    @Override
    public void deletePatchVideos(List<String> videoIds) throws ClientException {
        DefaultAcsClient client = Initializing.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        String ids = StringUtils.join(videoIds.toArray(), ",");
        request.setVideoIds(ids);
        DeleteVideoResponse response = client.getAcsResponse(request);

    }
}
