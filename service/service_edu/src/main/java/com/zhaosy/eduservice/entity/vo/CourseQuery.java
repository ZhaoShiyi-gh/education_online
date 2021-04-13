package com.zhaosy.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable {

    private String teacherId;

    private String title;

    private String subjectId;

    private String subjectParentId;

}
