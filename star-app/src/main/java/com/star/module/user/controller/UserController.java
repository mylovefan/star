package com.star.module.user.controller;

import com.github.pagehelper.PageSerializable;
import com.star.module.user.dto.UserDto;
import com.star.module.user.dto.UserPageDto;
import com.star.module.user.facade.UserFacade;
import com.star.module.user.service.UserService;
import com.star.module.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserFacade {

    @Autowired
    private UserService userService;

    @Override
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @Override
    public void delUser(@RequestParam("id") Long id) {
        userService.delUser(id);
    }

    @Override
    public void updateUser(@RequestParam("id")Long id,@RequestParam("status") Integer status) {
        userService.updateUser(id, status);
    }

    @Override
    public PageSerializable<UserVo> selectPage(@RequestBody UserPageDto userPageDto) {
        return userService.selectPage(userPageDto);
    }
}
