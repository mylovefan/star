package com.star.module.operation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FensMrgStarHiyDto {

    @ApiModelProperty("粉丝id")
    private Long id;

    @ApiModelProperty("明星id")
    private Long starId;

    @ApiModelProperty("明星名称")
    private String starName;

    @ApiModelProperty("热力值")
    private Integer vigourVal;
}
