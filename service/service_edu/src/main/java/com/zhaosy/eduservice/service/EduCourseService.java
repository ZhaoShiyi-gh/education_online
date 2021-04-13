package com.zhaosy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.eduservice.entity.EduChapter;
import com.zhaosy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.zhaosy.eduservice.entity.form.CourseInfoForm;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.entity.vo.CoursePublishVo;
import com.zhaosy.eduservice.entity.vo.front.CourseWebVo;
import com.zhaosy.eduservice.entity.vo.front.FrontCourse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm cif);

    List<ChapterVo> findAllChapterVideo(String courseId);

    CourseInfoForm findCourseInfoByCourseId(String courseId);

    void updateCourseInfo(CourseInfoForm cis);

    CoursePublishVo getCoursePublishById(String id);

    void deleteCourse(String id);

    List<EduCourse> findCourseByTeacherId(String teacherId);

    Map<String, Object> getPagesTeacher(Page<EduCourse> pageParams, FrontCourse courseQuery);

    CourseWebVo getCourseWebInfoByCourseId(String courseId);

}
