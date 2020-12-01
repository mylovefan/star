package com.star.module.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class StartVo{


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "明星头像")
    private String avatar;

    @ApiModelProperty(value = "明星照片")
    private String picture;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "是否热门搜索")
    private Integer hotSearch;

    @ApiModelProperty(value = "所属开屏图")
    private String openImg;

    @ApiModelProperty(value = "明星热力值")
    private Integer hotNums;

    @ApiModelProperty(value = "首页轮播图")
    private String homeImg;

    @ApiModelProperty(value = "详情页")
    private String detailImg;

    @ApiModelProperty(value = "打榜弹窗图")
    private String hitPopupImg;

    @ApiModelProperty(value = "周冠军次数")
    private Integer rankWeekChampionNum;

    @ApiModelProperty(value = "月冠军次数")
    private Integer rankMonthChampionNum;

    @ApiModelProperty(value = "周亚军次数")
    private Integer rankWeekSecondNum;

    @ApiModelProperty(value = "月亚军次数")
    private Integer rankMonthSecondNum;

    @ApiModelProperty(value = "周季军次数")
    private Integer rankWeekThirdNum;

    @ApiModelProperty(value = "月季军次数")
    private Integer rankMonthThirdNum;

    @ApiModelProperty(value = "本周排名")
    private Integer thisWeekRank;

    @ApiModelProperty(value = "本月排名")
    private Integer thisMonthRank;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;


}
