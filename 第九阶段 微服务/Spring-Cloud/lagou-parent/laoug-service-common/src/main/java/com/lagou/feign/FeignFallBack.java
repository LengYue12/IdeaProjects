package com.lagou.feign;

import com.lagou.pojo.Products;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 17:56
 * @Description
 */
@Component
public class FeignFallBack implements ProductFeign {
    // 熔断后服务降级，返回空商品列表
    @Override
    public List<Products> getProduct(Integer oid) {
        return new ArrayList<>();
    }
}
