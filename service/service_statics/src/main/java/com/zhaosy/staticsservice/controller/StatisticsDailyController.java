package com.zhaosy.staticsservice.controller;


import com.zhaosy.commenUtils.R;
import com.zhaosy.staticsservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/staticsservice/statistics")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService statisticsService;

    @GetMapping("/generateStatics/{day}")
    public R generateStatics(@PathVariable String day){

        statisticsService.generateStatics(day);

        return R.ok();
    }


    @GetMapping("/showCharts/{begin}/{end}/{type}")
    public R showCharts(@PathVariable String begin, @PathVariable String end, @PathVariable String type){
        Map<String, Object> map = statisticsService.showCharts(begin, end, type);
        return R.ok().data(map);
    }

}

