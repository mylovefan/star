package com.star.module.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.module.front.entity.FensMarkLog;
import com.star.module.user.vo.FensMarkVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 粉丝打榜记录表 Mapper 接口
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Repository
public interface FensMarkLogMapper extends BaseMapper<FensMarkLog> {

    List<FensMarkVo> selectMarkRankByFens(@Param(value = "fensId") Long fensId, @Param(value = "starId") Long starId, @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);
}
