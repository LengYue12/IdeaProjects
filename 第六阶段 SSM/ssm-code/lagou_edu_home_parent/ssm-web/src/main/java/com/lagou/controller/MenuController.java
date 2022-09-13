package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.Menu;
import com.lagou.entity.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 12:30
 * @description
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /*
        查询所有菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(int currentPage,int pageSize){

        PageInfo<Menu> allMenu = menuService.findAllMenu(currentPage, pageSize);
        return new ResponseResult(true,200,"查询所有菜单信息成功",allMenu);
    }



    /*
        回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(int id){

        // 根据id的值判断当前是更新还是添加操作  判断id的值是否为-1
        if (id == -1){
            // 添加  回显信息中就不需要查询menu信息了
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("menuInfo",null);
            map.put("parentMenuList",subMenuListByPid);

            return new ResponseResult(true,200,"添加回显成功",map);
        } else {
            // 修改操作 回显所有menu信息
            Menu menu = menuService.findMenuById(id);

            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("menuInfo",menu);
            map.put("parentMenuList",subMenuListByPid);

            return new ResponseResult(true,200,"修改回显成功",map);

        }
    }

    /*
        添加&修改菜单
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu){

        ResponseResult responseResult = null;
        if (menu.getId() == null){
            // 新增
            menuService.saveMenu(menu);
            responseResult = new ResponseResult(true,200,"添加菜单成功",null);
        } else {
            // 修改
            menuService.updateMenu(menu);
            responseResult = new ResponseResult(true,200,"修改菜单成功",null);
        }
        return responseResult;
    }



    /*
        删除菜单
     */
    @RequestMapping("/deleteMenu")
    public ResponseResult deleteMenu(int id){

        menuService.deleteMenu(id);

        return new ResponseResult(true,200,"删除成功",null);
    }
}
