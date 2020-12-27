package com.star.module.operation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.module.operation.entity.StarResourcesRel;
import com.star.module.operation.vo.ResourceStarVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 明星资源关系表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
public interface StarResourcesRelMapper extends BaseMapper<StarResourcesRel> {


    void updateNum(@Param("id") Long id, @Param("joinNum") int joinNum, @Param("reachNum") int reachNum,@Param("status") int status);


    List<ResourceStarVo> selectStar(@Param("id") Long id);
}
