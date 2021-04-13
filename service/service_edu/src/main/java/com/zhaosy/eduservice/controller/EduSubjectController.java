package com.zhaosy.eduservice.controller;


import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.vo.OneSubject;
import com.zhaosy.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    @PostMapping("/addClassification")
    public R addClassification(MultipartFile file){
        subjectService.addClassification(file, subjectService);
        return R.ok();
    }

    @GetMapping("findAllProject")
    public R findAllProject(){
        List<OneSubject> list = subjectService.findAllSubjectClassification();
        return R.ok().data("subjects", list);
    }
}

