package com.wanfeng.myweb.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("menu")
public class MenuEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String path;
    private String icon;
    private String url;
    private String label;
    private String name;
    @TableField("parent_id")
    private int parentId;
    @TableField("is_del")
    @TableLogic(value = "0", delval = "1")
    private int isDel;
}
