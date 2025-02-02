package com.wanfeng.myweb.api.dto;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class SystemConfigDto {
    private int id;
    private String name;
    private String password;
    private String biliCookie;
    private String yuanShenCookie;
    private String biliRefreshToken;
    private Timestamp time;
    private String position;
}
