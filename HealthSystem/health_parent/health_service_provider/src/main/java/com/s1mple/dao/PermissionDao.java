package com.s1mple.dao;

import com.s1mple.pojo.Permission;

import java.util.Set;

/**
 * @author wanfeng
 * @create 2022/2/26 11:11
 */
public interface PermissionDao {
    public Set<Permission> findByRoleId(Integer id);
}
