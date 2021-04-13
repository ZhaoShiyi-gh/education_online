package com.zhaosy.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.eduservice.entity.EduSubject;
import com.zhaosy.eduservice.entity.vo.ExcelSubjectData;
import com.zhaosy.eduservice.service.EduSubjectService;

public class SubjectEventListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService  eduSubjectService;

    public SubjectEventListener(){}
    public SubjectEventListener(EduSubjectService subjectService) {
        this.eduSubjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {

        if(subjectData == null){
            try {
                throw new Exception("文件数据为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }
    }

    public EduSubject existOneSubject(EduSubjectService eduSubjectService, String name){
        QueryWrapper<EduSubject>  wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    public EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String pid){
        QueryWrapper<EduSubject>  wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
