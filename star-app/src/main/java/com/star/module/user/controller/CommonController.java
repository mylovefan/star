package com.star.module.user.controller;


import com.star.module.user.dto.LoginDto;
import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.facade.CommonFacade;
import com.star.module.user.vo.UserLoginVo;
import com.star.module.user.vo.UserMenuVo;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public UserLoginVo login(LoginDto loginDto) {
        return null;
    }

    @Override
    public void loginout() {

    }

    @Override
    public UserMenuVo getMenus() {
        return null;
    }

    @Override
    public Boolean modifyPassCode(ModifyPassCodeDTO modifyPassCodeDTO) {
        return null;
    }
}
