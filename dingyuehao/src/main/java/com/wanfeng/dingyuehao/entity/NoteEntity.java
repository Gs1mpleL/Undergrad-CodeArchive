package com.wanfeng.dingyuehao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_note")
public class NoteEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userid;

    @TableField("text")
    private String text;

    @TableField("date")
    private Date time;

}
