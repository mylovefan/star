package com.star.module.operation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BulidFensDto {


    @ApiModelProperty(value = "网名")
    private String nickName;

    @ApiModelProperty(value = "图像")
    private String avatarUrl;
}
