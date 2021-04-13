package com.zhaosy.ossservice.serviece;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zhaosy.ossservice.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.UUID;

@Service
public interface OssService {

   String uploadFileAvatar(MultipartFile file);
}
