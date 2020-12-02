package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.entity.FensMarkLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.user.dto.FensMarkRankDto;
import com.star.module.user.vo.HitListVo;

/**
 * <p>
 * 粉丝打榜记录表 服务类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
public interface IFensMarkLogService extends IService<FensMarkLog> {

    /**
     * 粉丝为明星打榜的粉丝排名
     * @param fensMarkRankDto
     * @return
     */
    PageSerializable<HitListVo> selectPage(FensMarkRankDto fensMarkRankDto);
}
