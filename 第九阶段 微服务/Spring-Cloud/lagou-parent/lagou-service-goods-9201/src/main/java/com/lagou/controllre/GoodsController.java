package com.lagou.controllre;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lagou.pojo.ProductVO;
import com.lagou.pojo.Products;
import com.lagou.pojo.ResponseResult;
import com.lagou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/27 22:17
 * @Description
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/find/{pid}")
    public Products find(@PathVariable Integer pid){
        return goodsService.findById(pid);
    }


    @DeleteMapping("/delete/{pid}")
    public String deleteById(@PathVariable Integer pid){
        goodsService.deleteById(pid);
        return "删除成功!";
    }


    @PutMapping("/update")
    public String update(Products products){
        goodsService.updateById(products);
        return "修改成功!";
    }


    // 分页查询
    @GetMapping("/page")
    public ResponseResult selectPage(@RequestBody ProductVO productVO){
        IPage<Products> productsIPage = goodsService.selectPage(productVO);
        Map<String, Object> map = new HashMap<>();
        map.put("list",productsIPage.getRecords());
        map.put("total",productsIPage.getTotal());
        return new ResponseResult(true,200,"查询成功",map);
    }


    /**
     * 根据订单id查询商品列表
     */
    @GetMapping("/getProduct/{oid}")
    public List<Products> getProduct(@PathVariable Integer oid){

        try {
            // 睡眠6秒
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return goodsService.getProduct(oid);
    }

}
