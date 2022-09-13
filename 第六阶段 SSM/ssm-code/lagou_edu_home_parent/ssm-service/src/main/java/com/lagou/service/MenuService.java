package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.Menu;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 10:28
 * @description
 */
public interface MenuService {

    /*
        查询所有父子菜单信息
     */
    List<Menu> findSubMenuListByPid(int pid);



    /*
        查询所有菜单列表
     */
    PageInfo<Menu> findAllMenu(int currentPage, int pageSize);

    Menu findMenuById(int id);


    /*
        添加菜单
     */
    void saveMenu(Menu menu);

    /*
        修改菜单
     */
    void updateMenu(Menu menu);


    /*
        删除菜单
     */
    void deleteMenu(int id);
}
