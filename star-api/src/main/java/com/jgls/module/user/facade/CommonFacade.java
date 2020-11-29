package com.star.module.user.facade;

import com.star.module.user.dto.LoginDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "公共接口", tags = "公共接口")
@RequestMapping("common/")
public interface CommonFacade {

    @ApiOperation(value = "登录")
    @PostMapping("login")
    UserLoginVo login(@RequestBody LoginDto loginDto);


    @ApiOperation(value = "退出登录")
    @PostMapping("loginout")
    void loginout();

    @ApiOperation(value = "获取菜单")
    @PostMapping("getMenus")
    UserMenuVo getMenus();

    @ApiOperation(value = "修改密码")
    @PostMapping("modifyPass")
    Boolean modifyPassCode(@RequestBody ModifyPassCodeDTO modifyPassCodeDTO);
}
