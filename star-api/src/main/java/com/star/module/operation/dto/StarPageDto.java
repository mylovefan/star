package com.star.module.operation.dto;

import com.star.commen.dto.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StarPageDto extends PageDTO {

    private Long id;

    private String name;

    @ApiModelProperty(value = "周排序 (0-正序 1-倒叙)")
    private Integer weekSort;

    @ApiModelProperty(value = "月排序 (0-正序 1-倒叙)")
    private Integer monthSort;


}
