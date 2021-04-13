package com.zhaosy.emaservice.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailApiService {
    void sendEmail(String code, String emailArr);
}
