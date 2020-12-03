package com.star.module.user.controller;


import com.star.module.user.common.IgnoreSecurity;
import com.star.module.operation.dto.LoginDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.facade.CommonFacade;
import com.star.module.user.service.CommonService;
import com.star.module.user.service.WeixinAuthService;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import com.star.util.TencentUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * <p>
 * 运营后台用户表 前端控制器
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-11-29
 */
@RestController
public class CommonController implements CommonFacade {

    @Autowired
    private CommonService commonService;

    @Autowired
    private WeixinAuthService weixinAuthService;

    @Override
    @IgnoreSecurity
    public UserLoginVo login(@RequestBody LoginDto loginDto) {
        return commonService.login(loginDto.getAccount(), loginDto.getPwd());
    }

    @Override
    public void loginout() {

    }

    @Override
    public UserMenuVo getMenus() {
        UserMenuVo userMenuVo = new UserMenuVo();
        userMenuVo.setMenus(commonService.getMenus());
        return userMenuVo;
    }

    @Override
    public Boolean modifyPassCode(ModifyPassCodeDTO modifyPassCodeDTO) {
        return null;
    }


    @Override
    @IgnoreSecurity
    public UserLoginVo weiXinLong(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "rawData", required = false) String rawData,
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "encrypteData", required = false) String encrypteData,
                           @RequestParam(value = "iv", required = false) String iv) {

        return weixinAuthService.weiXinLong(code, rawData, signature, encrypteData, iv);

    }

    @Override
    public String upload(@RequestParam(value = "file") File file) {
        return TencentUploadUtil.upload(file);
    }

    @Override
    @IgnoreSecurity
    public UserLoginVo testLogin(Long id) {
        return weixinAuthService.testLogin(id);
    }
}
