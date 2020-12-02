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
 * 粉丝资源参与表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FensJoin对象", description="粉丝资源参与表")
public class FensJoin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "明星资源关联id")
    @TableField("resources_rel_id")
    private Long resourcesRelId;

    @ApiModelProperty(value = "粉丝id")
    @TableField("fens_id")
    private Long fensId;

    @ApiModelProperty(value = "完成次数")
    @TableField("complete_num")
    private Integer completeNum;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
