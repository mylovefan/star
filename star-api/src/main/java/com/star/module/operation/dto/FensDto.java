package com.star.module.operation.dto;

import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FensDto extends PageDTO {

    private Long id;

    @ApiModelProperty("是否自建粉丝")
    private Integer bulid = 0;
}
