package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.operation.dto.StarDto;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.StartVo;

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
}
