package com.zhaosy.cmtservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.zhaosy.cmtservice.mapper"})
@ComponentScan(basePackages = {"com.zhaosy"})
public class CmtApplication {

    public static void main(String[] args){
        SpringApplication.run(CmtApplication.class, args);
    }
}
