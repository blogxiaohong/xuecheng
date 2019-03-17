package com.xuecheng.cms_manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan(basePackages = "com.xuecheng.api")
@ComponentScan(basePackages = "com.xuecheng.cms_manage")
@ComponentScan(basePackages = "com.xuecheng.framework")
public class CmsPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsPageApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
