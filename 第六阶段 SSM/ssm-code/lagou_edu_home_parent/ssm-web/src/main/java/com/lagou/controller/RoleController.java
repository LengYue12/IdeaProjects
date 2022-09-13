package com.lagou.controller;

import com.lagou.entity.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 9:17
 * @description
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /*
        查询所有角色（条件）
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){

        List<Role> allRole = roleService.findAllRole(role);
        return new ResponseResult(true,200,"查询所有角色成功",allRole);
    }


    /*
        添加&修改角色
     */
    @RequestMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role){

        ResponseResult responseResult = null;
        if (role.getId() == null) {
            // 新增
            roleService.saveRole(role);
            responseResult = new ResponseResult(true,200,"添加角色成功",null);
        } else {
            // 修改
            roleService.updateRole(role);
            responseResult = new ResponseResult(true,200,"修改角色成功",null);
        }
        return responseResult;
    }


    /*
        查询所有的父子菜单信息(分配菜单的第一个接口)
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid(){

        // -1 表示查询所有的父子级菜单
        // 第一次查询是查询所有的父级菜单，第二次查询是该父级菜单所关联的子级菜单也进行查询
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);

        // 向map中设置前台要的 值
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",menuList);

        // 响应数据
        return new ResponseResult(true,200,"查询所有的父子菜单信息成功",map);
    }



    /*
        根据角色ID查询 关联的菜单信息ID
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId){

        List<Integer> menuByRoleId = roleService.findMenuByRoleId(roleId);

        return new ResponseResult(true,200,"查询角色关联的菜单信息成功",menuByRoleId);
    }


    /*
        为角色分配菜单
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo){

        roleService.roleContextMenu(roleMenuVo);

        return new ResponseResult(true,200,"响应成功",null);
    }



    /*
        根据角色ID 获取当前角色拥有的 资源信息
     */
    @RequestMapping("/findResourceListByRoleId")
    public ResponseResult findResourceListByRoleId(int roleId){

        List<ResourceCategory> resourcesList = roleService.findResourceListByRoleId(roleId);

        return new ResponseResult(true,200,"获取当前角色资源信息成功",resourcesList);
    }



    /*
        为角色分配资源
     */
    @RequestMapping("/roleContextResource")
    //  RoleResourceVo,用来接收 角色ID 与 资源ID集合
    public ResponseResult roleContextResource(@RequestBody RoleResourceVo roleResourceVo){

        roleService.roleContextResource(roleResourceVo);

        return new ResponseResult(true,200,"为角色分配资源信息成功",null);
    }



    /*
        删除角色
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(int id){

        roleService.deleteRole(id);


        return new ResponseResult(true,200,"删除角色成功",null);
    }
}
