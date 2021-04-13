package com.zhaosy.emaservice.controller;

import com.zhaosy.commenUtils.R;
import com.zhaosy.emaservice.service.EmailApiService;
import com.zhaosy.emaservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eduema/email")
//@CrossOrigin
public class EmailApiController {

    @Autowired
    private EmailApiService emailApiService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{emailArr}")
    public R sendEmail(@PathVariable String emailArr){
        String code = redisTemplate.opsForValue().get(emailArr);
        if(!StringUtils.isEmpty(code)) return R.ok();

        code = RandomUtil.getFourBitRandom();
        emailApiService.sendEmail(code, emailArr);
        redisTemplate.opsForValue().set(emailArr, code, 5, TimeUnit.MINUTES);
        return R.ok();
    }
}
