package com.lagou.service.impl;


import com.lagou.domain.Permission;
import com.lagou.domain.User;
import com.lagou.service.PermissionService;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @BelongsProject lagou-security-management
 * @Author lengy
 * @CreateTime 2022/8/18 0:02
 * @Description 基于数据库完成用户名和密码的认证
 */
@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserService userService;


    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名查询用户
     *  查询数据库，将信息封装成 security 的user 对象并交给框架，让框架来完成身份的验证
     * @param s 前端传入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 根据前台传递的用户名去查询数据库
        User user = userService.findByUsername(s);


        if (user == null) {
            throw new UsernameNotFoundException("用户没有找到," + s);
        }

        // 权限的集合
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if(s.equalsIgnoreCase("admin")){
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }else {
//            authorities.add(new SimpleGrantedAuthority("ROLE_PRODUCT"));
//        }
        // 基于数据库查询用户所对应的权限
        List<Permission> permissionList = permissionService.findByUserId(user.getId());
        for (Permission permission : permissionList) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionTag()));
        }


        UserDetails userDetails = new org.springframework.security.core.userdetails.User
            (s,"{bcrypt}"+user.getPassword(), // noop不使用密码加密，bcrypt使用加密算法
                true,   // 用户是否启用
                true,   // 用户是否过期
                true,   // 用户凭证是否过期
                true,   // 用户是否锁定
                authorities);
        return userDetails;
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        String encode1 = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode1);
    }
}
