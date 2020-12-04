package com.star.module.operation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class FensMarkVo {

    @ApiModelProperty(value = "周时间段")
    private String weekTime;

    @ApiModelProperty(value = "粉丝名称")
    private String fensName;

    @ApiModelProperty(value = "粉丝id")
    private Long fensId;

    @ApiModelProperty(value = "粉丝头像")
    private String fensAvatarUrl;

    @ApiModelProperty(value = "明星id")
    private Long starId;

    @ApiModelProperty(value = "排名")
    private int rank;

    @ApiModelProperty(value = "贡献值")
    private Long totalVigourVal;

    @ApiModelProperty(value = "排序 0：正序；1：倒序；")
    private int sortType;

}
