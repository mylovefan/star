package com.star.module.front.facade;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.vo.WeekRankVo;
import com.star.module.operation.dto.StarPageDto;
import com.star.module.operation.vo.StartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "首页",tags = "首页")
@RequestMapping("home/")
public interface HomeFacede {

    @ApiOperation("周榜排名列表")
    @GetMapping("weekRank/list")
    PageSerializable<WeekRankVo> pageListWeekRank(@RequestBody PageDTO pageDTO);

}
