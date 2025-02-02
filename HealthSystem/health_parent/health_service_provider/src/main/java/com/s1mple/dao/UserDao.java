package com.s1mple.dao;

import com.s1mple.pojo.User;

/**
 * @author wanfeng
 * @create 2022/2/26 11:03
 */
public interface UserDao {

    public User findByUsername(String username);
}
