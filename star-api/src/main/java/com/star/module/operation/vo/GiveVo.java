package com.star.module.operation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiveVo {

    @ApiModelProperty(value = "粉丝id")
    private Long fensId;

    @ApiModelProperty(value = "网名")
    private String nickName;

    @ApiModelProperty(value = "热力值")
    private Integer vigourVal;

    @ApiModelProperty(value = "赠送人")
    private String addUser;

    @ApiModelProperty(value = "赠送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime addTime;
}
