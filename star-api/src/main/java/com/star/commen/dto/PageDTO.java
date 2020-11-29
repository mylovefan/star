package com.star.commen.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
public class PageDTO {

    @ApiModelProperty(value = "当前页码", example = "1")
    public Integer pageNum = 1;

    @ApiModelProperty(value = "页面数量", example = "20")
    public Integer pageSize = 10;

}
