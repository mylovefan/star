package com.star.module.front.dto;

import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
public class ResourcesRankDto extends PageDTO {

    @ApiModelProperty(value = "resourcesRelId")
    private Long resourcesRelId;

}
