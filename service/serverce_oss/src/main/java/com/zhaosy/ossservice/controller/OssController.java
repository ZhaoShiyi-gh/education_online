package com.zhaosy.ossservice.controller;

import com.zhaosy.commenUtils.R;
import com.zhaosy.ossservice.serviece.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadOssFile")
    public R uploadOssFile(MultipartFile file){
       String url = ossService.uploadFileAvatar(file);
       return R.ok().data("url", url);
    }
}
