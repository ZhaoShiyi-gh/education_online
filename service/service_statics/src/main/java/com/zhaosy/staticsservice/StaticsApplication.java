package com.zhaosy.staticsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.zhaosy.staticsservice.mapper"})
@ComponentScan(basePackages = {"com.zhaosy"})
public class StaticsApplication {
    public static void main(String[] args){
        SpringApplication.run(StaticsApplication.class, args);
    }
}
