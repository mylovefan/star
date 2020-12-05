package com.star.module.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.module.front.entity.Guard;
import com.star.module.front.vo.StarGuardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 守护表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-03
 */
public interface GuardMapper extends BaseMapper<Guard> {

    /**
     * 打榜弹窗
     *
     * @param starId
     * @return
     */
    List<StarGuardVo> selectStarGuardList(@Param("starId") Long starId);

}
