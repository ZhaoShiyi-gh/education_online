package com.zhaosy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.vo.TeacherQuery;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.zhaosy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {
    @Autowired
    public EduTeacherService teacherService;

    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("/{id}")
    public R removeTeacher(@PathVariable String id){
        return teacherService.removeById(id)? R.ok() : R.error();
    }


    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Integer current, @PathVariable Integer limit){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> list = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageItems", list);
        return R.ok().data(map);
    }


    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create", end);
        }

        teacherService.page(pageTeacher, queryWrapper);
        long total = pageTeacher.getTotal();

        List<EduTeacher> list = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageItems", list);
        return R.ok().data(map);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        return teacherService.save(teacher) ? R.ok() : R.error();
    }

    @GetMapping("/getTeacher/{id}")
    public R findTeacherById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        return teacherService.updateById(teacher) ? R.ok() : R.error();
    }
}

