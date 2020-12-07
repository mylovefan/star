package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.RankDto;
import com.star.module.front.facade.HomeFacade;
import com.star.module.front.service.IFensService;
import com.star.module.front.service.IGuardService;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.HitDetailVo;
import com.star.module.front.vo.HomeCarouselVo;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.front.vo.PersonalVo;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.service.ICarouselService;
import com.star.module.operation.tesk.StarRankTask;
import com.star.module.operation.vo.FensMarkVo;
import com.star.module.operation.vo.HitListVo;
import com.star.module.user.common.IgnoreSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController implements HomeFacade {

    @Autowired
    private IHitListService iHitListService;
    @Autowired
    private ICarouselService iCarouselService;
    @Autowired
    private IStarService iStarService;
    @Autowired
    private IGuardService iGuardService;
    @Autowired
    private IFensService iFensService;

    @Override
    public PersonalVo getFens() {
        return iFensService.personalCenterInfo();
    }

    @Override
    @IgnoreSecurity
    public List<HomeCarouselVo> carouselList() {
        return iCarouselService.carouselList();
    }

    @Override
    public PageSerializable<MyGuardVo> selectMyGuard(@RequestBody PageDTO pageDTO) {
        return iGuardService.selectMyGuard(pageDTO);
    }

    @Override
    public PageSerializable<HitListVo> pageListRank(@RequestBody RankDto rankDto) {
        return iHitListService.pageListRank(rankDto);
    }

    @Override
    public PageSerializable<FensMarkVo> selectFensRankPage(@RequestBody FensMarkRankDto fensMarkRankDto) {
        return iHitListService.selectFensRankPage(fensMarkRankDto);
    }

    @Override
    public List<String> hotSearch() {
        return iStarService.hotSearch();
    }

    @Override
    public HitDetailVo selectHitDetail(@RequestParam("starId") Long starId) {
        return iStarService.selectHitDetail(starId);
    }

    @Override
    public void hit(@RequestParam("starId") Long starId, @RequestParam("vigourVal") Integer vigourVal) {

    }
}
