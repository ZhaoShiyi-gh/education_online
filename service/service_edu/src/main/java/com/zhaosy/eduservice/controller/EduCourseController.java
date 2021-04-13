package com.zhaosy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduCourse;
import com.zhaosy.eduservice.entity.EduSubject;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.zhaosy.eduservice.entity.form.CourseInfoForm;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.entity.vo.CoursePublishVo;
import com.zhaosy.eduservice.entity.vo.CourseQuery;
import com.zhaosy.eduservice.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService courseService;
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm cis){

        String newId = courseService.addCourseInfo(cis);
        if(newId!=null){
            return R.ok().data("id", newId);
        }else {
            return R.error();
        }
    }

    @PutMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm cis){

        courseService.updateCourseInfo(cis);
        return R.ok();
    }

    @GetMapping("findAllChapterVideo/{courseId}")
    public R findAllChapterVideo(@PathVariable String courseId){
        List<ChapterVo> allChapterVideo = courseService.findAllChapterVideo(courseId);
        return R.ok().data("list", allChapterVideo);
    }

    @GetMapping("/findCourseInfo/{courseId}")
    public R findCourseInfo(@PathVariable String courseId){
        CourseInfoForm courseInfoForm = courseService.findCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfo", courseInfoForm);
    }

    @GetMapping("/CoursePublishVo/{courseId}")
    public R CoursePublishVo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishById(courseId);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }


    @GetMapping("getCoursePageList/{current}/{limit}")
    public R getPageList(@PathVariable Integer current, @PathVariable Integer limit){
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        courseService.page(pageCourse, null);
        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        Map<String, Object> map= new HashMap<>();
        map.put("total", total);
        map.put("items", list);
        return R.ok().data(map);
    }


    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduSubjectService subjectService;

    @PostMapping("/getCoursePageQueryList/{current}/{limit}")
    public R getPageList(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody CourseQuery query){
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (query == null){
            courseService.page(pageCourse, queryWrapper);
            long total = pageCourse.getTotal();
            List<EduCourse> list = pageCourse.getRecords();
            System.out.println(total);
            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("items", list);
        }

        String title = query.getTitle();
        String teacherId = query.getTeacherId();
        String subjectParentId = query.getSubjectParentId();
        String subjectId = query.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        courseService.page(pageCourse, queryWrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", list);

        return R.ok().data(map);
    }

    // 根据课程id删除
    @DeleteMapping("/deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        return R.ok();
    }



    //根据课程id查询课程信息
    @GetMapping("getDto/{courseId}")
    public com.zhaosy.commenUtils.CourseInfoForm getCourseInfoDto(@PathVariable String courseId) {
        CourseInfoForm courseInfoForm = courseService.findCourseInfoByCourseId(courseId);
        com.zhaosy.commenUtils.CourseInfoForm courseInfo = new com.zhaosy.commenUtils.CourseInfoForm();
        BeanUtils.copyProperties(courseInfoForm,courseInfo);
        return courseInfo;
    }
}

