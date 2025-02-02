package com.wanfeng.javalearn.MyBatis测试;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimeMapper extends BaseMapper<TimeEntity> {
}
