package com.star;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
