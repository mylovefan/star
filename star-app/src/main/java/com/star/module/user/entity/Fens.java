package com.star.module.user.entity;

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
 * 微信用户信息
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Fens对象", description="微信用户信息")
public class Fens implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "open_id")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "网名")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_visit_time")
    private LocalDateTime lastVisitTime;

    @ApiModelProperty(value = "session_key")
    @TableField("session_key")
    private String sessionKey;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "国")
    @TableField("country")
    private String country;

    @ApiModelProperty(value = "头像")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "当前活力值")
    @TableField("vigour_val")
    private Integer vigourVal;

    @ApiModelProperty(value = "累计活力值")
    @TableField("total_vigour_val")
    private Integer totalVigourVal;

    @ApiModelProperty(value = "消耗活力值")
    @TableField("consume_vigour_val")
    private Integer consumeVigourVal;

    @ApiModelProperty(value = "个人标语")
    @TableField("slogan")
    private String slogan;

    @ApiModelProperty(value = "创建时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
