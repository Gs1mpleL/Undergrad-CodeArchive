package com.wanfeng.myweb.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("home_card")
@Data
public class HomeCardEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String title;
    private String icon;
    private String color;
}
