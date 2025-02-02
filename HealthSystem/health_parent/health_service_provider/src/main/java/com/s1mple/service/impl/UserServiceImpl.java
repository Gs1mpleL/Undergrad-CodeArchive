package com.s1mple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.s1mple.dao.PermissionDao;
import com.s1mple.dao.RoleDao;
import com.s1mple.dao.UserDao;
import com.s1mple.pojo.Permission;
import com.s1mple.pojo.Role;
import com.s1mple.pojo.User;
import com.s1mple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 提供用户信息的服务
 * @author wanfeng
 * @create 2022/2/26 11:00
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public User findByUsername(String username) {
        // 查询基本信息
        User user = userDao.findByUsername(username);
        if(user == null){
            return null;
        }
        Integer userId = user.getId();

        // 之后再查询角色
        Set<Role> roles = roleDao.findByUserId(userId);
        // 根据角色查询对应的权限
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
            // 让用户的每个角色关联自己的权限
            role.setPermissions(permissions);
        }
        user.setRoles(roles);

        return user;
    }
}
