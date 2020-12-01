package com.star.module.front.service;

import com.star.module.front.entity.HitList;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
