package com.star.module.front.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FinishTaskVigourDto {

    @ApiModelProperty(value = "明星id")
    private Long starId;

    @ApiModelProperty(value = "任务类型 1-签到 2-抽奖 3-看视频 4-分享")
    private Integer type;
}
