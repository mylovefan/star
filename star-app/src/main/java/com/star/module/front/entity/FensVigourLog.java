package com.star.module.front.entity;

import java.time.LocalDate;
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
 * 粉丝热力获取记录
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FensVigourLog对象", description="粉丝热力获取记录")
public class FensVigourLog implements Serializable {

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

    @ApiModelProperty(value = "热力值类型(1-签到 2-抽奖 3-看视频 4-分享 5-赠送)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "热力值")
    @TableField("vigour_val")
    private Integer vigourVal;

    @ApiModelProperty(value = "获取热力值日期 年月日")
    @TableField("vig_time")
    private LocalDate vigTime;

    @ApiModelProperty(value = "赠送人")
    @TableField("add_user")
    private String addUser;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;


}
