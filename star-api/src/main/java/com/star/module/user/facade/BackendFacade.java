package com.star.module.user.facade;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.user.dto.StarDto;
import com.star.module.user.vo.StartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "后台接口", tags = "后台接口")
@RequestMapping("backend/")
public interface BackendFacade {


    @ApiOperation(value = "明星列表")
    @PostMapping("star/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "明星姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "id",value = "id", paramType = "query", dataType = "Long")
    })
    PageSerializable<StartVo> getStars(PageDTO pageDTO, @RequestParam(value = "name") String name, @RequestParam(value = "id") Long id);

    @ApiOperation(value = "新增明星")
    @PostMapping("star/add")
    void addStar(@RequestBody StarDto dto);
}
