package com.star.module.front.dao;

import com.github.pagehelper.Page;
import com.star.module.front.entity.HitList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.operation.vo.FensMarkVo;
import com.star.module.operation.vo.HitListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 打榜记录表 Mapper 接口
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@Repository
public interface HitListMapper extends BaseMapper<HitList> {

    List<HitListVo> selectHitRankByStar(@Param(value = "startTime") String startTime, @Param(value = "endTime")String endTime,
                                        @Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize, @Param(value = "sortType")int sortType, @Param(value = "needLimit") boolean needLimit);

    int totalCount(@Param(value = "startTime") String startTime, @Param(value = "endTime")String endTime);

    List<FensMarkVo> selectFensMarkRankByFens(@Param(value = "starId") Long starId, @Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize,
                                              @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime, @Param(value = "sortType")int sortType, @Param(value = "needLimit") boolean needLimit);

    int totalCountFensMark(@Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);


    Integer totalCountVigourMark(@Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime,Long starId);

    /**
     * 明星本周|本月排名
     *
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getThisRank(@Param(value = "id") Long id,@Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);


    /**
     * 粉丝本周|本月排名
     *
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    Page<FensVigourRankVo> getFensThisRank(@Param(value = "id") Long id, @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);
}
