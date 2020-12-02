package com.star.module.operation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.star.commen.dto.PageDTO;
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
public class ResourcesPageDto extends PageDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源类型(1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏)")
    private Integer type;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;



}
