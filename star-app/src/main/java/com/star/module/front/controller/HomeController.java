package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.RankDto;
import com.star.module.front.facade.HomeFacede;
import com.star.module.front.service.IHitListService;
import com.star.module.front.vo.HomeCarouselVo;
import com.star.module.operation.service.ICarouselService;
import com.star.module.operation.tesk.StarRankTask;
import com.star.module.operation.vo.HitListVo;
import com.star.module.user.common.IgnoreSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController implements HomeFacede {

    @Autowired
    private IHitListService iHitListService;
    @Autowired
    private ICarouselService iCarouselService;
    @Autowired
    private StarRankTask starRankTask;


    @Override
    @IgnoreSecurity
    public List<HomeCarouselVo> carouselList() {
        starRankTask.starRankByWeek();
        return iCarouselService.carouselList();
    }

    @Override
    public PageSerializable<HitListVo> pageListRank(@RequestBody RankDto rankDto) {
        return iHitListService.pageListRank(rankDto);
    }
}
