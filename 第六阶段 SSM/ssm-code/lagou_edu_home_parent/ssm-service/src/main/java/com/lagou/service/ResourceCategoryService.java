package com.lagou.service;

import com.lagou.entity.ResourceCategory;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 14:51
 * @description
 */
public interface ResourceCategoryService {

    /*
            查询所有资源分类
         */
    public List<ResourceCategory> findAllResourceCategory();



    /*
        添加资源分类
     */
    void saveResourceCategory(ResourceCategory resourceCategory);


    /*
        修改资源分类
     */
    void updateResourceCategory(ResourceCategory resourceCategory);


    /*
        删除资源分类
     */
    void deleteResourceCategory(int id);
}
