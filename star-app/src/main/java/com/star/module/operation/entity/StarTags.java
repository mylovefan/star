package com.star.module.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 明星标签表
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StarTags对象", description="明星标签表")
public class StarTags implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "明星id")
    @TableField("star_id")
    private Long starId;

    @ApiModelProperty(value = "标签id")
    @TableField("tags_id")
    private Long tagsId;

    @ApiModelProperty(value = "标签名称")
    @TableField("tags_name")
    private String tagsName;


}
