package com.example.home.ApacheCamelEureka;

import org.apache.camel.component.cxf.CxfClientCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"gs_producing_web_service","com.example.home.ApacheCamelEureka"})
@EnableDiscoveryClient
public class ApacheCamelEurekaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ApacheCamelEurekaApplication.class, args);
    }



}
