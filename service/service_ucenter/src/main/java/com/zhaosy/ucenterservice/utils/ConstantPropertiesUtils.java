package com.zhaosy.ucenterservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("wxed9954c01bb89b47")
    private String appid;

    @Value("a7482517235173ddb4083788de60b90e")
    private String appsecret;

    @Value("http://guli.shop/api/ucenter/wx/callback")
    private String redirecturl;

    //定义公开静态常量
    public static String APPID;
    public static String APPSECRET;
    public static String REDIRECTURL;


    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        APPSECRET = appsecret;
        REDIRECTURL = redirecturl;
    }
}
