package com.lagou.dao;

import com.lagou.entity.*;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/1 16:26
 * @description
 */
public interface UserMapper {

    /*
        用户分页&多条件组合查询
     */
    public List<User> findAllUserByPage(UserVo userVo);


    /*
        修改用户状态
     */
    void updateUserStatus(User user);


    /*
        用户登录（根据用户名查询具体的用户信息）
     */
    User login(User user);


    /*
        根据用户ID清空中间表
     */
    void deleteUserContextRole(Integer userId);


    /*
        分配角色，就是向中间表添加记录
     */
    void userContextRole(User_Role_relation user_role_relation);



    /*
        1.根据用户ID查询关联的角色信息       多个角色
     */
    List<Role> findUserRelationRoleById(int id);



    /*
        2.根据角色ID，查询角色所拥有的顶级菜单(-1)
     */
    // 多个角色对应的ID值
    List<Menu> findParentMenuByRoleId(List<Integer> ids);


    /*
        3.根据PID，父菜单信息，查询子菜单信息
     */
    List<Menu> findSubMenuByPid(Integer pid);


    /*
        4.获取用户拥有的资源权限信息，其实是根据角色信息去查询资源
     */
    // 根据角色Id进行查询
    List<Resource> findResourceByRoleId(List<Integer> ids);
    
    List<Resource> findResourceByRoleId2(List<Integer> ids);


    public void test11();
    public void test21();
    public void test31();
    public void test41();
    public void test51();





    public void test1();
    public void test2();
    public void test3();
    public void test4();
    public void test5();

}
