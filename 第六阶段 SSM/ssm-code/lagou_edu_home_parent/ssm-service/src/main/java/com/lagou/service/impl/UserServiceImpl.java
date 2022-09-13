package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.entity.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zs
 * @date 2022/7/1 16:52
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /*
        用户分页&多条件查询
     */
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {

        // 实现分页效果       参数：当前页，每页显示多少条
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());

        // 这样的话，在调用mapper方法时，就可以分页查询了
        // list 集合就是分页数据
        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);

        // 拿到pageInfo，里面就有分页的相关参数了
        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);

        return pageInfo;
    }


    /*
        修改用户状态
     */
    @Override
    public void updateUserStatus(int id, String status) {

        // 封装数据
        User user = new User();
        user.setId(id);
        user.setStatus(status);

        // 调用mapper
        userMapper.updateUserStatus(user);
    }


    /*
        用户登录
     */
    @Override
    public User login(User user) throws Exception {

        // 1.调用mapper   user1：包含了密文密码
        User user1 = userMapper.login(user);

        // 表示根据用户名查询出来的包含了密文密码的对象，的密文密码与前台的加密过后的明文密码 是相同的
        // 那么用户名密码正确，登录成功
        if (user1 != null && Md5.verify(user.getPassword(), "lagou", user1.getPassword())) {
            return user1;
        } else {
            return null;
        }
    }


    /*
        分配角色（回显）
     */
    @Override
    public List<Role> findUserRelationRoleById(int id) {
        return userMapper.findUserRelationRoleById(id);
    }


    /*
        分配角色，就是向中间表添加记录
     */
    @Override
    public void userContextRole(UserVo userVo) {

        // 1.根据用户ID 清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());

        // 2.再重新建立关联关系
        for (Integer roleId : userVo.getRoleIdList()) {

            // 封装数据
            User_Role_relation user_role_relation = new User_Role_relation();

            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }


    /*
        获取用户权限信息
     */
    @Override
    public ResponseResult getUserPermissions(Integer userId) {

        // 1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        // 2.获取角色ID，保存到List集合中
        ArrayList<Integer> roleIds = new ArrayList<>();

        // 遍历保存角色Id
        for (Role role : roleList) {

            roleIds.add(role.getId());
        }

        // 3.根据角色ID查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);


        // 4.查询封装父菜单所关联的子菜单
        // 因为现在查询出来的父菜单信息有很多，每个父菜单都对应一些子菜单
        // 所以要分别查询每个父菜单所关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            // 封装父菜单里面的子菜单
            menu.setSubMenuList(subMenu);
        }


        // 5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);


        // 6.封装数据并返回
        Map<String, Object> map = new HashMap<>();
        // 封装好的父菜单及关联的子菜单集合
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);

        return new ResponseResult(true,200,"获取用户权限信息成功",map);
    }
}
