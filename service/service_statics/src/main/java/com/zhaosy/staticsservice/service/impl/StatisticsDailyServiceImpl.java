package com.zhaosy.staticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.staticsservice.entity.StatisticsDaily;
import com.zhaosy.staticsservice.mapper.StatisticsDailyMapper;
import com.zhaosy.staticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaosy.staticsservice.util.UcenterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void generateStatics(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        Integer registerNum = ucenterClient.getRegisterNum(day);

        Random r = new Random(1);
        Integer loginNum = r.nextInt(100);
        Integer videoViewNum = r.nextInt(100);
        Integer courseNum = r.nextInt(100);

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setDateCalculated(day);

        baseMapper.insert(statisticsDaily);
        return;
    }

    @Override
    public Map<String, Object> showCharts(String begin, String end, String type) {
         QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
         wrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("data", dataList);
        map.put("date", dateList);

        for(StatisticsDaily daily : list){
            dateList.add(daily.getDateCalculated());

            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return map;
    }
}
