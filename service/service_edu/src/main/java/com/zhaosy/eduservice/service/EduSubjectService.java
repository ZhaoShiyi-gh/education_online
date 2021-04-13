package com.zhaosy.eduservice.service;

import com.zhaosy.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaosy.eduservice.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addClassification(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> findAllSubjectClassification();
}
