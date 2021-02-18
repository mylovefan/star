package com.star.module.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Carousel对象", description="轮播图")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "首页轮播图")
    @TableField("home1")
    private String home1;

    @ApiModelProperty(value = "二级轮播图")
    @TableField("level1")
    private String level1;

    @ApiModelProperty(value = "首页轮播图")
    @TableField("home2")
    private String home2;

    @ApiModelProperty(value = "二级轮播图")
    @TableField("level2")
    private String level2;

    @ApiModelProperty(value = "是否开启")
    @TableField("open")
    private Integer open;

    @ApiModelProperty(value = "是否开启")
    @TableField("open2")
    private Integer open2;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
