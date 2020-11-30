package com.star.module.user.facade;

import com.star.module.user.vo.StartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "后台接口", tags = "后台接口")
@RequestMapping("backend/")
public interface BackendFacade {


    @ApiOperation(value = "明星列表")
    @PostMapping("star/list")
    StartVo getStars();
}
