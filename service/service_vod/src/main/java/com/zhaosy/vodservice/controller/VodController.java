package com.zhaosy.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zhaosy.commenUtils.R;
import com.zhaosy.vodservice.service.VodService;
import com.zhaosy.vodservice.utils.ConstantPropertiesUtils;
import com.zhaosy.vodservice.utils.Initializing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/videovod")
//@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;



    @PostMapping("/uploadVideo")
    public R uploadVideo(@RequestParam("file") MultipartFile vidioFile){
        String videoId = vodService.uploadVideo(vidioFile);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        try {
            vodService.removeVideoById(id);
            return R.ok().message("删除视频成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("删除视频失败");
        }

    }

    @DeleteMapping("/deletePatchVideos")
    public R deletePatchVideos(@RequestParam List<String> videoIds) {
        try {
            vodService.deletePatchVideos(videoIds);
            return R.ok().message("删除视频成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("删除视频失败");
        }

    }


    @GetMapping("/getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    Initializing.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(videoId);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            return R.error().message("获取播放凭证失败");
        }
    }
}
