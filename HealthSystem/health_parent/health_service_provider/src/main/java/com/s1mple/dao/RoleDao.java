package com.s1mple.dao;

import com.s1mple.pojo.Role;

import java.util.Set;

/**
 * @author wanfeng
 * @create 2022/2/26 11:06
 */
public interface RoleDao {
    public Set<Role> findByUserId(Integer id);
}
