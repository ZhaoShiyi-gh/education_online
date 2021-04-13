package com.zhaosy.eduservice.mapper;

import com.zhaosy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaosy.eduservice.entity.vo.CoursePublishVo;
import com.zhaosy.eduservice.entity.vo.front.CourseWebVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Component
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo selectInfoWebById(String courseId);
}
