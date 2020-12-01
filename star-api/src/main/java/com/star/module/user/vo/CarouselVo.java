package com.star.module.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Data
public class CarouselVo {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "首页轮播图")
    private String home1;

    @ApiModelProperty(value = "二级轮播图")
    private String level1;

    @ApiModelProperty(value = "首页轮播图")
    private String home2;

    @ApiModelProperty(value = "二级轮播图")
    private String level2;

    @ApiModelProperty(value = "是否开启")
    private Integer open;



}