package com.star.module.front.facade;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.FinishTaskVigourDto;
import com.star.module.front.dto.RankDto;
import com.star.module.front.dto.ResourcesRankDto;
import com.star.module.front.dto.StarFensRankDto;
import com.star.module.front.vo.*;
import com.star.module.operation.dto.StarPageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "明星详情页",tags = "明星详情页")
@RequestMapping("starDetail/")
public interface StarDetailFacade {

    @ApiOperation("明星详情页明星信息")
    @GetMapping("selectStarInfo")
    StarInfoVo selectStarInfo(@RequestParam("id") Long id);

    @ApiOperation("周榜|月榜|总榜")
    @PostMapping("selectFensRank")
    PageSerializable<FensVigourRankVo> selectFensRank(@RequestBody StarFensRankDto rankDto);

    @ApiOperation("明星详情页打榜弹窗")
    @GetMapping("selectStarGuardList")
    List<StarGuardVo> selectStarGuardList(@RequestParam("starId") Long starId);

    @ApiOperation("查询热力值设置")
    @GetMapping("selectHitSettings")
    StarHitSettingsVo selectHitSettings(@RequestParam("starId") Long starId);

    @ApiOperation("资源列表")
    @PostMapping("selectResources")
    PageSerializable<StarResourcesVo> selectResources(@RequestBody StarPageDto starPageDto);

    @ApiOperation("粉丝资源排名")
    @PostMapping("selectResourcesRank")
    PageSerializable<FensJoinResVo> selectResourcesRank(@RequestBody ResourcesRankDto resourcesRankDto);

    @ApiOperation("完成任务之后获得热力值接口")
    @PostMapping("getVigourVal")
    void getVigourVal(@RequestBody FinishTaskVigourDto finishTaskVigourDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourcesRelId", value = "活动列表返回", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "是否看完视频", dataType = "Integer", paramType = "query")
    })
    @ApiOperation("参与活动")
    @PostMapping("joinResources")
    void joinResources(@RequestParam("resourcesRelId") Long resourcesRelId,@RequestParam("status") Integer status);
}
