package com.star.module.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.star.module.front.entity.FensJoin;
import com.star.module.front.vo.FensJoinResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 粉丝资源参与表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-02
 */
public interface FensJoinMapper extends BaseMapper<FensJoin> {

    Integer selectCompleteNum(@Param("resourcesRelId") Long  resourcesRelId);

    List<String> selectFens(@Param("resourcesRelId") Long  resourcesRelId);


    Page<FensJoinResVo> selectFensResources(@Param("resourcesRelId") Long  resourcesRelId);

}
