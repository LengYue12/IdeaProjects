package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.entity.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 9:16
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    @Override
    public void saveRole(Role role) {

        // 封装数据
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);
        role.setCreatedBy("system");
        role.setUpdatedBy("system");

        // 调用mapper
        roleMapper.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {

        // 封装数据
        role.setUpdatedTime(new Date());
        roleMapper.updateRole(role);
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {

        // 1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());

        // 2.为角色分配菜单

        for (Integer mid : roleMenuVo.getMenuIdList()) {

            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            // 封装数据
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            // mapper方法就是获取到 role_menu_relation 里的属性对中间表进行添加
            roleMapper.roleContextMenu(role_menu_relation);
        }

    }


    /*
        根据角色ID 获取资源分类数据和资源数据
     */
    @Override
    public List<ResourceCategory> findResourceListByRoleId(int roleId) {

        // 先获取资源分类数据
        List<ResourceCategory> resourceCategoryList = roleMapper.findResourceCategoryByRoleId(roleId);
        for (ResourceCategory resourceCategory : resourceCategoryList) {
            // 再获取资源数据
            // 将资源数据封装到对应的资源分类数据中的 resourceList 这个list 资源集合中，因为一个资源分类下有多个资源
            resourceCategory.setResourceList(roleMapper.findResourceByRoleId(roleId));
        }

        return resourceCategoryList;
    }



    /*
        为角色分配资源
     */
    @Override
    public void roleContextResource(RoleResourceVo roleResourceVo) {

        // 1.先清空中间表的关联关系
        roleMapper.deleteRoleContextResource(roleResourceVo.getRoleId());

        // 2.补全信息,插入新的关联关系  为角色分配资源
        for (Integer resourceId : roleResourceVo.getResourceIdList()) {

            Role_resource_relation role_resource_relation = new Role_resource_relation();
            // 封装资源ID       正在遍历的roleResourceVo 对象里面的 资源ID
            role_resource_relation.setResourceId(resourceId);
            // 封装角色ID       前台传递过来的 roleResourceVo 对象里面的 角色ID roleId
            role_resource_relation.setRoleId(roleResourceVo.getRoleId());

            // 封装数据
            Date date = new Date();
            role_resource_relation.setCreatedTime(date);
            role_resource_relation.setUpdatedTime(date);
            role_resource_relation.setCreatedBy("system");
            role_resource_relation.setUpdatedBy("system");

            // mapper方法就是获取到 封装好的 role_resource_relation 里的属性对中间表进行添加
            roleMapper.roleContextResource(role_resource_relation);
        }

    }


    @Override
    public void deleteRole(int roleId) {

        // 调用根据roleId 清空中间表关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        // 再根据roleId 删除具体角色信息
        roleMapper.deleteRole(roleId);
    }
}
