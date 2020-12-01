package com.star.module.operation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImportGiveVo {

    @ApiModelProperty(value = "成功数量")
    private int succ;

    @ApiModelProperty(value = "失败数量")
    private int fail;

    @ApiModelProperty(value = "粉丝IDS")
    private List<String> fensIds;

}
