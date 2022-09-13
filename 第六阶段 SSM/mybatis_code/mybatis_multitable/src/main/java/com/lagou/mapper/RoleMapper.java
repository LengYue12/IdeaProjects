package com.lagou.mapper;

import com.lagou.domain.Role;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/4 19:52
 * @description
 */
public interface RoleMapper {

    /*
        根据用户id查询对应角色
     */
    public List<Role> findByUid(Integer uid);

}
