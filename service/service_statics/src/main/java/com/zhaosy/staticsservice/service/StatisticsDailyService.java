package com.zhaosy.staticsservice.service;

import com.zhaosy.staticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void generateStatics(String day);

    Map<String, Object> showCharts(String begin, String end, String type);
}
