package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.ResourcesRankDto;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.facade.StarDetailFacade;
import com.star.module.front.service.IGuardService;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IHitSettingsService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.*;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.service.IResourcesService;
import com.star.module.user.common.IgnoreSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StarDetailController implements StarDetailFacade {

    @Autowired
    private IStarService starService;

    @Autowired
    private IHitListService hitListService;

    @Autowired
    private IGuardService guardService;

    @Autowired
    private IHitSettingsService hitSettingsService;

    @Autowired
    private IResourcesService resourcesService;

    @Override
    @IgnoreSecurity
    public StarInfoVo selectStarInfo(@RequestParam("id") Long id) {
        return starService.selectStarInfo(id);
    }


    @Override
    @IgnoreSecurity
    public PageSerializable<FensVigourRankVo> selectFensRank(@RequestBody StarFensRankDto rankDto) {
        return hitListService.selectFensRank(rankDto);
    }

    @Override
    @IgnoreSecurity
    public List<StarGuardVo> selectStarGuardList(@RequestParam("starId") Long starId) {
        return guardService.selectStarGuardList(starId);
    }

    @Override
    @IgnoreSecurity
    public StarHitSettingsVo selectHitSettings(@RequestParam("starId") Long starId) {
        return hitSettingsService.selectHitSettings(starId);
    }

    @Override
    @IgnoreSecurity
    public PageSerializable<StarResourcesVo> selectResources(@RequestBody StarPageDto starPageDto) {
        return resourcesService.selectResources(starPageDto);
    }

    @Override
    @IgnoreSecurity
    public PageSerializable<FensJoinResVo> selectResourcesRank(@RequestBody ResourcesRankDto resourcesRankDto) {
        return resourcesService.selectResourcesRank(resourcesRankDto);
    }
}
