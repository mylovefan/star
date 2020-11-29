package com.star.module.user.service;


import com.star.module.user.dto.ModifyPassCodeDTO;
import com.star.module.user.vo.MenusVo;
import com.star.module.user.vo.UserLoginVo;

import java.util.List;


public interface CommonService {

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @return
     */
    UserLoginVo login(String account, String pwd);

    /**
     * 获取菜单
     *
     * @return
     */
    List<MenusVo> getMenus(String userId);

    /**
     *
     * @param modifyPassCodeDTO
     * @return
     */
    Boolean modifyPassCode(ModifyPassCodeDTO modifyPassCodeDTO);
}
