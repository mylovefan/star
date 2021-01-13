package com.star.module.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.RankDto;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.entity.HitList;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.dto.FensMrgStarHiyDto;
import com.star.module.operation.dto.HitListDto;
import com.star.module.operation.vo.FensMarkVo;
import com.star.module.operation.vo.HitListVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * <p>
 * 打榜记录表 服务类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface IHitListService extends IService<HitList> {

    /**
     * 统计周/月冠军次数
     * @param starId 明星ID
     *  @param startTime 开始时间（本周第一天/本月第一天）
     *  @param endTime 结束时间
     * @return
     */
    int statisticsRankByTime(Long starId ,Date startTime ,Date endTime);

    /**
     * 榜单列表
     * @param hitListDto
     * @return
     */
    PageSerializable<HitListVo> selectPage(HitListDto hitListDto);

    /**
     * 粉丝打榜列表
     * @param fensMarkRankDto
     * @return
     */
    PageSerializable<FensMarkVo> selectFensRankPage(FensMarkRankDto fensMarkRankDto);

    /**
     * 首页榜单列表
     * @param rankDto
     * @return
     */
    PageSerializable<HitListVo> pageListRank(RankDto rankDto);

    /**
     * 粉丝周榜月榜总榜
     *
     * @param rankDto
     * @return
     */
    PageSerializable<FensVigourRankVo> selectFensRank(StarFensRankDto rankDto);

    /**
     * 打榜
     *
     * @param starId
     * @param vigourVal
     */
    void hit( Long starId, Integer vigourVal);

    /**
     * 自建粉丝打榜
     *
     * @param fensMrgStarHiyDto
     */
    void bulidFensHit(FensMrgStarHiyDto fensMrgStarHiyDto);
}
