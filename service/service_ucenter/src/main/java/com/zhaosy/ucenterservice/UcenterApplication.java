package com.zhaosy.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.zhaosy.ucenterservice.mapper"})
@ComponentScan(basePackages = {"com.zhaosy"})
public class UcenterApplication {
    public static void main(String[] args){
        SpringApplication.run(UcenterApplication.class, args);
    }
}
