package com.star.module.user.service;

import com.star.module.user.vo.UserLoginVo;

public interface WeixinAuthService {

    /**
     * 微信登录
     *
     * @param code
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     */
    UserLoginVo weiXinLong(String code, String rawData, String signature, String encrypteData, String iv);

    /**
     * 测试获取token
     *
     * @param id
     * @return
     */
    UserLoginVo testLogin(Long id);
}
