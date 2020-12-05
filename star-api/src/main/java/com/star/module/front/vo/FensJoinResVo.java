package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FensJoinResVo {

    @ApiModelProperty(value = "粉丝Id")
    private Long fensId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "完成次数")
    private Integer completeNum;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "是否是自己")
    private boolean flag;

}
