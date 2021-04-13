package com.zhaosy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduVideo;
import com.zhaosy.eduservice.entity.vo.VideoFormVo;
import com.zhaosy.eduservice.mapper.EduVideoMapper;
import com.zhaosy.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaosy.eduservice.utils.VodClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public String addVideo(VideoFormVo videoFormVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoFormVo, video);
        baseMapper.insert(video);
        String id = video.getId();

        return id;
    }

    @Override
    public Integer updateVideo(VideoFormVo videoFormVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoFormVo, video);
        int i = baseMapper.updateById(video);
        return i;
    }

    @Override
    public void deleteVideoById(String id) {
        baseMapper.deleteById(id);
    }

    @Override
    public VideoFormVo getVideoById(String id) {
        VideoFormVo v = new VideoFormVo();
        EduVideo video = baseMapper.selectById(id);
        BeanUtils.copyProperties(video, v);
        return v;
    }

    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        baseMapper.delete(queryWrapper);
    }



    @Autowired
    private VodClient vodClient;

    @Override
    public Integer removeAlyVideoById(String id) {

        EduVideo video = baseMapper.selectById(id);
        String videoResourceId = video.getVideoSourceId();

        if(!StringUtils.isEmpty(videoResourceId)){
            vodClient.deleteVideo(videoResourceId);
        }
        Integer result = baseMapper.deleteById(id);
        return result;
    }

    @Override
    public void removeAllAlyVideoByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
//        wrapper.select("video_source_id");
        List<EduVideo> videos = baseMapper.selectList(wrapper);

        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : videos){
            if (!StringUtils.isEmpty(video.getVideoSourceId())){
                videoIds.add(video.getVideoSourceId());
            }
        }
        vodClient.deletePatchVideos(videoIds);
        baseMapper.delete(wrapper);
    }
}
