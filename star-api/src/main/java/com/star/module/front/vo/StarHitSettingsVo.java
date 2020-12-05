package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 热力设置表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Data
public class StarHitSettingsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "热力抽奖8栏位数值 例：11,13,43,55,22")
    private String drawFieldNums;

    @ApiModelProperty(value = "每次签到获热力值数")
    private Integer vigourSignNum;

    @ApiModelProperty(value = "每次看视频获得热力值数")
    private Integer vigourVideoNum;

    @ApiModelProperty(value = "每次分享微信获得热力值数")
    private Integer vigourShareNum;

    @ApiModelProperty(value = "是否签到")
    private boolean signFlag;

}
