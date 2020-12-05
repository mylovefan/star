package com.star.module.operation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.star.module.front.vo.StarResourcesVo;
import com.star.module.operation.entity.Resources;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

    Page<StarResourcesVo> selectStarResources(@Param("starId") Long starId,@Param("nowTime") String nowTime);

}
