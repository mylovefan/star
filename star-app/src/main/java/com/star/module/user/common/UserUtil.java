package com.star.module.user.common;


import javax.servlet.http.HttpServletRequest;

/**
 * @description :TODO
 * @author zhangrc
 * @date 2020/11/29 18:10
 * @version 1.0
 */
public class UserUtil {

    /**
     * 获取当前登录用户账号
     *
     * @return
     */
    public static String getCurrentAccount(HttpServletRequest request) {
        Object account = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_ACCOUNT);
        return account == null ? null : account.toString();
    }


    /**
     * 获取当前登录用户名称
     *
     * @return
     */
    public static String getCurrentUserName(HttpServletRequest request) {
        Object userName = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_NAME);
        return userName == null ? null : userName.toString();
    }


    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static String getCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_ID);
        if (userId == null) {
            return null;
        }
        return userId.toString();
    }

}
