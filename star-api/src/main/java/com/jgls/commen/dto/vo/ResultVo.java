package com.star.commen.dto.vo;

import lombok.Data;


@Data
public class ResultVo {

    //返回状态true:成功， false:失败
    private boolean success = true;
    //信息/失败原因
    private String message;
    //失败代码，用于定位问题，成功返回200。
    private String code = "200";
    //服务响应时间戳
    private Long time;
    //业务json
    private Object data;

}
