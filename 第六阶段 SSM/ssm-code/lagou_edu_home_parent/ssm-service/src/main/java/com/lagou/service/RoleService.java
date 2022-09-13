package com.lagou.service;

import com.lagou.entity.ResourceCategory;
import com.lagou.entity.Role;
import com.lagou.entity.RoleMenuVo;
import com.lagou.entity.RoleResourceVo;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 9:15
 * @description
 */
public interface RoleService {

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
        为角色分配菜单
     */
    public void roleContextMenu(RoleMenuVo roleMenuVo);



    /*
        获取资源分类数据和资源数据
     */
    List<ResourceCategory> findResourceListByRoleId(int roleId);




    /*
        为角色分配资源
     */
    void roleContextResource(RoleResourceVo roleResourceVo);


    /*
        删除角色
     */
    void deleteRole(int roleId);
}
