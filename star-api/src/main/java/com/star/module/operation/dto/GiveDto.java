package com.star.module.operation.dto;

import com.star.commen.dto.PageDTO;
import lombok.Data;

@Data
public class GiveDto extends PageDTO {

    private Long id;

    private String giveTime;

    private String giveTimeEnd;
}
