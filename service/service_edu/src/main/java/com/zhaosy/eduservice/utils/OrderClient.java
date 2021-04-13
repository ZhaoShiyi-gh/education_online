package com.zhaosy.eduservice.utils;

import com.zhaosy.eduservice.utils.impl.OrderClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
@Component
public interface OrderClient {

    @GetMapping("/orderservice/order/isBuy/{courseId}/{memberId}")
    public boolean isBuy(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
