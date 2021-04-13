package com.zhaosy.orderservice.client;

import com.zhaosy.commenUtils.UCenterInfoForm;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UCenterClient {


    @GetMapping("/eduucenter/member/getMember/{id}")
    public UCenterInfoForm getInfo(@PathVariable("id") String id);
}
