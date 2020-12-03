package com.star.module.front.facade;

import com.star.module.front.vo.PersonalVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "个人中心", tags = "个人中心")
@RequestMapping("personalCenter/")
public interface PersonalCenterFacade {

    @ApiOperation(value = "个人中心我的信息")
    @GetMapping("personalCenterInfo")
    PersonalVo personalCenterInfo();

}
