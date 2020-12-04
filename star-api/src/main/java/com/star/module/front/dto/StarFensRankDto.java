package com.star.module.front.dto;

import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Data
public class StarFensRankDto extends PageDTO {

    @ApiModelProperty(value = "排名类型：0周榜；1月榜；2总榜")
    private int rankType;

    @ApiModelProperty(value = "明星id")
    private Long id;

}
