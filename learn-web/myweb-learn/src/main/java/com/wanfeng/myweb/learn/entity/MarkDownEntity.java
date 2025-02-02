package com.wanfeng.myweb.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("markdown")
public class MarkDownEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String text;
    private String title;
    @TableField("is_del")
    @TableLogic(value = "0", delval = "1")
    private int isDel;
    // getters and setters...
}