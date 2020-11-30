package com.star.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class TencentCosConfig {

    //腾讯云的SecretId
    public String secretId = "AKIDsdFgvSs8B2xmPqA5tysDONDrJbhaotHV";
    //腾讯云的SecretKey
    public String secretKey = "EI9oqhOtKaccQ1PbaOH3hBTbbS7dx5C8";
    //腾讯云的bucket (存储桶)
    public String bucket;
    //腾讯云的region(bucket所在地区)
    public String region;
    //腾讯云的allowPrefix(允许上传的路径)
    public String allowPrefix = "*";
    //腾讯云的临时密钥时长(单位秒)
    public String durationSeconds;
    //腾讯云的访问基础链接:
    public String baseUrl;
}
