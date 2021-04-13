package com.zhaosy.orderservice.client;

import com.zhaosy.commenUtils.CourseInfoForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
@Component
public interface EduClient {

    @GetMapping("/eduservice/course/getDto/{courseId}")
    public CourseInfoForm getCourseInfoDto(@PathVariable("courseId") String courseId);
}
