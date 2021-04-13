package com.zhaosy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getPagesTeacher(Page<EduTeacher> pageParams);
}
