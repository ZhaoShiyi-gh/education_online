package com.zhaosy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.eduservice.entity.EduSubject;
import com.zhaosy.eduservice.entity.vo.ExcelSubjectData;
import com.zhaosy.eduservice.entity.vo.OneSubject;
import com.zhaosy.eduservice.entity.vo.TwoSubject;
import com.zhaosy.eduservice.listener.SubjectEventListener;
import com.zhaosy.eduservice.mapper.EduSubjectMapper;
import com.zhaosy.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void addClassification(MultipartFile file, EduSubjectService subjectService) {

        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, ExcelSubjectData.class, new SubjectEventListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> findAllSubjectClassification() {
        List<OneSubject> finalRes = new ArrayList<>();

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> eduOneSubjects = baseMapper.selectList(wrapper);

        for (EduSubject es:
             eduOneSubjects) {
            OneSubject os = new OneSubject();
            BeanUtils.copyProperties(es,os);
            finalRes.add(os);

            QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id", es.getId());
            List<EduSubject> eduTwoSubjects = baseMapper.selectList(wrapper1);

            List<TwoSubject> twoSub = new ArrayList<>();
            for(EduSubject tes: eduTwoSubjects){
                TwoSubject ts = new TwoSubject();
                BeanUtils.copyProperties(tes, ts);
                twoSub.add(ts);
            }
            os.setTwoSubjects(twoSub);
        }
        return finalRes;

    }
}
