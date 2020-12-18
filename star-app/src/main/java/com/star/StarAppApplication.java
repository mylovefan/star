package com.star;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@SpringBootApplication(scanBasePackages = {"com.star"})
@MapperScan({"com.star.module.*.dao"})
@EnableFeignClients
@Slf4j
//@EnableSwagger2
public class StarAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(StarAppApplication.class, args);
        log.info("starApp Run SUCCESS");
    }

}
