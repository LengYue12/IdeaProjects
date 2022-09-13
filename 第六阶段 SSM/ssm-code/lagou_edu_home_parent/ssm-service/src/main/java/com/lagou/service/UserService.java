package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.ResponseResult;
import com.lagou.entity.Role;
import com.lagou.entity.User;
import com.lagou.entity.UserVo;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/1 16:51
 * @description
 */
public interface UserService {

    /*
        用户分页&多条件查询
     */
    PageInfo findAllUserByPage(UserVo userVo);


    /*
        修改用户状态
     */
    void updateUserStatus(int id, String status);

    /*
        用户登录（根据用户名查询具体的用户信息）
     */
    User login(User user) throws Exception;


    /*
        分配角色（回显）
     */
    List<Role> findUserRelationRoleById(int id);


    /*
        分配角色，就是向中间表添加记录
     */
    void userContextRole(UserVo userVo);


    /*
        获取用户权限，进行菜单动态展示
     */
    ResponseResult getUserPermissions(Integer userId);
}
