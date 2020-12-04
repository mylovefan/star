package com.star.module.front.facade;

import com.star.module.front.vo.StarInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "明星详情页",tags = "明星详情页")
@RequestMapping("starDetail/")
public interface StarDetailFacede {

    @ApiOperation("明星详情页明星信息")
    @GetMapping("selectStarInfo")
    StarInfoVo selectStarInfo(@RequestParam("id") Long id);

}
