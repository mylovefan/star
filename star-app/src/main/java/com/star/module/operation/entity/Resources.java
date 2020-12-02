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
 * 资源表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Resources对象", description="资源表")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "资源类型(1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "金额和大屏名称")
    @TableField("mark")
    private String mark;

    @ApiModelProperty(value = "目标人数")
    @TableField("target")
    private Integer target;

    @ApiModelProperty(value = "开始时间")
    @TableField("begin_time")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "明星标签")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
