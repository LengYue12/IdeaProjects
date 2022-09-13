package com.lagou.controller;

import com.lagou.entity.ResourceCategory;
import com.lagou.entity.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zs
 * @date 2022/7/2 14:53
 * @description
 */
@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {


    @Autowired
    private ResourceCategoryService resourceCategoryService;

    /*
        查询资源分类信息
     */
    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory(){

        return new ResponseResult(true,200,"查询所有分类信息成功",resourceCategoryService.findAllResourceCategory());
    }



    /*
        添加&修改资源分类
     */
    @RequestMapping("/saveOrUpdateResourceCategory")
    // 前台是post，需要把请求体中的JSON拿到转为 ResourceCategory 对象
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory){

        ResponseResult responseResult = null;

        // 判断前台传递的id为不为空，有id 就是修改操作，没有id 就是新增操作
        if (resourceCategory.getId() == null) {
            // 新增 调用新增方法
            resourceCategoryService.saveResourceCategory(resourceCategory);
            responseResult = new ResponseResult(true,200,"添加资源分类信息成功",null);
        } else {
            // 修改
            resourceCategoryService.updateResourceCategory(resourceCategory);
            responseResult = new ResponseResult(true,200,"修改资源分类信息成功",null);
        }
        return responseResult;
    }




    /*
        删除资源分类
     */
    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(int id){

        resourceCategoryService.deleteResourceCategory(id);

        return new ResponseResult(true,200,"删除资源分类信息成功",null);
    }
}
