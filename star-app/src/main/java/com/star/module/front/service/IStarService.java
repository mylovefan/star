package com.star.module.front.service;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;
import com.star.module.user.dto.StarDto;
import com.star.module.user.vo.StartVo;

/**
 * <p>
 * 明星表 服务类
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
public interface IStarService extends IService<Star> {

    PageSerializable<StartVo> selectPage(PageDTO pageDTO, String name, Long id);

    void addStar(StarDto dto);
}
