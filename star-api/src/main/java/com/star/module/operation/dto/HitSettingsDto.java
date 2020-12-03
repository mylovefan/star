package com.star.module.operation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 热力设置表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Data
public class HitSettingsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "热力抽奖8栏位数值 例：11,13,43,55,22")
    private String drawFieldNums;

    @ApiModelProperty(value = "每日抽奖最高次数")
    private Integer deawMaxNum;

    @ApiModelProperty(value = "高分值策略开关 0：关闭；1开启；")
    private Integer scoreStrategyFlag;

    @ApiModelProperty(value = "单个粉丝累计抽奖次数，超过后随机赠送热力值")
    private Integer strategyDeawMinNum;

    @ApiModelProperty(value = "随机热力值赠送方式 0：超过（含）具体值；1：超过（含）当前八档数值；")
    private Integer vigourSendType;

    @ApiModelProperty(value = "具体赠送热力值")
    private Integer vigourSendNum;

    @ApiModelProperty(value = "每日最高签到次数")
    private Integer signMaxNum;

    @ApiModelProperty(value = "每次签到获热力值数")
    private Integer vigourSignNum;

    @ApiModelProperty(value = "每日看视频次数")
    private Integer videoMaxNum;

    @ApiModelProperty(value = "每次看视频获得热力值数")
    private Integer vigourVideoNum;

    @ApiModelProperty(value = "每日最高分享微信次数")
    private Integer shareMaxNum;

    @ApiModelProperty(value = "每次分享微信获得热力值数")
    private Integer vigourShareNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
