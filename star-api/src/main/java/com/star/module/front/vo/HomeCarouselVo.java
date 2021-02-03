package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HomeCarouselVo {

    @ApiModelProperty(value = "轮播图")
    private String img;

    @ApiModelProperty(value = "明星名称")
    private String starName;

    @ApiModelProperty(value = "明星id")
    private Long starId;

    @ApiModelProperty(value = "轮播图级别")
    private Integer level;

    @ApiModelProperty(value = "周月")
    private String code;

}
