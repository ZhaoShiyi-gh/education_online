package com.zhaosy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.eduservice.entity.*;
import com.zhaosy.eduservice.entity.form.CourseInfoForm;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.entity.vo.CoursePublishVo;
import com.zhaosy.eduservice.entity.vo.VideoVo;
import com.zhaosy.eduservice.entity.vo.front.CourseWebVo;
import com.zhaosy.eduservice.entity.vo.front.FrontCourse;
import com.zhaosy.eduservice.mapper.EduCourseMapper;
import com.zhaosy.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService courseDescriptionService;

    @Autowired
    EduSubjectService subjectService;
    @Override
    public String addCourseInfo(CourseInfoForm info) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(info, eduCourse);


        baseMapper.insert(eduCourse);



        String newId = eduCourse.getId();

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(newId);
        courseDescription.setDescription(info.getDescription());
        courseDescriptionService.save(courseDescription);
        return newId;
    }



    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduVideoService videoService;


    @Override
    public List<ChapterVo> findAllChapterVideo(String courseId) {

        List<ChapterVo> finalRes = new ArrayList<>();
        List<EduChapter> list = chapterService.findAllChapterByCourseId(courseId);

        for (EduChapter e:
                list) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(e, chapterVo);
            finalRes.add(chapterVo);

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("chapter_id", e.getId());
            List<EduVideo> list1 = videoService.list(queryWrapper);

            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo:
                    list1) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                videoVoList.add(videoVo);
            }
            chapterVo.setVideoVo(videoVoList);
        }
        return finalRes;
    }

    @Override
    public CourseInfoForm findCourseInfoByCourseId(String courseId) {
        CourseInfoForm cif = new CourseInfoForm();
        EduCourse course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(course, cif);

        EduCourseDescription coursDescription = courseDescriptionService.getById(courseId);
        cif.setDescription(coursDescription.getDescription());
        return cif;
    }

    @Override
    public void updateCourseInfo(CourseInfoForm cis) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(cis, eduCourse);

        baseMapper.updateById(eduCourse);

        EduCourseDescription coursDescription = new EduCourseDescription();
        BeanUtils.copyProperties(cis, coursDescription);
        courseDescriptionService.updateById(coursDescription);

    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        return baseMapper.getCoursePublishById(id);
    }

    @Override
    public void deleteCourse(String id) {
        videoService.removeVideoByCourseId(id);

        chapterService.removeChapterByCourseId(id);

        courseDescriptionService.removeById(id);

        baseMapper.deleteById(id);
    }

    @Override
    public List<EduCourse> findCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> courses = baseMapper.selectList(wrapper);
        return courses;
    }

    @Override
    public Map<String, Object> getPagesTeacher(Page<EduCourse> pageParams, FrontCourse courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            wrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParams, wrapper);

        List<EduCourse> courses = pageParams.getRecords();
        long current = pageParams.getCurrent();
        long pages = pageParams.getPages();
        long size = pageParams.getSize();
        long total = pageParams.getTotal();
        boolean hasNext = pageParams.hasNext();
        boolean hasPrevious = pageParams.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", courses);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getCourseWebInfoByCourseId(String courseId) {
        CourseWebVo courseWebVo = baseMapper.selectInfoWebById(courseId);

        return courseWebVo;
    }

}
