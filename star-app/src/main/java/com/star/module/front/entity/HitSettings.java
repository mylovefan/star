package com.star.module.front.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 热力设置表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="HitSettings对象", description="热力设置表")
public class HitSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "热力抽奖8栏位数值 例：11,13,43,55,22")
    @TableField("draw_field_nums")
    private String drawFieldNums;

    @ApiModelProperty(value = "每日抽奖最高次数")
    @TableField("deaw_max_num")
    private Integer deawMaxNum;

    @ApiModelProperty(value = "高分值策略开关 0：关闭；1开启；")
    @TableField("score_strategy_flag")
    private Integer scoreStrategyFlag;

    @ApiModelProperty(value = "单个粉丝累计抽奖次数，超过后随机赠送热力值")
    @TableField("strategy_deaw_min_num")
    private Integer strategyDeawMinNum;

    @ApiModelProperty(value = "随机热力值赠送方式 0：超过（含）具体值；1：超过（含）当前八档数值；")
    @TableField("vigour_send_type")
    private Integer vigourSendType;

    @ApiModelProperty(value = "具体赠送热力值")
    @TableField("vigour_send_num")
    private String vigourSendNum;

    @ApiModelProperty(value = "每日最高签到次数")
    @TableField("sign_max_num")
    private Integer signMaxNum;

    @ApiModelProperty(value = "每次签到获热力值数")
    @TableField("vigour_sign_num")
    private Integer vigourSignNum;

    @ApiModelProperty(value = "每日看视频次数")
    @TableField("video_max_num")
    private Integer videoMaxNum;

    @ApiModelProperty(value = "每次看视频获得热力值数")
    @TableField("vigour_video_num")
    private Integer vigourVideoNum;

    @ApiModelProperty(value = "每日最高分享微信次数")
    @TableField("share_max_num")
    private Integer shareMaxNum;

    @ApiModelProperty(value = "每次分享微信获得热力值数")
    @TableField("vigour_share_num")
    private Integer vigourShareNum;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
