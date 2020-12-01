package com.star.module.user.service;

import com.github.pagehelper.PageSerializable;
import com.star.module.user.dto.UserDto;
import com.star.module.user.dto.UserPageDto;
import com.star.module.user.vo.UserVo;

public interface UserService {

    /**
     * 新增账号
     * @param userDto
     */
    void addUser(UserDto userDto);

    /**
     * 删除账号
     *
     * @param id
     */
    void delUser(Long id);



    /**
     * 启用|禁用账号
     *
     * @param id
     */
    void updateUser(Long id,Integer status);


    /**
     * 账号列表
     *
     * @param userPageDto
     * @return
     */
    PageSerializable<UserVo> selectPage(UserPageDto userPageDto);
}
