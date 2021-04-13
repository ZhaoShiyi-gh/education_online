package com.zhaosy.eduservice.utils.impl;

import com.zhaosy.eduservice.utils.OrderClient;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuy(String courseId, String memberId) {
//        System.out.println("查询是否购买课程失败.........." );
        return false;
    }
}
