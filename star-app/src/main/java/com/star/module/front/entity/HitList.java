package com.star.module.front.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 打榜记录表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="HitList对象", description="打榜记录表")
public class HitList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "打榜明星id")
    @TableField("star_id")
    private Long starId;

    @ApiModelProperty(value = "本次消耗活力值")
    @TableField("vigour_val")
    private Integer vigourVal;

    @ApiModelProperty(value = "打榜粉丝id")
    @TableField("fens_id")
    private Long fensId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;


}
