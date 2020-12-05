package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StarGuardVo {

    @ApiModelProperty(value = "粉丝id")
    private Long fensId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "热力值")
    private Integer vigourVal;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;
}
