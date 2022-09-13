package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.ResourceMapper;
import com.lagou.entity.Resource;
import com.lagou.entity.ResourceVo;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/2 14:35
 * @description
 */
@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceMapper resourceMapper;


    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo) {
        // 分页查询
        PageHelper.startPage(resourceVo.getCurrentPage(),resourceVo.getPageSize());

        List<Resource> allResourceByPage = resourceMapper.findAllResourceByPage(resourceVo);

        return new PageInfo<>(allResourceByPage);
    }

    @Override
    public void saveResource(Resource resource) {
        // 封装数据
        Date date = new Date();
        resource.setCreatedTime(date);
        resource.setUpdatedTime(date);
        resource.setCreatedBy("system");
        resource.setUpdatedBy("system");

        // 调用mapper
        resourceMapper.saveResource(resource);
    }

    @Override
    public void updateResource(Resource resource) {

        // 封装数据
        resource.setUpdatedTime(new Date());

        // 调用mapper
        resourceMapper.updateResource(resource);
    }

    @Override
    public void deleteResource(int id) {

        resourceMapper.deleteResource(id);
    }
}
