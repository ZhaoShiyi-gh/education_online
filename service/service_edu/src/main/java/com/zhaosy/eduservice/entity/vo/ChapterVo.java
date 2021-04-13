package com.zhaosy.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> videoVo;

}
