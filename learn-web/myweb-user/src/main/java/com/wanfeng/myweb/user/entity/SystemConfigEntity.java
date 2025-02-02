package com.wanfeng.myweb.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("system_config")
public class SystemConfigEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField(value = "bili_cookie")
    private String biliCookie;
    @TableField(value = "yuan_shen_cookie")
    private String yuanShenCookie;
    @TableField(value = "bili_refresh_token")
    private String biliRefreshToken;
    @TableField(value = "name")
    private String name;
    @TableField(value = "password")
    private String password;
    @TableField(value = "login_time")
    private Timestamp time;

    @TableField(value = "shutdown")
    private int shutdown;
    @TableField(value = "position")
    private String position;
}
