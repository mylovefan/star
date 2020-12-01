package com.star.module.operation.controller;


import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.user.facade.BackendFacade;
import com.star.module.front.service.IStarService;
import com.star.module.user.vo.StartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 明星表 前端控制器
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@RestController
public class StarController implements BackendFacade {

    @Autowired
    private IStarService iStarService;

    @Override
    public PageSerializable<StartVo> getStars(PageDTO pageDTO, String name, Long id) {
        return iStarService.selectPage(pageDTO,name,id);
    }
}
