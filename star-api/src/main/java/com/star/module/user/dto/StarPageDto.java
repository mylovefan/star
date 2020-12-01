package com.star.module.user.dto;

import com.star.commen.dto.PageDTO;
import lombok.Data;

@Data
public class StarPageDto extends PageDTO {

    private Long id;

    private String name;
}
