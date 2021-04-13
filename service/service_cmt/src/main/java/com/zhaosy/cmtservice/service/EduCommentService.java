package com.zhaosy.cmtservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.cmtservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-07
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getPageList(Page<EduComment> pageParams, String courseId);

    String addComment(EduComment comment);
}
