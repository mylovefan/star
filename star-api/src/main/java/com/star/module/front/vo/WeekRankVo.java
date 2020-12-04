package com.star.module.front.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "周榜排名")
public class WeekRankVo {

    @ApiModelProperty(value = "明星Id")
    private Long starId;

    @ApiModelProperty(value = "明星姓名")
    private String starName;

    @ApiModelProperty(value = "明星头像")
    private String acatar;

    @ApiModelProperty(value = "排名")
    private int rank;

    @ApiModelProperty(value = "总活力值")
    private Long totalVigourVal;
}
