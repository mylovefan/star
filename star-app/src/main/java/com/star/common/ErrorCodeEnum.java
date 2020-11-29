package com.star.common;


public enum ErrorCodeEnum implements Constant {
    SYSTOM_ERROR("100001","系统错误"),

    ERROR_200001("200001","接收数据失败"),
    PARAM_ERROR("200002","参数不正确"),

    USER_NOT_EXIST("300001","用户不存在或者密码错误"),
    USER_FORBID_ERROR("300003","账号已被禁用，请与管理员联系"),

    ;

    private String code;
    private String value;


    ErrorCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
