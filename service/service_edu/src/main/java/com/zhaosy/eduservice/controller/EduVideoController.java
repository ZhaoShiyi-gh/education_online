package com.zhaosy.eduservice.controller;


import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduVideo;
import com.zhaosy.eduservice.entity.vo.VideoFormVo;
import com.zhaosy.eduservice.service.EduVideoService;
import com.zhaosy.eduservice.utils.VodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @GetMapping("/getVideoById/{id}")
    public R getVideoById (@PathVariable String id){
        VideoFormVo videoFormVo = videoService.getVideoById(id);
        return R.ok().data("videoFormVo", videoFormVo);
    }


    @PostMapping("/addVideo")
    public R addVideo(@RequestBody VideoFormVo videoFormVo){
        String id = videoService.addVideo(videoFormVo);
        return R.ok().data("VideoId", id);
    }

    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody VideoFormVo videoFormVo){
        Integer result = videoService.updateVideo(videoFormVo);
        return result>0 ? R.ok() : R.error();
    }

    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.deleteVideoById(id);
        return R.ok();
    }

    @DeleteMapping("/deleteVideoById/{id}")
    public R deleteVideoById(@PathVariable String id){
        Integer result = videoService.removeAlyVideoById(id);
        return R.ok().data("result", result);
    }
}

