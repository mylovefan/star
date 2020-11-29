package com.star.module.user.common;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @description :TODO
 * @author zhangrc
 * @date 2020/11/29 18:10
 * @version 1.0
 */
@Service
public class AbstractTokenManager implements TokenManager {

    // token生效时效
    protected int tokenExpireSeconds = 60 * 60 * 1000 * 3;

    // token更新时效
    protected int tokenUpdateSeconds = 60 * 60 * 1000;

    public static final String TOKEN_KEY = "sdfhsfhgyvcw%#$^%783624wGFGEJH";

    private static final String JWT_KEY_USER_NAME = "user_name";
    private static final String JWT_KEY_UPDATE_SECONDS = "updateSeconds";

    @Override
    public  String createToken(Long userId, String account, String userName) {

        String id = String.valueOf(userId);
        Date now = Calendar.getInstance().getTime();
        JwtBuilder builder = Jwts
                .builder()
                .setSubject(account)
                .setAudience(id)
                .setIssuedAt(now)
                .claim(JWT_KEY_USER_NAME,userName)
                // token更新时间
                .claim(JWT_KEY_UPDATE_SECONDS,now.getTime() + tokenUpdateSeconds)
                .signWith(SignatureAlgorithm.HS512, AbstractTokenManager.TOKEN_KEY);

        if (tokenExpireSeconds >= 0) {
            long expMillis = now.getTime() + tokenExpireSeconds;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }


}
