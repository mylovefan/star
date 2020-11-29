package com.star.module.user.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserMenuVo {

    private String userName;

    private List<MenusVo> menus;
}
