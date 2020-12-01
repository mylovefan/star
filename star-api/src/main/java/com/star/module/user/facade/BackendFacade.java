package com.star.module.user.facade;

import com.star.module.user.vo.StartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "后台接口", tags = "后台接口")
@RequestMapping("backend/")
public interface BackendFacade {


    @ApiOperation(value = "明星列表")
    @PostMapping("star/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "明星姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "id",value = "id", paramType = "query", dataType = "Long")
    })
    StartVo getStars(@RequestParam(value = "name") String name, @RequestParam(value = "id") Long id);
}
