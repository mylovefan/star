package com.star.module.operation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class HitListVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "周时间段")
    private String weekTime;

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "明星")
    private String starName;

    @ApiModelProperty(value = "明星头像")
    private String starAvatar;

    @ApiModelProperty(value = "明星id")
    private Long starId;

    @ApiModelProperty(value = "排名")
    private int rank;

    @ApiModelProperty(value = "总活力值")
    private Long totalVigourVal;

    @ApiModelProperty(value = "排序 0：正序；1：倒序；")
    private int sortType;

}
