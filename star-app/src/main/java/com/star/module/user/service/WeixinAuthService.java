package com.star.module.user.service;

import com.star.module.user.vo.UserLoginVo;
import org.springframework.web.bind.annotation.RequestParam;

public interface WeixinAuthService {

    /**
     * 微信登录
     *
     * @param openid
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     */
    UserLoginVo weiXinLong(String openid, String rawData, String signature, String encrypteData, String iv);

    /**
     * 测试获取token
     *
     * @param id
     * @return
     */
    UserLoginVo testLogin(Long id);

    /**
     * 获取session
     *
     * @param code
     * @return
     */
    String getSessionKey(String code);
}
