package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
public class StarResourcesVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "resourcesRelId")
    private Long resourcesRelId;

    @ApiModelProperty(value = "资源类型(1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏)")
    private Integer type;

    @ApiModelProperty(value = "金额和大屏名称")
    private String mark;

    @ApiModelProperty(value = "目标人数")
    private Integer target;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "参与人数")
    private Integer joinNum;

    @ApiModelProperty(value = "达成人数")
    private Integer reachNum;

    @ApiModelProperty(value = "粉丝排行")
    private List<String> fensList;
}
