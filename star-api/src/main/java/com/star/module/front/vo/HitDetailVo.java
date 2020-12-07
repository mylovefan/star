package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HitDetailVo {

    @ApiModelProperty(value = "打榜弹窗图")
    private String hitPopupImg;

    @ApiModelProperty(value = "当前活力值")
    private Integer vigourVal;
}
