package com.zhaosy.staticsservice.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/eduucenter/member/getRegisterNum/{day}")
    public Integer getRegisterNum(@PathVariable("day") String day);
}
