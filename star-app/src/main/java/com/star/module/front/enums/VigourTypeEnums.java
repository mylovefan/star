package com.star.module.front.enums;

public enum  VigourTypeEnums {
    SIGN(1,"签到"),
    LUCK(2,"抽奖"),
    VIEW(3,"看视频"),
    SHARE(1,"分享"),
    GIVE(1,"赠送"),
    ;

    private int code;

    private String message;

    VigourTypeEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
