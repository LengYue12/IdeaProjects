package com.lagou.feign;

import com.lagou.pojo.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/28 15:01
 * @Description 商品feign接口
 */
@FeignClient(name = "lagou-service-goods",fallback = FeignFallBack.class)
public interface ProductFeign {

    /** 根据订单id查询商品列表
     *  调用商品微服务中的方法
     *  url 要写全
     */
    @GetMapping("/goods/getProduct/{oid}")
    List<Products> getProduct(@PathVariable Integer oid);
}
