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

    @ApiModelProperty(value = "详情页图片")
    private String levelImg;

    @ApiModelProperty(value = "周月")
    private String code;

    @ApiModelProperty(value = "开屏图")
    private String openImg;

}
