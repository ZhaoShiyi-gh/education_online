package com.zhaosy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.eduservice.entity.EduChapter;
import com.zhaosy.eduservice.entity.EduVideo;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.entity.vo.VideoVo;
import com.zhaosy.eduservice.mapper.EduChapterMapper;
import com.zhaosy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaosy.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Override
    public List<EduChapter> findAllChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        return eduChapters;
    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }

}
