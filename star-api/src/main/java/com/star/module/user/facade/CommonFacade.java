package com.star.module.user.facade;

import com.star.module.user.dto.LoginDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rawData", value = "非敏感信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "签名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "encrypteData", value = "敏感加密信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "偏移向量", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "微信登录")
    @GetMapping("weiXinLong")
    void weiXinLong(@RequestParam(value = "code",required = false) String code,
                    @RequestParam(value = "rawData",required = false) String rawData,
                    @RequestParam(value = "signature",required = false) String signature,
                    @RequestParam(value = "encrypteData",required = false) String encrypteData,
                    @RequestParam(value = "iv",required = false) String iv);
}
