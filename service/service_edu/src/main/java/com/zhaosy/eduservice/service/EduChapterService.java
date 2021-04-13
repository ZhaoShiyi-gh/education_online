package com.zhaosy.eduservice.service;

import com.zhaosy.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
public interface EduChapterService extends IService<EduChapter> {

    List<EduChapter> findAllChapterByCourseId(String courseId);

    void removeChapterByCourseId(String id);

}
