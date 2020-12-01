package com.star.module.user.vo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class StartVo{


    private Long id;

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

    private LocalDateTime createTime;

    /**
     * 周冠军
     */
    private int rankWeekChampion;

    /**
     * 月冠军
     */
    private int rankMonthChampion;
    /**
     * 周亚军
     */
    private int rankWeekSecond;

    /**
     * 月亚军
     */
    private int rankMonthSecond;
    /**
     * 周季军
     */
    private int rankWeekThird;

    /**
     * 月季军
     */
    private int rankMonthThird;

    /**
     * 当前周排名
     */
    private int thisWeekRank;

    /**
     * 当前月排名
     */
    private int thisMonthRank;


}
