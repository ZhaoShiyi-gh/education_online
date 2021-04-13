package com.zhaosy.eduservice.utils;

import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.utils.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/videovod/deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/videovod/deletePatchVideos")
    public R deletePatchVideos(@RequestParam("videoIds") List<String> videoIds);
}
