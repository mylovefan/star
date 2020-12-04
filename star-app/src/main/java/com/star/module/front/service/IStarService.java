package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.StartVo;

import java.util.Date;

/**
 * <p>
 * 明星表 服务类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface IStarService extends IService<Star> {

    PageSerializable<StartVo> selectPage(StarPageDto starPageDto);

    void addStar(StarDto dto);

    void updateStar(StarDto dto);

    /**
     * 更新明星本周排名/本月排名
     * @param type 0本周；1本月
     * @param startTime
     * @param endTime
     */
    void getStarRank(int type, Date startTime, Date endTime);
}
