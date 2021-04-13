package com.zhaosy.emaservice.service.impl;

import com.zhaosy.emaservice.service.EmailApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmaiApiServiceImpl implements EmailApiService {

    @Autowired
    JavaMailSenderImpl mailSender;
    @Override
    public void sendEmail(String code, String emailArr) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("注册验证码");
        String string = "[思培教育]   你本次申请的注册验证码为：";
        message.setText(string + code + "【5分钟内有效】");
        message.setFrom("1252725323@qq.com");
        message.setTo(emailArr);
        mailSender.send(message);
    }

}
