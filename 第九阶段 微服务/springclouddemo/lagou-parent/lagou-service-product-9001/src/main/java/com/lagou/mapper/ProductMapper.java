package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.common.pojo.Products;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 19:22
 * @Description 现在使用的Mybatis-plus组件，该组件是mybatis的加强版
 *                能够与SpringBoot进行友好的整合，对比Mybatis框架只有便捷的改变
 *                没有具体功能的改变
 *                具体使用：让具体的Mapper接口继承BaseMapper即可
 */
public interface ProductMapper extends BaseMapper<Products> {
}
