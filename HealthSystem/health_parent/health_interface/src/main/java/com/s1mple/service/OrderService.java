package com.s1mple.service;

import com.s1mple.entity.Result;

import java.util.Map;

/**
 * 预约提交处理Service
 * @author wanfeng
 * @create 2022/2/24 19:52
 */
public interface OrderService {
    public Result order(Map map) throws Exception;

    public Map findById(Integer id) throws Exception;
}
