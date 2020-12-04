package com.star.module.front.controller;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.facade.StarDetailFacade;
import com.star.module.front.service.IHitListService;
import com.star.module.front.service.IStarService;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.front.vo.StarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarDetailController implements StarDetailFacade {

    @Autowired
    private IStarService starService;

    @Autowired
    private IHitListService hitListService;

    @Override
    public StarInfoVo selectStarInfo(@RequestParam("id") Long id) {
        return starService.selectStarInfo(id);
    }


    @Override
    public PageSerializable<FensVigourRankVo> selectFensRank(@RequestBody StarFensRankDto rankDto) {
        return hitListService.selectFensRank(rankDto);
    }
}
