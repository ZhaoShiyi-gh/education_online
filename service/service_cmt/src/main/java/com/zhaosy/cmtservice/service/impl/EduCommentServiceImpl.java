package com.zhaosy.cmtservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.cmtservice.entity.EduComment;
import com.zhaosy.cmtservice.mapper.EduCommentMapper;
import com.zhaosy.cmtservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-07
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> getPageList(Page<EduComment> pageParams, String courseId) {

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id", courseId);
        baseMapper.selectPage(pageParams, wrapper);
        List<EduComment> comments = pageParams.getRecords();
        long current = pageParams.getCurrent();
        long pages = pageParams.getPages();
        long size = pageParams.getSize();
        long total = pageParams.getTotal();
        boolean hasNext = pageParams.hasNext();
        boolean hasPrevious = pageParams.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", comments);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public String addComment(EduComment comment) {
        EduComment eduComment = new EduComment();
        BeanUtils.copyProperties(comment, eduComment);

        baseMapper.insert(eduComment);
        String commentId = eduComment.getId();

        return commentId;
    }
}
