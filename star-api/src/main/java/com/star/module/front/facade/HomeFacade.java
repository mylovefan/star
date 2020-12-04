package com.star.module.front.facade;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.vo.HomeCarouselVo;
import com.star.module.front.vo.MyGuardVo;
import com.star.module.front.vo.PersonalVo;
import com.star.module.operation.dto.FensMarkRankDto;
import com.star.module.operation.vo.FensMarkVo;
import com.star.module.operation.vo.HitListVo;
import com.star.module.front.dto.RankDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "首页",tags = "首页")
@RequestMapping("home/")
public interface HomeFacade {

    @ApiOperation("我的信息")
    @PostMapping("fens")
    PersonalVo getFens();

    @ApiOperation("轮播图列表")
    @PostMapping("carousel/list")
    List<HomeCarouselVo> carouselList();

    @ApiOperation(value = "我的守护")
    @PostMapping("selectMyGuard")
    PageSerializable<MyGuardVo> selectMyGuard(@RequestBody PageDTO pageDTO);

    @ApiOperation("周榜月榜总榜排名")
    @PostMapping("weekRank/list")
    PageSerializable<HitListVo> pageListRank(@RequestBody RankDto rankDto);

    @ApiOperation("粉丝排名")
    @PostMapping("fensRank/list")
    PageSerializable<FensMarkVo> selectFensRankPage(@RequestBody FensMarkRankDto fensMarkRankDto);


}
