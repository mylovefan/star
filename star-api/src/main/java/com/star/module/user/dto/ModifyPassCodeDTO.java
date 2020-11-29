package com.star.module.user.dto;

import lombok.Data;


@Data
public class ModifyPassCodeDTO {

    private String account;

    private String oldPassword;

    private String newPassword;
}
