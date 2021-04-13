package com.zhaosy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.eduservice.entity.EduTeacher;
import com.zhaosy.eduservice.mapper.EduTeacherMapper;
import com.zhaosy.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getPagesTeacher(Page<EduTeacher> pageParams) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(pageParams, wrapper);

        List<EduTeacher> teachers = pageParams.getRecords();
        long current = pageParams.getCurrent();
        long pages = pageParams.getPages();
        long size = pageParams.getSize();
        long total = pageParams.getTotal();
        boolean hasNext = pageParams.hasNext();
        boolean hasPrevious = pageParams.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", teachers);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
