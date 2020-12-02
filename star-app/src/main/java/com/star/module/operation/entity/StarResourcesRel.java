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
 * 明星资源关系表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StarResourcesRel对象", description="明星资源关系表")
public class StarResourcesRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "资源id")
    @TableField("resources_id")
    private Long resourcesId;

    @ApiModelProperty(value = "明星id")
    @TableField("star_id")
    private Long starId;

    @ApiModelProperty(value = "参与人数")
    @TableField("join_num")
    private Integer joinNum;

    @ApiModelProperty(value = "达成人数")
    @TableField("reach_num")
    private Integer reachNum;

    @ApiModelProperty(value = "任务是否完成")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "新增时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
