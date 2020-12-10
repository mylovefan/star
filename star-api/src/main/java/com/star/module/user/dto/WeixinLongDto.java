package com.star.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeixinLongDto {

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "非敏感信息")
    private String rawData;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "敏感加密信息")
    private String encrypteData;

    @ApiModelProperty(value = "偏移向量")
    private String iv;
}
