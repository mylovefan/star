package com.star.module.operation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.Value;

import java.util.Date;

@Data
public class HitListDto extends PageDTO {

    @ApiModelProperty(value = "榜单类型 0：周榜；1：月榜；2：总榜")
    private int hitListType;

    @ApiModelProperty(value = "列表类型 默认空， 0：本周；1：近三个月周时间段；2：具体某个月份")
    private Integer listType;

    @ApiModelProperty(value = "周开始时间")
    private String startTime;

    @ApiModelProperty(value = "周结束时间")
    private String endTime;

    @ApiModelProperty(value = "具体月份值")
    private int monthNum;

    @ApiModelProperty(value = "明星姓名")
    private String starName;

    @ApiModelProperty(value = "明星ID")
    private Long starId;

    @ApiModelProperty(value = "排序 0：正序；1：倒序；")
    private int sortType;
}
