package com.s1mple.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.s1mple.pojo.Permission;
import com.s1mple.pojo.Role;
import com.s1mple.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户认证Service
 * @author wanfeng
 * @create 2022/2/26 10:46
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    /**
     * 使用dubbo远程调用服务提供方，来查询数据库用户信息
     */
    @Reference
    private UserService userService;




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        // 用户名不存在
        if(user == null){
            System.out.println("error");
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        // 动态为当前用户授权
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            // 遍历角色集合为用户授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                // 遍历角色的权限集合，为用户设置角色的权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        // 封装好 用户的角色和权限，返回一个User对象，参数是username，password(注意加密问题)，用户的权限集合
        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), list);
    }
}
