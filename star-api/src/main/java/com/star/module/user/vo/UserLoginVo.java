package com.star.module.user.vo;

import lombok.Data;

@Data
public class UserLoginVo {

    private String token;

    private Long userId;

    private String account;

    private String phone;
}
