package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.facade.HomeFacede;
import com.star.module.front.service.IHitListService;
import com.star.module.front.vo.WeekRankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements HomeFacede {
    @Autowired
    private IHitListService iHitListService;

    @Override
    public PageSerializable<WeekRankVo> pageListWeekRank(PageDTO pageDTO) {
        return null;
    }
}
