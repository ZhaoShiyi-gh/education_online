package com.zhaosy.eduservice.front;

import com.zhaosy.eduservice.utils.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;

public class Fuzhu {

    @Autowired
    OrderClient orderClient;

    public  boolean isBuy(String courseId,String memberId){
        boolean is = orderClient.isBuy(courseId, memberId);
        return is;
    }

}
