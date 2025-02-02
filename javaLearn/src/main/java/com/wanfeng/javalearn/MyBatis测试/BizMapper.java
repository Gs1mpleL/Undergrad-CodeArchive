package com.wanfeng.javalearn.MyBatis测试;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BizMapper extends BaseMapper<BizEntity> {
    int updateById(@Param("entity")BizEntity entity);

    void insertOrDelete(List<BizEntity> entities);
}
