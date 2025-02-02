package com.wanfeng.javalearn.MyBatis测试;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("t_biz")
public class BizEntity extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("data")
    private String data;
}
