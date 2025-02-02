package com.wanfeng.javalearn.MyBatis测试;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_time_test")
public class TimeEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("time")
    private Date date;
}
