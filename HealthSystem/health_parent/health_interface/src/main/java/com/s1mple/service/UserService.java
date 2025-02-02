package com.s1mple.service;

import com.s1mple.pojo.User;

/**
 * 用户登录信息Service
 * @author wanfeng
 * @create 2022/2/26 10:49
 */
public interface UserService {
    public User findByUsername(String username);
}
