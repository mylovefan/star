package com.star.module.front.entity;

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
 * 粉丝打榜记录表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FensMarkLog对象", description="粉丝打榜记录表")
public class FensMarkLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "粉丝id")
    @TableField("fens_id")
    private Long fensId;

    @ApiModelProperty(value = "明星id")
    @TableField("star_id")
    private Long starId;

    @ApiModelProperty(value = "热力值")
    @TableField("vigour_val")
    private Integer vigourVal;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;


}
