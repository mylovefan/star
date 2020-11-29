package com.star.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LogCostInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<Object> userInfoThreadLocal = new ThreadLocal<>();

    private void getUserInfo(HttpServletRequest request){
//        UserInfo userInfo = new UserInfo();
//        Object account = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_ACCOUNT);
//        userInfo.setAccount(account == null ? null : account.toString());
//        Object userName = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_NAME);
//        userInfo.setUserName(userName == null ? null : userName.toString());
//        Object authData = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_AUTH_DATA);
//        if(authData != null){
//            CustomAuthData customAuthData = JSON.parseObject(authData.toString(),CustomAuthData.class);
//            userInfo.setCustomAuthData(customAuthData);
//        }
//        Object userId = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_ID);
//        userInfo.setUserId(userId == null ? null : userId.toString());
//        userInfoThreadLocal.set(userInfo);
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        getUserInfo(httpServletRequest);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        userInfoThreadLocal.remove();
    }
}
