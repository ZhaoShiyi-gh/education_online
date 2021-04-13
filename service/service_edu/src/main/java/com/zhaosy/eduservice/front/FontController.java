package com.zhaosy.eduservice.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.commenUtils.JwtUtils;
import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduCourse;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.entity.vo.front.CourseWebVo;
import com.zhaosy.eduservice.entity.vo.front.FrontCourse;
import com.zhaosy.eduservice.service.EduCourseService;
import com.zhaosy.eduservice.service.EduTeacherService;
import com.zhaosy.eduservice.utils.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front")
//@CrossOrigin
public class FontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("/getCourseTeacher")
    public R getCourseTeacher(){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> items = teacherService.list(wrapper);

        QueryWrapper<EduCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 8");
        List<EduCourse> items1 = courseService.list(wrapper1);
        return R.ok().data("teachers", items).data("courses", items1);
    }

    @GetMapping("/getPagesTeacher/{current}/{limit}")
    public R getPagesTeacher(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduTeacher> pageParams = new Page<>(current, limit);

        Map<String, Object> map = teacherService.getPagesTeacher(pageParams);

        return R.ok().data(map);
    }


    @GetMapping("/getTeacherInfo/{teacherId}")
    public R getTeacherDetail(@PathVariable String teacherId){

        EduTeacher teacher = teacherService.getById(teacherId);

        List<EduCourse> courses = courseService.findCourseByTeacherId(teacherId);

        return R.ok().data("courses", courses).data("teacher", teacher);
    }



    @PostMapping("/getPagesCourse/{current}/{limit}")
    public R getPagesCourse(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody FrontCourse courseQuery) {
        Page<EduCourse> pageParams = new Page<>(current, limit);

        Map<String, Object> map = courseService.getPagesTeacher(pageParams, courseQuery);

        return R.ok().data(map);
    }

    @Autowired
    OrderClient orderClient;

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseDetail(@PathVariable String courseId, HttpServletRequest request){

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes);

        CourseWebVo courseWebVo = courseService.getCourseWebInfoByCourseId(courseId);

        List<ChapterVo> chapter = courseService.findAllChapterVideo(courseId);

        String memberId = JwtUtils.getMemberIdByJwtToken(request);


        boolean is = orderClient.isBuy(courseId, memberId);
        return R.ok().data("courseInfo", courseWebVo).data("chapters", chapter).data("isBuy", is);
    }
}
