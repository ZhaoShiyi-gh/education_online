package com.zhaosy.eduservice.utils.impl;

import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.utils.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deletePatchVideos(List<String> videoIds) {
        return R.error().message("删除多个视频出错");
    }
}
