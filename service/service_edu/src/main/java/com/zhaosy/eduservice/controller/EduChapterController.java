package com.zhaosy.eduservice.controller;


import com.zhaosy.commenUtils.R;
import com.zhaosy.eduservice.entity.EduChapter;
import com.zhaosy.eduservice.entity.vo.ChapterVo;
import com.zhaosy.eduservice.service.EduChapterService;
import com.zhaosy.eduservice.service.EduVideoService;
import com.zhaosy.eduservice.utils.VodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService chapterService;



    @Autowired
    private EduVideoService videoService;

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean save = chapterService.save(chapter);
        return R.ok().data("success", save);
    }

    @PutMapping("/update")
    public R update(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }


    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id){
        videoService.removeAllAlyVideoByChapterId(id);

        boolean b = chapterService.removeById(id);
        return R.ok();
    }

    @GetMapping("/getChapterById/{id}")
    public R getChapterById(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }
}

