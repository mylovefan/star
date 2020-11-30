package com.star.module.user.controller;


import com.star.module.user.facade.BackendFacade;
import com.star.module.user.vo.StartVo;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 明星表 前端控制器
 * </p>
 *
 * @author ljk <longwaystyle@163.com>
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/star")
public class StarController implements BackendFacade {

    @Override
    public StartVo getStars() {
        return null;
    }
}