package com.star.module.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FensVigourRankVo {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "热力值")
    private Integer vigourVal;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "是否是自己")
    private boolean flag;
}
