package com.zhaosy.cmtservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.cmtservice.entity.EduComment;
import com.zhaosy.cmtservice.service.EduCommentService;
import com.zhaosy.commenUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/cmtservice/comment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @GetMapping("/getPageComment/{courseId}/{current}/{limit}")
    public R getPageComment(@PathVariable long current, @PathVariable long limit, @PathVariable String courseId){
        Page<EduComment> pageParams = new Page<>(current, limit);

        Map<String, Object> map = commentService.getPageList(pageParams, courseId);

        return R.ok().data(map);
    }

    @PostMapping("/addComment/")
    public R addComment(@RequestBody EduComment comment){

        String commentId = commentService.addComment(comment);

        return R.ok().data("commentId", commentId);
    }

}

