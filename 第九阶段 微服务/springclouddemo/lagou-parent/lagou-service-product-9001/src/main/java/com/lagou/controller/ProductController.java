package com.lagou.controller;

import com.lagou.common.pojo.Products;
import com.lagou.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 20:14
 * @Description 通过Id查询，返回一个product对象
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/find/{id}")
    public Products findById(@PathVariable Integer id){
        return productService.findById(id);
    }
}
