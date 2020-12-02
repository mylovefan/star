package com.star.module.operation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
public class ListAwardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "WEEK:周榜 MONTH-月榜")
    private String code;

    @ApiModelProperty(value = "是否开启")
    private Integer open;

    @ApiModelProperty(value = "1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏")
    private Integer type;

    @ApiModelProperty(value = "宣传页")
    private String img;

    @ApiModelProperty(value = "是否开启最低热力值")
    private Integer openMin;

    @ApiModelProperty(value = "最低热力值")
    private Integer minVal;


}
