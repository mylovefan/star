package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HotStarVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;


}
