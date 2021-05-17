package com.example.demo.springeurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.spring.guides.gs_producing_web_service","com.example.demo.springeurekaclient"})
public class SpringEurekaClientApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SpringEurekaClientApplication.class, args);
    }
}


