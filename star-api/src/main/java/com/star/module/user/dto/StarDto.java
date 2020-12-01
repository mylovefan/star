package com.star.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StarDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "首页轮播图")
    private String homeImg;

    @ApiModelProperty(value = "详情页")
    private String detailImg;

    @ApiModelProperty(value = "打榜弹窗图")
    private String hitPopupImg;

    @ApiModelProperty(value = "明星头像")
    private String avatar;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "是否热门搜索")
    private Integer hotSearch;

    @ApiModelProperty(value = "所属开屏图")
    private String openImg;
}