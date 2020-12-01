package com.star.module.user.facade;

import com.github.pagehelper.PageSerializable;
import com.star.module.user.dto.UserDto;
import com.star.module.user.dto.UserPageDto;
import com.star.module.user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "运营账号", tags = "运营账号")
@RequestMapping("user/")
public interface UserFacade {

    @ApiOperation(value = "新增账号")
    @PostMapping("addUser")
    void addUser(@RequestBody UserDto userDto);


    /**
     * 删除账号
     *
     * @param id
     */
    @ApiOperation(value = "删除账号")
    @DeleteMapping("delUser")
    void delUser(@RequestParam("id") Long id);



    /**
     * 启用|禁用账号
     *
     * @param id
     */
    @ApiOperation(value = "启用|禁用账号")
    @PostMapping("updateUser")
    void updateUser(@RequestParam("id") Long id,@RequestParam("status") Integer status);


    @ApiOperation(value = "启用|禁用账号")
    @PostMapping("selectPage")
    PageSerializable<UserVo> selectPage(@RequestBody UserPageDto userPageDto);
}
