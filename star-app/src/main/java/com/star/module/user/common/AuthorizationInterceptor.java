package com.star.module.user.common;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

/**
 * @description :身份验证拦截器
 * @author zhangrc
 * @date 2020/1/2 18:03
 * @version 1.0
 */
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {


    /**
     * 存放登录用户模型Key的Request Key
     */

    public static final String REQUEST_CURRENT_USER_ID = "REQUEST_CURRENT_USER_ID";

    public static final String REQUEST_CURRENT_ACCOUNT = "REQUEST_CURRENT_ACCOUNT";

    public static final String REQUEST_CURRENT_USER_NAME = "REQUEST_CURRENT_USER_NAME";

    public static final String REQUEST_CURRENT_AUTH_DATA = "REQUEST_CURRENT_AUTH_DATA";

    /**
     *  管理身份验证操作的对象
     */
    @Autowired
    private TokenManager tokenManager;

    /**
     *  存放鉴权信息的Header名称，默认是Authorization
     */
    private String httpHeaderName = "Authorization";


    /**
     *  鉴权失败后返回的错误信息，默认为401 unauthorized
     */
    private String unauthorizedErrorMessage = "401 unauthorized";

    /**
     *  鉴权失败后返回的HTTP错误码，默认为401
     */
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = null;
        try {
            handlerMethod = (HandlerMethod) handler;
        }catch (Exception e){
            return false;
        }
        Method method = handlerMethod.getMethod();

        String unauthorizedErrorMsg = unauthorizedErrorMessage;

        // 从header中得到token
        String token = request.getHeader(httpHeaderName);

        if(token == null){
            // 如果不是映射到方法直接通过
            if ((handlerMethod.getBeanType().getAnnotation(IgnoreSecurity.class) != null   // 查看方法所在的Controller是否有注解
                    || method.getAnnotation(IgnoreSecurity.class) != null)){
                return true;
            }
        }

        if (token != null && token.length() > 0) {
            Jws<Claims> claimsJws = null;
            try {

                claimsJws = Jwts.parser().setSigningKey(AbstractTokenManager.TOKEN_KEY).parseClaimsJws(token);
                // OK, we can trust this JWT

            } catch (SignatureException | MalformedJwtException | ExpiredJwtException e) {
                // don't trust the JWT!
                log.warn("Wrong token: {}", token);
                unauthorizedErrorMsg ="Token authorization failed";
            }

            if (claimsJws != null) {
                String userId = claimsJws.getBody().getAudience();
                String account = claimsJws.getBody().getSubject();
                String userName = claimsJws.getBody().get("user_name").toString();
                String updateSeconds = claimsJws.getBody().get("updateSeconds").toString();
                Date now = Calendar.getInstance().getTime();
                long nowTime = now.getTime();
                long updateSecondsTime = Long.valueOf(updateSeconds);
                if(nowTime>updateSecondsTime){
                    token = tokenManager.createToken(Long.valueOf(userId),account,userName);
                    response.setHeader("Access-Control-Expose-Headers",httpHeaderName);
                    response.setHeader(httpHeaderName,token);
                    System.out.println("更新token："+token);
                }
                if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(account)) {
                    // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                    request.setAttribute(REQUEST_CURRENT_USER_ID, userId);
                    request.setAttribute(REQUEST_CURRENT_ACCOUNT, account);
                    request.setAttribute(REQUEST_CURRENT_USER_NAME, userName);
                    return true;
                }else {
                    unauthorizedErrorMsg ="Token expired, please log in again";
                }
            }

        }
        response.setStatus(unauthorizedErrorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.write("{\n" +
                "  \"success\": false,\n" +
                "  \"code\": \"401\",\n" +
                "  \"message\": \""+unauthorizedErrorMsg+"\",\n" +
                "  \"time\": \""+System.currentTimeMillis()+"\",\n"+
                "  \"data\": null,\n" +
                "  \"errors\": null,\n" +
                "  \"stackTrace\": null\n" +
                "}");
        writer.close();
        return false;
    }



    public String getHttpHeaderName() {
        return httpHeaderName;
    }

    public void setHttpHeaderName(String httpHeaderName) {
        this.httpHeaderName = httpHeaderName;
    }

    public String getUnauthorizedErrorMessage() {
        return unauthorizedErrorMessage;
    }

    public void setUnauthorizedErrorMessage(String unauthorizedErrorMessage) {
        this.unauthorizedErrorMessage = unauthorizedErrorMessage;
    }

    public int getUnauthorizedErrorCode() {
        return unauthorizedErrorCode;
    }

    public void setUnauthorizedErrorCode(int unauthorizedErrorCode) {
        this.unauthorizedErrorCode = unauthorizedErrorCode;
    }

    public static String getRequestCurrentUserId() {
        return REQUEST_CURRENT_USER_ID;
    }

    public static String getRequestCurrentAccount() {
        return REQUEST_CURRENT_ACCOUNT;
    }
}
