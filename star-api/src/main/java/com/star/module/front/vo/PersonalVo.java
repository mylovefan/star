package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalVo {

    @ApiModelProperty(value = "ID")
    private Long fensId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "个人标语")
    private String slogan;

    @ApiModelProperty(value = "热力值")
    private Integer vigourVal;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;
}
