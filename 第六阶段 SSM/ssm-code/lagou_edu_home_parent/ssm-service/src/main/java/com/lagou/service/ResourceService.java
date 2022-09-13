package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.Resource;
import com.lagou.entity.ResourceVo;

/**
 * @author zs
 * @date 2022/7/2 14:34
 * @description
 */
public interface ResourceService {

    /*
            资源分页&多条件查询
         */
    PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);


    /*
        添加资源
     */
    void saveResource(Resource resource);


    /*
        修改资源
     */
    void updateResource(Resource resource);

    /*
        删除资源
     */
    void deleteResource(int id);
}
