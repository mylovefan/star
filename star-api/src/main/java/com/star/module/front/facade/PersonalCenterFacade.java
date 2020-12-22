package com.star.module.front.facade;

import com.github.pagehelper.PageSerializable;
import com.star.commen.dto.PageDTO;
import com.star.module.front.dto.UpdatePersonalCenterInfoDto;
import com.star.module.front.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "个人中心", tags = "个人中心")
@RequestMapping("personalCenter/")
public interface PersonalCenterFacade {

    @ApiOperation(value = "个人中心我的信息")
    @GetMapping("personalCenterInfo")
    PersonalVo personalCenterInfo();

    @ApiOperation(value = "我的守护")
    @PostMapping("selectMyGuard")
    PageSerializable<MyGuardVo> selectMyGuard(@RequestBody PageDTO pageDTO);

    @ApiOperation(value = "热力获取记录")
    @PostMapping("selectVigourLog")
    PageSerializable<FensVigourLogVo> selectVigourLog(@RequestBody PageDTO pageDTO);

    @ApiOperation(value = "打榜记录")
    @PostMapping("selectHitList")
    PageSerializable<MyHitListVo> selectHitList(@RequestBody PageDTO pageDTO);


    @ApiOperation(value = "修改我的信息")
    @GetMapping("updatePersonalCenterInfo")
    void updatePersonalCenterInfo(@RequestBody UpdatePersonalCenterInfoDto updatePersonalCenterInfoDto);

    @ApiOperation(value = "榜单")
    @GetMapping("listAward")
    List<ListAwardPersionVo> listAward();

}
