package com.lagou.fegin;

import com.lagou.common.pojo.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/23 20:38
 * @Description 自定义的Feign接口，调用Product微服务的所有的接口方法都在此进行定义
 */
// name值唯一，所以将所有调用Product微服务的方法都定义在这个接口中
// 在2个Feign接口类内定义相同的名字，
// @FeignClient(name = 相同的名字 就会出现报错，在之前的版本不会提示报错）
// 所以最好是针对于调用的某一个微服务的提供者单独定义一个Feign接口
// 就是把调用一个微服务的方法都写在一个Feign接口中
    // fallback 指定回退之后所调用的实现类的字节码对象
@FeignClient(name = "lagou-service-product",fallback = ProductFeignFallBack.class)
public interface ProductFeign {

    // 访问商品微服务的方法
    /**
     * 通过id查询，返回一个Product对象
     * 调用商品微服务中的方法
     * url 要写全
     * @param id
     * @return
     */
    @GetMapping("/product/find/{id}")
    public Products findById(@PathVariable Integer id);


    @GetMapping("/service/port")
    public String getPort();
}
