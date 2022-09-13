package com.lagou.dao;

import com.lagou.entity.*;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 9:11
 * @description
 */
public interface RoleMapper {

    /*
        查询所有角色&条件进行查询
     */
    List<Role> findAllRole(Role role);


    /*
        添加角色
     */
    void saveRole(Role role);

    /*
        修改角色
     */
    void updateRole(Role role);



    /*
        根据角色ID 查询该角色关联的菜单信息 ID  [1,2,3,4,5]
     */
    List<Integer> findMenuByRoleId(Integer roleId);


    /*
        根据RoleId 清空中间表的关联关系
     */
    void deleteRoleContextMenu(Integer rid);


    /*
        为角色分配菜单信息
     */
    void roleContextMenu(Role_menu_relation role_menu_relation);



    /*
         根据角色ID 查询当前角色拥有的资源分类信息
     */
    List<ResourceCategory> findResourceCategoryByRoleId(int roleId);


    /*
        查询当前角色拥有的资源信息
     */
    List<Resource> findResourceByRoleId(int roleId);




    /*
        根据RoleId 清空角色与资源的关联关系  其实删除角色资源中间表的关联关系
     */
    void deleteRoleContextResource(Integer roleId);


    /*
        为角色分配资源信息
     */
    void roleContextResource(Role_resource_relation role_resource_relation);





    /*
        删除角色
     */
    void deleteRole(int roleId);
}
