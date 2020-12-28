package com.star.module.operation.vo;

import com.star.module.operation.dto.TagsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 明星表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-12-01
 */
@Data
public class StarDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "star_id")
    private Long starId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "明星头像")
    private String avatar;

    @ApiModelProperty(value = "明星照片")
    private String picture;

    @ApiModelProperty(value = "所属开屏图")
    private String openImg;

    @ApiModelProperty(value = "明星热力值")
    private Integer hotNums;

    @ApiModelProperty(value = "首页轮播图")
    private String homeImg;

    @ApiModelProperty(value = "详情页")
    private String detailImg;

    @ApiModelProperty(value = "打榜弹窗图")
    private String hitPopupImg;

    private List<TagsDto> tags;



}
