package com.star.module.operation.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatModel {

    private Long id;

    private Long starId;

    private String name;

    private String avatar;

    private String picture;

    private String tags;

    private Integer hotSearch;

    private String openImg;

    private Integer hotNums;

    private String homeImg;

    private String detailImg;

    private String hitPopupImg;

    private Integer rankWeekChampionNum;

    private Integer rankMonthChampionNum;

    private Integer rankWeekSecondNum;

    private Integer rankMonthSecondNum;

    private Integer rankWeekThirdNum;

    private Integer rankMonthThirdNum;

    private Integer thisWeekRank;

    private Integer thisMonthRank;

    private LocalDateTime createTime;

    private LocalDateTime deleteTime;
    /**
     * 热力值
     */
    private Integer vigourVal;
}
