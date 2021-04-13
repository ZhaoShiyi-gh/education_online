package com.zhaosy.ossservice.serviece.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zhaosy.ossservice.serviece.OssService;
import com.zhaosy.ossservice.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file){
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        try{
            InputStream inputStream = file.getInputStream();

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid + file.getOriginalFilename();

            String datepath = new DateTime().toString("yyyy/MM/dd");
            fileName = datepath +"/" + fileName;
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://" + bucketName +"."+ endpoint+"/"+fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
