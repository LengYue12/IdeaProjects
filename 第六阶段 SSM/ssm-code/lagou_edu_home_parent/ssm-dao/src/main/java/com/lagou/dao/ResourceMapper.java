package com.lagou.dao;

import com.lagou.entity.Resource;
import com.lagou.entity.ResourceVo;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 14:22
 * @description
 */
public interface ResourceMapper {

    /*
        资源分页&多条件查询
     */
    List<Resource> findAllResourceByPage(ResourceVo resourceVo);


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
