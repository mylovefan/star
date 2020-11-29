package com.star.module.user.common;

/**
 * @Description: 对Token进行管理的接口
 * @Author:  zhangrc
 * @CreateDate:  2020/11/29 18:10
 */
public interface TokenManager {

    /**
     * 创建token
     * @param userId
     * @param account
     */
    String createToken( Long userId,String account, String userName);



}
