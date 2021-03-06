package com.star.module.user.facade;

import com.star.module.operation.dto.LoginDto;
import com.star.module.operation.dto.UploadDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.dto.WeixinLongDto;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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


    @ApiOperation(value = "微信登录")
    @PostMapping("weiXinLong")
    UserLoginVo weiXinLong(@RequestBody WeixinLongDto weixinLongDto);

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    String upload(@RequestParam(value = "file") MultipartFile file);


    @ApiOperation(value = "上传文件")
    @PostMapping("uploadBase64")
    String uploadBase64(@RequestBody UploadDto uploadDto);


    @ApiOperation(value = "开发获取token")
    @PostMapping("testLogin")
    UserLoginVo testLogin(@RequestParam(value = "id") Long id);
}
