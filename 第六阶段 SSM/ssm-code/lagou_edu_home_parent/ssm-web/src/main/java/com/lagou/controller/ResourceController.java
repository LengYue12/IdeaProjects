package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.Resource;
import com.lagou.entity.ResourceVo;
import com.lagou.entity.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zs
 * @date 2022/7/2 14:39
 * @description
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    /*
        分页查询&多条件
     */
    @RequestMapping("/findAllResource")
    public ResponseResult findAllResource(@RequestBody ResourceVo resourceVo){
        PageInfo<Resource> allResourceByPage = resourceService.findAllResourceByPage(resourceVo);

        return new ResponseResult(true,200,"资源信息分页多条件查询成功",allResourceByPage);
    }



    /*
        添加&更新资源信息
     */
    @RequestMapping("/saveOrUpdateResource")
    public ResponseResult saveOrUpdateResource(@RequestBody Resource resource){

        ResponseResult responseResult = null;
        if (resource.getId() == null) {
            // 新增
            resourceService.saveResource(resource);
            responseResult = new ResponseResult(true,200,"新增资源信息成功",null);
        } else {
            // 修改
            resourceService.updateResource(resource);
            responseResult = new ResponseResult(true,200,"修改资源信息成功",null);
        }
        return responseResult;
    }


    /*
        删除资源信息
     */
    @RequestMapping("/deleteResource")
    public ResponseResult deleteResource(int id){
        resourceService.deleteResource(id);
        return new ResponseResult(true,200,"删除资源信息成功",null);
    }
}
