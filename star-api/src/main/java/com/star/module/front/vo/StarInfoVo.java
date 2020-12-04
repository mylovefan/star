package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StarInfoVo {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "明星头像")
    private String avatar;

    @ApiModelProperty(value = "打榜弹窗图")
    private String hitPopupImg;

    @ApiModelProperty(value = "详情页")
    private String detailImg;

    @ApiModelProperty(value = "本周排名")
    private Integer thisWeekRank;

    @ApiModelProperty(value = "本月排名")
    private Integer thisMonthRank;

}
