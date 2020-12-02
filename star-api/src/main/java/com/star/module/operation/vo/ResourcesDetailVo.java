package com.star.module.operation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.star.module.operation.dto.TagsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
public class ResourcesDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "资源类型(1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏)")
    private Integer type;

    @ApiModelProperty(value = "金额和大屏名称")
    private String mark;

    @ApiModelProperty(value = "目标人数")
    private Integer target;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "明星标签")
    private List<TagsDto> tags;

    @ApiModelProperty(value = "明星Ids")
    private List<Long> starIds;

}
