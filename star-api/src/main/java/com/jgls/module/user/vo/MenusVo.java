package com.star.module.user.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenusVo {

    private Long id;

    private String name;

    private String uri;

    private String icon;

    List<MenusVo> menus;
}
