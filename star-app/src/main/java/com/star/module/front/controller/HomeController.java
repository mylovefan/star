package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.RankDto;
import com.star.module.front.facade.HomeFacede;
import com.star.module.front.service.IHitListService;
import com.star.module.front.vo.WeekRankVo;
import com.star.module.operation.vo.HitListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements HomeFacede {
    @Autowired
    private IHitListService iHitListService;

    @Override
    public PageSerializable<HitListVo> pageListRank(@RequestBody RankDto rankDto) {
        return iHitListService.pageListRank(rankDto);
    }
}
