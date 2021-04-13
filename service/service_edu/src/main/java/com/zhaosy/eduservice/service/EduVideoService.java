package com.zhaosy.eduservice.service;

import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaosy.eduservice.entity.vo.VideoFormVo;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
public interface EduVideoService extends IService<EduVideo> {

    String addVideo(VideoFormVo videoFormVo);

    Integer updateVideo(VideoFormVo videoFormVo);

    void deleteVideoById(String id);

    VideoFormVo getVideoById(String id);

    void removeVideoByCourseId(String id);

    Integer removeAlyVideoById(String id);

    void removeAllAlyVideoByChapterId(String chapterId);
}
