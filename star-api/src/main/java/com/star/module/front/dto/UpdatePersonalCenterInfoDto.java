package com.star.module.front.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePersonalCenterInfoDto {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "个人标语")
    private String slogan;

    @ApiModelProperty(value = "是否开启个人标语 0未开启 1开启")
    private String sloganOpen;
}
