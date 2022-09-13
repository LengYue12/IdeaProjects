package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.MenuMapper;
import com.lagou.entity.Menu;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 10:28
 * @description
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findSubMenuListByPid(int pid) {

        // List集合 里不仅有父级菜单信息，还有关联的子级菜单信息
        return menuMapper.findSubMenuListByPid(pid);
    }

    @Override
    public PageInfo<Menu> findAllMenu(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage,pageSize);

        List<Menu> allMenu = menuMapper.findAllMenu();

        PageInfo<Menu> menuPageInfo = new PageInfo<>(allMenu);

        return menuPageInfo;
    }


    @Override
    public Menu findMenuById(int id) {
        return menuMapper.finMenuById(id);
    }

    @Override
    public void saveMenu(Menu menu) {

        // 封装数据
        Menu menu1 = new Menu();
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        menu.setCreatedBy("system");
        menu.setUpdatedBy("system");
        if (menu.getParentId() == -1){
            // 父级菜单为0
            menu.setLevel(0);
        } else {
            menu.setLevel(1);   // 子菜单为 1
        }

        menuMapper.saveMenu(menu);

    }

    @Override
    public void updateMenu(Menu menu) {
        Date date = new Date();
        menu.setUpdatedTime(date);
        menu.setCreatedBy("system");
        menu.setUpdatedBy("system");

        menuMapper.updateMenu(menu);
    }

    @Override
    public void deleteMenu(int id) {
        menuMapper.deleteMenu(id);
    }
}
