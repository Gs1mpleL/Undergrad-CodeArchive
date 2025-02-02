package com.wanfeng.javalearn.MyBatis测试;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class BaseEntity {
    @TableLogic(value = "Y",delval = "N")
    private String isDelete;
}
