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
 * 明星表
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Star对象", description="明星表")
public class Star implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "明星头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "明星照片")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "标签")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "是否热门搜索")
    @TableField("hot_search")
    private Integer hotSearch;

    @ApiModelProperty(value = "所属开屏图")
    @TableField("open_img")
    private String openImg;

    @ApiModelProperty(value = "明星热力值")
    @TableField("hot_nums")
    private Integer hotNums;

    @ApiModelProperty(value = "首页轮播图")
    @TableField("home_img")
    private String homeImg;

    @ApiModelProperty(value = "详情页")
    @TableField("detail_img")
    private String detailImg;

    @ApiModelProperty(value = "打榜弹窗图")
    @TableField("hit_popup_img")
    private String hitPopupImg;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    private LocalDateTime deleteTime;


}