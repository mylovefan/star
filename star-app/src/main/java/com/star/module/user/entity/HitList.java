package com.star.module.user.entity;

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
 * 打榜记录表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
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

    @ApiModelProperty(value = "明星姓名")
    @TableField("star_name")
    private String starName;

    @ApiModelProperty(value = "明星头像")
    @TableField("star_avatar_url")
    private String starAvatarUrl;

    @ApiModelProperty(value = "本次消耗活力值")
    @TableField("vigour_val")
    private Integer vigourVal;

    @ApiModelProperty(value = "打榜粉丝id")
    @TableField("fens_id")
    private Long fensId;

    @ApiModelProperty(value = "打榜粉丝昵称")
    @TableField("fens_nick_name")
    private String fensNickName;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;


}
