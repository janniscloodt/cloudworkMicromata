package com.oskarwiedeweg.cloudwork.user.dto;

import lombok.Data;

@Data
public class Setup2FADto {

    private final String qrCode;
    private final String tempToken;

}
