package com.star.module.front.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.front.dto.RankDto;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.vo.FensVigourRankVo;
import com.star.module.front.vo.StarInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "明星详情页",tags = "明星详情页")
@RequestMapping("starDetail/")
public interface StarDetailFacade {

    @ApiOperation("明星详情页明星信息")
    @GetMapping("selectStarInfo")
    StarInfoVo selectStarInfo(@RequestParam("id") Long id);

    @ApiOperation("周榜|月榜|总榜")
    @PostMapping("selectFensRank")
    PageSerializable<FensVigourRankVo> selectFensRank(@RequestBody StarFensRankDto rankDto);


}
