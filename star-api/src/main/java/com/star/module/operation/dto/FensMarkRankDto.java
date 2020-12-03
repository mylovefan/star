package com.star.module.operation.dto;

import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FensMarkRankDto extends PageDTO {

    @ApiModelProperty(value = "明星ID")
    private Long starId;

    @ApiModelProperty(value = "粉丝ID")
    private Long fensId;

    @ApiModelProperty(value = "粉丝名称")
    private String fensName;

    @ApiModelProperty(value = "周开始时间")
    private String startTime;

    @ApiModelProperty(value = "周结束时间")
    private String endTime;

    @ApiModelProperty(value = "排序 0：正序；1：倒序；")
    private int sortType;
}
