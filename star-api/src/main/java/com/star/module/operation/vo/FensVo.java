package com.star.module.operation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FensVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "粉丝id")
    private Long fensId;

    @ApiModelProperty(value = "网名")
    private String nickName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastVisitTime;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "当前活力值")
    private Integer vigourVal;

    @ApiModelProperty(value = "累计活力值")
    private Integer totalVigourVal;

    @ApiModelProperty(value = "消耗活力值")
    private Integer consumeVigourVal;

    @ApiModelProperty(value = "最后打榜明星")
    private String lastStar;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime addTime;


}
