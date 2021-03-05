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
    private String home;

    @ApiModelProperty(value = "二级轮播图")
    private String level;

    @ApiModelProperty(value = "是否开启")
    private Integer open;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
