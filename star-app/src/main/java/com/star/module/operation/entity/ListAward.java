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
 * 
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ListAward对象", description="")
public class ListAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "WEEK:周榜 MONTH-月榜")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "是否开启")
    @TableField("open")
    private Integer open;

    @ApiModelProperty(value = "1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "宣传页")
    @TableField("img")
    private String img;

    @ApiModelProperty(value = "是否开启最低热力值")
    @TableField("open_min")
    private Integer openMin;

    @ApiModelProperty(value = "最低热力值")
    @TableField("min_val")
    private Integer minVal;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
