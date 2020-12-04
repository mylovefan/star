package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyGuardVo {
    @ApiModelProperty(value = "明星id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "明星头像")
    private String avatar;

    @ApiModelProperty(value = "明星热力值")
    private Integer hotNums;
}
