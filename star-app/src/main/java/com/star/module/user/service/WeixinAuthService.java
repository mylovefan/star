package com.star.module.user.service;

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
    void weiXinLong( String code,String rawData,String signature,String encrypteData,String iv);
}
